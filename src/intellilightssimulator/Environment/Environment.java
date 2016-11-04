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
    
    public Environment() {
    }
    
    public void initEnvironment(int solarPanelDimensionXmm, 
                                int solarPanelDimensionYmm, 
                                float Vmpp, float Impp,
                                int batteryCapacity,
                                int sensorModuleEnergyCost, //total cost of the sensor module: MCU, transciever, sensor
                                int ledEnergyCost
                                ){
        battery = new Battery(batteryCapacity);
        solarPanel = new SolarPanel(Vmpp, Impp, solarPanelDimensionXmm, solarPanelDimensionYmm);
        sensorModule = new SensorModule(sensorModuleEnergyCost);
        led = new LED(ledEnergyCost);
        System.out.println("Environment Initialized");
    }
    
    public void runSimulation(){
        
    }
    
    
}
