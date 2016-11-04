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
    
    private final float Vmpp;
    private final float Impp;
    private final int XDimensions;
    private final int YDimensions;
    
    public SolarPanel(float Vmpp, float Impp, int XDimensions, int YDimensions ){
        this.Vmpp = Vmpp;
        this.Impp = Impp;
        this.XDimensions = XDimensions;
        this.YDimensions = YDimensions;
    }
    
    public int getCharge(int startTime, int endTime) {
        return 1; //calculation for time-period here.
    }
}
