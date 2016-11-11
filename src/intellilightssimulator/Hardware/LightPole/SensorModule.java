/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Hardware.LightPole;

/**
 *
 * @author emilj
 */
public class SensorModule {
    
    private final double powerConsumption;

    public SensorModule(double powerConsumption){
        this.powerConsumption = powerConsumption;
    }
    
    public double getPowerConsumption() {
        return powerConsumption;
    }
}
