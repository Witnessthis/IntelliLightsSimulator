/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator;

import intellilightssimulator.Environment.Environment;
import intellilightssimulator.Hardware.EnergyPackage.SolarPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author emilj
 */
public class IntelliLightsSimulator {

       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Environment env = getEnvFromConfig(args[0]);
        env.runSimulation(); //does nothing at the moment.
    }
    
     /**
     * @param configPath the path to the configuration file
     */
    private static Environment getEnvFromConfig(String configPath) {
        String months = "jan feb mar jun jul aug sep oct nov dec";
        Pattern modPatt = Pattern.compile("mod[0-9]");

        int solarPanelDimensionXmm = 0;
        int solarPanelDimensionYmm = 0;
        int ledEnergyCostMin = 0;
        int ledEnergyCostMax = 0;
        int ledPowMin = 0;
        int ledPowMax = 0;
        int speedLimitMin = 0;
        int speedLimitMax = 0;
        int amountOfCarsMin = 0;
        int amountOfCarsMax = 0;
        int amountOfPolesMin = 0;
        int amountOfPolesMax = 0;
        double Vmpp = 0.0;
        double Impp = 0.0;
        double pmpp = 0.0;
        double sensorModuleEnergyCostMin = 0.0;
        double sensorModuleEnergyCostMax = 0.0;
        double longitude = 0.0;
        double latitude = 0.0;
        String date = "";
        ArrayList<Double> monthIradVals = new ArrayList<>(12);
        HashMap<Double, ArrayList<Double>> iradModifiers = new HashMap<>();
        Environment env = null;
        
        BufferedReader br = null;
        String control = "";
        String[] parameters;
        String[] vals;

        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(configPath));
            
            while((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.equals("") || sCurrentLine.charAt(0) == '#') {
                    continue;
                }
                
                parameters = sCurrentLine.split("\\s+");
                control = parameters[0];
                
                if (control.equals("solarPanelDimension")) {
                    vals = parameters[1].split(":");
                    solarPanelDimensionXmm = Integer.parseInt(vals[0]);
                    solarPanelDimensionYmm = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + solarPanelDimensionXmm 
                            + ":" + solarPanelDimensionYmm);
                }
                else if (control.equals("Vmpp")) {
                    Vmpp = Double.parseDouble(parameters[1]);
                    System.out.println(control + " : " + Vmpp);
                }
                else if (control.equals("Impp")) {
                    Impp = Double.parseDouble(parameters[1]);
                    System.out.println(control + " : " + Impp);
                }
                else if (control.equals("Pmpp")) {
                    pmpp = Double.parseDouble(parameters[1]);
                    System.out.println(control + " : " + pmpp);
                }
                else if (control.equals("sensorModuleEnergyCost")) {
                    vals = parameters[1].split(":");
                    sensorModuleEnergyCostMin = Double.parseDouble(vals[0]);
                    sensorModuleEnergyCostMax = Double.parseDouble(vals[1]);
                    System.out.println(control + " : " + sensorModuleEnergyCostMin 
                            + ":" + sensorModuleEnergyCostMax);
                }
                else if (control.equals("ledEnergyCost")) {
                    vals = parameters[1].split(":");
                    ledEnergyCostMin = Integer.parseInt(vals[0]);
                    ledEnergyCostMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + ledEnergyCostMin + ":" 
                            + ledEnergyCostMax);
                }
                else if (control.equals("P_led")) {
                    vals = parameters[1].split(":");
                    ledPowMin = Integer.parseInt(vals[0]);
                    ledPowMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + ledPowMin + ":" 
                            + ledPowMax);
                }
                else if (control.equals("date")) {
                    date = parameters[1];
                    System.out.println(control + " : " + date);
                }
                else if (control.equals("speedLimit")) {
                    vals = parameters[1].split(":");
                    speedLimitMin = Integer.parseInt(vals[0]);
                    speedLimitMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + speedLimitMin 
                            + ":" + speedLimitMax);
                }
                else if (control.equals("amountOfCars")) {
                    vals = parameters[1].split(":");
                    amountOfCarsMin = Integer.parseInt(vals[0]);
                    amountOfCarsMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + amountOfCarsMin 
                            + ":" + amountOfCarsMax);
                }
                else if (control.equals("amountOfPoles")) {
                    vals = parameters[1].split(":");
                    amountOfPolesMin = Integer.parseInt(vals[0]);
                    amountOfPolesMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + amountOfPolesMin 
                            + ":" + amountOfPolesMax);
                }
                else if (control.equals("longitude")) {
                    longitude = Double.parseDouble(parameters[1]);
                    System.out.println(control + " : " + longitude);
                }
                else if (control.equals("latitude")) {
                    latitude = Double.parseDouble(parameters[1]);
                    System.out.println(control + " : " + latitude);
                }
                else if (months.contains(control)) {
                    monthIradVals.add(Double.parseDouble(parameters[1]));
                    System.out.println(control + " : " + parameters[1]);
                } 
                else if (modPatt.matcher(control).find()) {
                    vals = parameters[1].split(":");
                    ArrayList<Double> tmp = new ArrayList<>(2);
                    tmp.add(Double.parseDouble(vals[1]));
                    tmp.add(Double.parseDouble(vals[2]));
                    iradModifiers.put(Double.parseDouble(vals[0]), tmp);
                    System.out.println(parameters[1]);
                }
            }
            SolarPanel solarPanel = new SolarPanel(Vmpp, Impp, pmpp, 
                    solarPanelDimensionXmm, solarPanelDimensionYmm, iradModifiers);
            
            env = new Environment(solarPanel, sensorModuleEnergyCostMin, 
                    sensorModuleEnergyCostMax, ledEnergyCostMin, 
                    ledEnergyCostMax, ledPowMin, ledPowMax, 
                    speedLimitMin, speedLimitMax, amountOfCarsMin, amountOfCarsMax, 
                    amountOfPolesMin, amountOfPolesMax, longitude, latitude, 
                    date, monthIradVals);
            
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
		if (br != null)br.close();
            } catch (IOException ex) {
		ex.printStackTrace();
            }
        }
        return env;
    }
}