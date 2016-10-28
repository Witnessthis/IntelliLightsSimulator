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

    public LED(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
    
    public int getPowerConsumption() {
        return powerConsumption;
    }
}
