/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator;

import intellilightssimulator.Environment.Environment;
import intellilightssimulator.Environment.SolarPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        LinkedHashMap<String, Double> simResults = env.runSimulation();
        env.writeLog(simResults);
    }
    
     /**
     * @param configPath the path to the configuration file
     */
    private static Environment getEnvFromConfig(String configPath) {
        String months = "jan feb mar apr may jun jul aug sep oct nov dec";
        Pattern modPatt = Pattern.compile("pmppMod[0-9]");

        double sensorWattsMin = 0;
        double sensorWattsMax = 0;
        int ledWattsMin = 0;
        int ledWattsMax = 0;
        int speedLimitMin = 0;
        int speedLimitMax = 0;
        int amountOfCarsMin = 0;
        int amountOfCarsMax = 0;
        int amountOfPolesMin = 0;
        int amountOfPolesMax = 0;
        double poleSpacing = 0.0;
        double vmpp = 0.0;
        double Impp = 0.0;
        double pmpp = 0.0;
        double longitude = 0.0;
        double latitude = 0.0;
        double battEff = 0.0;
        LinkedHashMap<String, Double> monthIradVals = new LinkedHashMap<>(12);
        HashMap<Double, ArrayList<Double>> iradModifiers = new HashMap<>();
        Environment env = null;
        
        BufferedReader br = null;
        String control;
        String[] params;
        String[] vals;

        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(configPath));
            
            while((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.equals("") || sCurrentLine.charAt(0) == '#') {
                    continue;
                }
                
                params = sCurrentLine.split("\\s+");
                control = params[0];
                
                if (control.equals("vmpp")) {
                    vmpp = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + vmpp);
                }
                else if (control.equals("impp")) {
                    Impp = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + Impp);
                }
                else if (control.equals("pmpp")) {
                    pmpp = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + pmpp);
                }
                else if (control.equals("sensorModuleWatts")) {
                    vals = params[1].split(":");
                    sensorWattsMin = Double.parseDouble(vals[0]);
                    sensorWattsMax = Double.parseDouble(vals[1]);
                    System.out.println(control + " : " + sensorWattsMin + ":" 
                            + sensorWattsMax);
                }
                else if (control.equals("ledWatts")) {
                    vals = params[1].split(":");
                    ledWattsMin = Integer.parseInt(vals[0]);
                    ledWattsMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + ledWattsMin + ":" 
                            + ledWattsMax);
                }
                else if(control.equals("battEff")) {
                    battEff = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + battEff);
                }
                else if (control.equals("speedLimit")) {
                    vals = params[1].split(":");
                    speedLimitMin = Integer.parseInt(vals[0]);
                    speedLimitMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + speedLimitMin 
                            + ":" + speedLimitMax);
                }
                else if (control.equals("amountOfCars")) {
                    vals = params[1].split(":");
                    amountOfCarsMin = Integer.parseInt(vals[0]);
                    amountOfCarsMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + amountOfCarsMin 
                            + ":" + amountOfCarsMax);
                }
                else if (control.equals("amountOfPoles")) {
                    vals = params[1].split(":");
                    amountOfPolesMin = Integer.parseInt(vals[0]);
                    amountOfPolesMax = Integer.parseInt(vals[1]);
                    System.out.println(control + " : " + amountOfPolesMin 
                            + ":" + amountOfPolesMax);
                }
                else if (control.equals("poleSpacing")) {
                    poleSpacing = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + poleSpacing);
                }
                else if (control.equals("longitude")) {
                    longitude = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + longitude);
                }
                else if (control.equals("latitude")) {
                    latitude = Double.parseDouble(params[1]);
                    System.out.println(control + " : " + latitude);
                }
                else if (months.contains(control)) {
                    monthIradVals.put(control, Double.parseDouble(params[1]));
                    System.out.println(params[0]);
                } 
                else if (modPatt.matcher(control).find()) {
                    vals = params[1].split(":");
                    ArrayList<Double> tmp = new ArrayList<>(2);
                    tmp.add(Double.parseDouble(vals[1]));
                    tmp.add(Double.parseDouble(vals[2]));
                    iradModifiers.put(Double.parseDouble(vals[0]), tmp);
                    System.out.println(params[1]);
                }
            }
            SolarPanel solarPanel = new SolarPanel(vmpp, Impp, pmpp, iradModifiers);
            
            env = new Environment(solarPanel, sensorWattsMin, 
                    sensorWattsMax, ledWattsMin, ledWattsMax, battEff, 
                    speedLimitMin, speedLimitMax, amountOfCarsMin, amountOfCarsMax, 
                    amountOfPolesMin, amountOfPolesMax, poleSpacing, longitude, latitude, monthIradVals);
            
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