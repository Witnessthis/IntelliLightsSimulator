/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Environment;
import intellilightssimulator.IntelliLightsSimulator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.LinkedHashMap;
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

    private final SolarPanel solarPanel;

    private final double sensorWatts;
    private final int ledPowMin;
    private final int ledPowMax;
    private final double battEff;
    private final int speedLimitMin;
    private final int speedLimitMax;
    private final int amountOfCarsMin;
    private final int amountOfCarsMax;
    private final int amountOfPolesMin;
    private final int amountOfPolesMax;
    private final double poleSpacing;
    private final double longitude;
    private final double latitude;
    private final LinkedHashMap<String, Double> monthIradVals;
    
    private String logFileName = null;
    private String filePath = "test/"; //path in windows should be formated as: "C:\\Users\\username\\Desktop\\"
    
    public Environment(SolarPanel solarPanel, double sensorWatts,
                                int ledPowMin,
                                int ledPowMax,
                                double battEff,
                                int speedLimitMin,
                                int speedLimitMax,
                                int amountOfCarsMin,
                                int amountOfCarsMax,
                                int amountOfPolesMin,
                                int amountOfPolesMax,
                                double poleSpacing,
                                double longitude,
                                double latitude,
                                LinkedHashMap<String, Double> monthIradVals
                                ){
        
        this.solarPanel = solarPanel;
        this.sensorWatts = sensorWatts;
        this.ledPowMin = ledPowMin;
        this.ledPowMax = ledPowMax;
        this.speedLimitMin = speedLimitMin;
        this.speedLimitMax = speedLimitMax;
        this.amountOfCarsMin = amountOfCarsMin;
        this.amountOfCarsMax = amountOfCarsMax;
        this.amountOfPolesMin = amountOfPolesMin;
        this.amountOfPolesMax = amountOfPolesMax;
        this.poleSpacing = poleSpacing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.monthIradVals = monthIradVals;
        this.battEff = battEff;
        
        System.out.println("Environment Initialized");
    }
    
    public LinkedHashMap<String, Double> runSimulation(){
        double pmpp = solarPanel.getPmpp();
        LinkedHashMap<String, Double> results = new LinkedHashMap<>();
        
        for (String month : monthIradVals.keySet()) {
            double monthIrad = monthIradVals.get(month);
            
            if (monthIrad < 1000) {
              pmpp = calcPmpp(monthIrad);
            }
            double dayTime = lookUpDaytime(this.latitude, this.longitude, 
                    getFormattedDate(month, "15"));
            double nightTime = 24 - dayTime;
            double sensorConsumpitonMax = amountOfPolesMax * nightTime * sensorWatts;
            double sensorConsumpitonMin = amountOfPolesMin * nightTime * sensorWatts;
            //corresponds to P_avail in the formula pdf (without battery modifier)
            double availWattsMaxPoles = (dayTime * pmpp) * battEff - sensorConsumpitonMax;
            double availWattsMinPoles = (dayTime * pmpp) * battEff - sensorConsumpitonMin;
            //worst case most poles (max led power, most cars, slowest cars)
            double worstCaseMaxPoles = calcConsumption(ledPowMax, amountOfPolesMax, poleSpacing, 
                    speedLimitMin, amountOfCarsMax);
            //worst case most poles (max led power, most cars, slowest cars)
            double bestCaseMaxPoles = calcConsumption(ledPowMin, amountOfPolesMax, poleSpacing, 
                    speedLimitMax, amountOfCarsMin);
            //worst case most poles (max led power, most cars, slowest cars)
            double worstCaseMinPoles = calcConsumption(ledPowMax, amountOfPolesMin, poleSpacing, 
                    speedLimitMin, amountOfCarsMax);
            //worst case most poles (max led power, most cars, slowest cars)
            double bestCaseMinPoles = calcConsumption(ledPowMin, amountOfPolesMin, poleSpacing, 
                    speedLimitMax, amountOfCarsMin);
            
            results.put(month + " worstCaseMaxPoles ", availWattsMaxPoles - worstCaseMaxPoles);
            results.put(month + " bestCaseMaxPoles ", availWattsMaxPoles - bestCaseMaxPoles);
            results.put(month + " worstCaseMinPoles ", availWattsMinPoles - worstCaseMinPoles);
            results.put(month + " bestCaseMinPoles ", availWattsMinPoles - bestCaseMinPoles);
        }
        
        
        return results;
    }
    
    /*
    * corresponds to P_led(max/min) in the formula pdf
    */
    private double calcConsumption(double ledWatts, int noOfLeds, double ledSpacing,
            int carSpeed, int noOfCars) {
        double onTimePerCar = ledSpacing * (noOfLeds - 1) / carSpeed;
        return (noOfCars * ledWatts * onTimePerCar);
    }
    
    private double calcPmpp(double irad) {
        double closestIrad = 0.0;
        double prevIradDiff = Double.MAX_VALUE;
        
        for (Double k : solarPanel.getIradModifiers().keySet()) {
            double iradDiff = Math.abs(k - irad);
            if (prevIradDiff > iradDiff) {
                prevIradDiff = iradDiff;
                closestIrad = k;
            }
        }
        ArrayList<Double> pmppModifs = solarPanel.getIradModifiers().get(closestIrad);
        double vmod = solarPanel.getVmpp() * pmppModifs.get(0) / 100;
        double imod = solarPanel.getImpp() * pmppModifs.get(1) / 100;
        double pmpp = (solarPanel.getImpp() + imod) * (solarPanel.getVmpp() + vmod); 
        return pmpp;
    }
  
    private String getFormattedDate(String month, String day) {
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        SimpleDateFormat retForm = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH)
                    .parse(year + "-" + month + "-" + day);
        } catch (java.text.ParseException e) {
            System.err.println("Unable to parse provided month");
        }
        return retForm.format(date);
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
                            double totalHours = hours + minutes/60 + seconds/(60*60);
                            
                            return totalHours;
                        }else if(content.get("status").equals("INVALID_REQUEST")){
                            System.err.println("Error: INVALID_REQUEST");
                            return -1;
                        }else if(content.get("status").equals("INVALID_DATE")){
                            System.err.println("Error: INVALID_DATE");
                            return -1;
                        }else{
                            System.err.println("Error: UNKNOWN_ERROR");
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
    
    /**
     * For demonstration purposes only
     * @param data
     */
    public void writeLog(LinkedHashMap<String, Double> data) {

        data.entrySet().forEach((_item) -> {
            appendToLog(_item.toString(), this.filePath);
        });
    }

    /**
     * prints string to a log file.
     * @param message
     * @param filePath 
     */
    public void appendToLog(String message, String filePath) {

        File file;
        FileWriter fw;

        if (logFileName == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime now = LocalDateTime.now();
            this.logFileName = "IntelliLog" + dtf.format(now) + ".txt"; //2016/11/16 12:08:43
        }
        try {
            file = new File(filePath + this.logFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(message);
            fw.write("\r\n");
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
