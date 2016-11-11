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
public class Battery {
    
    //private final int chargeRate;
    private final double batteryCapacity;
    //private final int energyLossRatio; //?
    private double batteryCharge;

    public Battery(/*int chargeRate, */double batteryCapacity/*, int energyLossRatio*/){
        //this.chargeRate = chargeRate;
        this.batteryCapacity = batteryCapacity;
        //this.energyLossRatio = energyLossRatio;
        this.batteryCharge = 0;
    }
    
    public double getBatteryCharge() {
        return batteryCharge;
    }
    
    
    
}
