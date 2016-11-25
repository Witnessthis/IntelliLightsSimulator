/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator.Environment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author emilj
 */
public class SolarPanel {
    private final double Vmpp;
    private final double Impp;
    private final double pmpp = 0;
    private final HashMap<Double, ArrayList<Double>> irradModifiers;
    
    public SolarPanel(double Vmpp, double Impp, 
            HashMap<Double, ArrayList<Double>> irradModifiers){
        this.Vmpp = Vmpp;
        this.Impp = Impp;
        this.irradModifiers = irradModifiers;
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

     /**
     * @return the Vmpp
     */
    public double getPmpp() {
        return pmpp;
    }
    
    public HashMap<Double, ArrayList<Double>> getIradModifiers() {
        return this.irradModifiers;
    }
}
