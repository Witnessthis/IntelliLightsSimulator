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
    private final double vmpp;
    private final double impp;
    private final double pmpp;
    private final HashMap<Double, ArrayList<Double>> irradModifiers;
    
    public SolarPanel(double vmpp, double impp, 
            HashMap<Double, ArrayList<Double>> irradModifiers){
        this.vmpp = vmpp;
        this.impp = impp;
        this.pmpp = vmpp * impp;
        this.irradModifiers = irradModifiers;
    }
    
     /**
     * @return the vmpp
     */
    public double getVmpp() {
        return vmpp;
    }

    /**
     * @return the impp
     */
    public double getImpp() {
        return impp;
    }

     /**
     * @return the pmpp
     */
    public double getPmpp() {
        return pmpp;
    }
    
    public HashMap<Double, ArrayList<Double>> getIradModifiers() {
        return this.irradModifiers;
    }
}
