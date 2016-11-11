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
public class LED {

    private final int powerConsumption;
    private final int voltage;
    
    public LED(int powerConsumption, int voltage) {
        this.powerConsumption = powerConsumption;
        this.voltage = voltage;
    }
    
    public int getPowerConsumption() {
        return powerConsumption;
    }
    
    public int getVoltage() {
        return voltage;
    }
}
