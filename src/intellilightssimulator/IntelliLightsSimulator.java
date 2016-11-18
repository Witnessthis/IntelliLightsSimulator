/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator;

import intellilightssimulator.Environment.Environment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author emilj
 */
public class IntelliLightsSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int solarPanelDimensionXmm = 0;
        int solarPanelDimensionYmm = 0;
        double Vmpp = 0, Impp = 0;
        double sensorModuleEnergyCostMin = 0;
        double sensorModuleEnergyCostMax = 0;
        int ledEnergyCostMin = 0;
        int ledEnergyCostMax = 0;
        int LEDVoltageMin = 0;
        int LEDVoltageMax = 0;
        int startTimeHours = 0;
        int startTimeMinutes = 0;
        int stopTimeHours = 0;
        int stopTimeMinutes = 0;
        int speedLimitMin = 0;
        int speedLimitMax = 0;
        int amountOfCarsMin = 0;
        int amountOfCarsMax = 0;
        int amountOfPolesMin = 0;
        int amountOfPolesMax = 0;
        double longitude = 0;
        double latitude = 0;
        double jan = 0;
        double feb = 0;
        double mar = 0;
        double apr = 0;
        double may = 0;
        double jun = 0;
        double jul = 0;
        double aug = 0;
        double sep = 0;
        double oct = 0;
        double nov = 0;
        double dec = 0;
        
        BufferedReader br = null;
        String control = "";
        String[] parameters;
        String[] vals;
        
        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(args[0]));
            
            while((sCurrentLine = br.readLine()) != null){
                
//                if(sCurrentLine.equals("\r\n") || sCurrentLine.equals("\n\r")|| sCurrentLine.equals("\n") || sCurrentLine.equals("\r"))
//                    continue;
                if(sCurrentLine.equals("") ||
                    sCurrentLine.charAt(0) == '#'
                    )
                    continue;
                
                parameters = sCurrentLine.split("\\s+");
                control = parameters[0];

                switch(control){
                    case "solarPanelDimension":
                        vals = parameters[1].split(":");
                        solarPanelDimensionXmm = Integer.parseInt(vals[0]);
                        solarPanelDimensionYmm = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + solarPanelDimensionXmm + ":" + solarPanelDimensionYmm);
                        break;
                    case "Vmpp":
                        Vmpp = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + Vmpp);
                        break;
                    case "Impp":
                        Impp = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + Impp);
                        break;
                    case "sensorModuleEnergyCost":
                        vals = parameters[1].split(":");
                        sensorModuleEnergyCostMin = Double.parseDouble(vals[0]);
                        sensorModuleEnergyCostMax = Double.parseDouble(vals[1]);
                        System.out.println(control + " : " + sensorModuleEnergyCostMin + ":" + sensorModuleEnergyCostMax);
                        break;
                    case "ledEnergyCost":
                        vals = parameters[1].split(":");
                        ledEnergyCostMin = Integer.parseInt(vals[0]);
                        ledEnergyCostMax = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + ledEnergyCostMin + ":" + ledEnergyCostMax);
                        break;
                    case "LEDVoltage":
                        vals = parameters[1].split(":");
                        LEDVoltageMin = Integer.parseInt(vals[0]);
                        LEDVoltageMax = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + LEDVoltageMin + ":" + LEDVoltageMax);
                        break;
                    case "nightDuration":
                        String[] start;
                        String[] stop;
                        vals = parameters[1].split(":");
                        start = vals[0].split("-");
                        stop = vals[1].split("-");
                        startTimeHours = Integer.parseInt(start[0]);
                        startTimeMinutes = Integer.parseInt(start[1]);
                        stopTimeHours = Integer.parseInt(stop[0]);
                        stopTimeMinutes = Integer.parseInt(stop[1]);
                        System.out.println(control + " : " + startTimeHours + "-" + startTimeMinutes + ":" + stopTimeHours + "-" + stopTimeMinutes);
                        break;
                    case "speedLimit":
                        vals = parameters[1].split(":");
                        speedLimitMin = Integer.parseInt(vals[0]);
                        speedLimitMax = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + speedLimitMin + ":" + speedLimitMax);
                        break;
                    case "amountOfCars":
                        vals = parameters[1].split(":");
                        amountOfCarsMin = Integer.parseInt(vals[0]);
                        amountOfCarsMax = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + amountOfCarsMin + ":" + amountOfCarsMax);
                        break;
                    case "amountOfPoles":
                        vals = parameters[1].split(":");
                        amountOfPolesMin = Integer.parseInt(vals[0]);
                        amountOfPolesMax = Integer.parseInt(vals[1]);
                        System.out.println(control + " : " + amountOfPolesMin + ":" + amountOfPolesMax);
                        break;
                    case "longitude":
                        longitude = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + longitude);
                        break;
                    case "latitude":
                        latitude = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + latitude);
                        break;
                    case "jan":
                        jan = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + jan);
                        break;
                    case "feb":
                        feb = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + feb);
                        break;
                    case "mar":
                        mar = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + mar);
                        break;
                    case "apr":
                        apr = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + apr);
                        break;
                    case "may":
                        may = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + may);
                        break;
                    case "jun":
                        jun = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + jun);
                        break;
                    case "jul":
                        jul = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + jul);
                        break;
                    case "aug":
                        aug = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + aug);
                        break;
                    case "sep":
                        sep = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + sep);
                        break;
                    case "oct":
                        oct = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + oct);
                        break;
                    case "nov":
                        nov = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + nov);
                        break;
                    case "dec":
                        dec = Double.parseDouble(parameters[1]);
                        System.out.println(control + " : " + dec);
                        break;
                    default:
                        break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
		if (br != null)br.close();
            } catch (IOException ex) {
		ex.printStackTrace();
            }
        }
        
        Environment env = new Environment();
        env.initSimulation(solarPanelDimensionXmm,
                            solarPanelDimensionYmm,
                            Vmpp, Impp,
                            sensorModuleEnergyCostMin,
                            sensorModuleEnergyCostMax,
                            ledEnergyCostMin,
                            ledEnergyCostMax,
                            LEDVoltageMin,
                            LEDVoltageMax,
                            startTimeHours,
                            startTimeMinutes,
                            stopTimeHours,
                            stopTimeMinutes,
                            speedLimitMin,
                            speedLimitMax,
                            amountOfCarsMin,
                            amountOfCarsMax,
                            amountOfPolesMin,
                            amountOfPolesMax,
                            longitude,
                            latitude,
                            jan,
                            feb,
                            mar,
                            apr,
                            may,
                            jun,
                            jul,
                            aug,
                            sep,
                            oct,
                            nov,
                            dec
                            );
        env.runSimulation(); //does nothing at the moment.
    }
}