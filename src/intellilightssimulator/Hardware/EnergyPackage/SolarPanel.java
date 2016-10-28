/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Hardware.EnergyPackage;

/**
 *
 * @author emilj
 */
public class SolarPanel {
    
    private final int chargeRate;
    
    public SolarPanel(int chargeRate){
        this.chargeRate = chargeRate;
    }
    
    public int getChargeRate() {
        return chargeRate;
    }
}
