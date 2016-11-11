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
    
    private int ticks;
    private int speedLimit;
    private int lightPoleDistance;
    
    public Environment() {
    }
    
    public void initEnvironment(int solarPanelDimensionXmm, 
                                int solarPanelDimensionYmm, 
                                double Vmpp, double Impp,
                                double batteryCapacity,
                                double sensorModuleEnergyCost, //total cost of the sensor module: MCU, transciever, sensor
                                int ledEnergyCost,
                                int nightDuration,
                                int speedLimit,
                                int LEDVoltage
                                ){
        battery = new Battery(batteryCapacity);
        solarPanel = new SolarPanel(Vmpp, Impp, solarPanelDimensionXmm, solarPanelDimensionYmm);
        sensorModule = new SensorModule(sensorModuleEnergyCost);
        led = new LED(ledEnergyCost, LEDVoltage);

        this.speedLimit = speedLimit;
        calculateTicks(nightDuration);
        
        //set lightPoleDistance in roadConfiguration initialization
        
        System.out.println("Environment Initialized");
    }
    
    public void runSimulation(){
        
        for(int i = 0; i < this.ticks; i++){
            
            // simulate one night
            
        }
    }
    
    public void calculateTicks(int nightDuration){
        
        //calculate amount of ticks based on night duration.
        
        this.ticks = 0;
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
}
