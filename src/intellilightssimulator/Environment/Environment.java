/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Environment;

import intellilightssimulator.Hardware.EnergyPackage.Battery;
import intellilightssimulator.Hardware.EnergyPackage.SolarPanel;
import intellilightssimulator.Hardware.LightPole.LED;
import intellilightssimulator.Hardware.LightPole.SensorModule;
import intellilightssimulator.IntelliLightsSimulator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author emilj
 */
public class Environment {

//    public int sunIntensity;
//    public int time;
//    public int trafficRate;
//    public int trafficSpeed;
//    public int roadConfiguration;
    private Battery battery;
    private SolarPanel solarPanel;
    private SensorModule sensorModule;
    private LED led;

    private int lightPoleDistance;
    private double speedLimit;
    
    private int solarPanelDimensionXmm;
    private int solarPanelDimensionYmm;
    private double Vmpp, Impp;
    private double sensorModuleEnergyCostMin;
    private double sensorModuleEnergyCostMax;
    private int ledEnergyCostMin;
    private int ledEnergyCostMax;
    private int LEDVoltageMin;
    private int LEDVoltageMax;
    private String date;
    private int speedLimitMin;
    private int speedLimitMax;
    private int amountOfCarsMin;
    private int amountOfCarsMax;
    private int amountOfPolesMin;
    private int amountOfPolesMax;
    private double longitude;
    private double latitude;
    private double nightTime;
    private double jan;
    private double feb;
    private double mar;
    private double apr;
    private double may;
    private double jun;
    private double jul;
    private double aug;
    private double sep;
    private double oct;
    private double nov;
    private double dec;
    
    public Environment() {
    }
    
    public void initSimulation(int solarPanelDimensionXmm, 
                                int solarPanelDimensionYmm, 
                                double Vmpp, double Impp,
                                double sensorModuleEnergyCostMin,
                                double sensorModuleEnergyCostMax,
                                int ledEnergyCostMin,
                                int ledEnergyCostMax,
                                int LEDVoltageMin,
                                int LEDVoltageMax,
                                String date,
                                int speedLimitMin,
                                int speedLimitMax,
                                int amountOfCarsMin,
                                int amountOfCarsMax,
                                int amountOfPolesMin,
                                int amountOfPolesMax,
                                double longitude,
                                double latitude,
                                double jan,
                                double feb,
                                double mar,
                                double apr,
                                double may,
                                double jun,
                                double jul,
                                double aug,
                                double sep,
                                double oct,
                                double nov,
                                double dec
                                ){
        solarPanel = new SolarPanel(Vmpp, Impp, solarPanelDimensionXmm, solarPanelDimensionYmm);
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        
        
        //set lightPoleDistance in roadConfiguration initialization
        
        System.out.println("Environment Initialized");
    }
    
    public void runSimulation(){
        
        double dayTime = lookUpDaytime(this.latitude, this.longitude, this.date);
        this.nightTime = 1440 - dayTime;
        System.out.println("night time: "+this.nightTime);
        
//        for(int i = 0; i < this.ticks; i++){
//            
//            // simulate one night
//            
//        }
    }
    
    public double calcLedConsumption(){
        // m/(m/s) = m*(s/m) = ms/m = s    #notetoself
        /*
            50 w
            60 s
            20 amp-hour
            48 V

            60s/3600 = 0.016 h
            50 W * 0.016 h = 0.8 Wh
            0.8 / 48 V = 0.016 amp-hours
            20 - 0.016 = 

            ((20amp-hour*48V)Wh) / 0.8 Wh = 1200 h
            20/0.01666666666666666666 = 1200.00000000000000048 h
        */
        
        int carSpeed = generateCarSpeed();
        double meterPerSeconds = carSpeed / 3.6;
        double timeBetweenPoles = this.lightPoleDistance / meterPerSeconds;
        double pwrConsumption = this.led.getPowerConsumption();
        double watthours = pwrConsumption * (timeBetweenPoles/3600);
        double energyConsumed = watthours / this.led.getVoltage();
        return energyConsumed;
    }
    
    public int generateCarSpeed(){
        
        int minSpeed = (int)Math.round(this.speedLimit * 0.9);
        int maxSpeed = (int)Math.round(this.speedLimit * 1.1);
        
        int range = (maxSpeed - minSpeed) + 1;
        return (int)(Math.random()* range) + minSpeed;
    }
    
    /**
     * 
     * @param latitude
     * @param longitude
     * @param date
     * @return 
     */
    public double lookUpDaytime(double latitude, double longitude, String date) {

        URL url;
        try {
            url = new URL("http://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "&date=" + date);
            String dayTime;
            String[] parts;
            double hours;
            double minutes;
            double seconds;
            JSONParser parser = new JSONParser();
            JSONObject content;
                    
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    try {
                        content = (JSONObject)parser.parse(line);
                        if(content.get("status").equals("OK")){
                            JSONObject results = (JSONObject)content.get("results");
                            dayTime = results.get("day_length").toString();
                            parts = dayTime.split(":");
                            
                            hours  = Integer.parseInt(parts[0]);
                            minutes = Integer.parseInt(parts[1]);
                            seconds = Integer.parseInt(parts[2]);
                            double totalMin = hours*60 + minutes + seconds/60;
                            
                            return totalMin;
                        }else{
                            System.err.println("Error");
                            return -1;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(IntelliLightsSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}
