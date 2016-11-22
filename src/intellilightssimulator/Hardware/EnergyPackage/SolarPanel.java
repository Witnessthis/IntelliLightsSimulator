/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Hardware.EnergyPackage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author emilj
 */
public class SolarPanel {
    private final double Vmpp;
    private final double Impp;
    private final int XDimensions;
    private final int YDimensions;
    private final HashMap<Double, ArrayList<Double>> iradModifiers;
    
    public SolarPanel(double Vmpp, double Impp, int XDimensions, int YDimensions,
            HashMap<Double, ArrayList<Double>> iradModifiers){
        this.Vmpp = Vmpp;
        this.Impp = Impp;
        this.XDimensions = XDimensions;
        this.YDimensions = YDimensions;
        this.iradModifiers = iradModifiers;
    }
    
     /**
     * @return the Vmpp
     */
    public double getVmpp() {
        return Vmpp;
    }

    /**
     * @return the Impp
     */
    public double getImpp() {
        return Impp;
    }
    
    public HashMap<Double, ArrayList<Double>> getIradModifiers() {
        return this.iradModifiers;
    }
    
    public double getCharge(int startTime, int endTime) {
        return 1; //calculation for time-period here.
    }
}
