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
        double batteryCapacity = 0;
        double sensorModuleEnergyCost = 0;
        int ledEnergyCost = 0;
        int nightDuration = 0;
        int speedLimit = 0;
        int LEDVoltage = 0;
        
        BufferedReader br = null;
        String control = "";
        String[] parameters;
        
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
                
                System.out.println(control + " : " + parameters[1]);
                
                switch(control){
                    case "solarPanelDimensionXmm":
                        solarPanelDimensionXmm = Integer.parseInt(parameters[1]);
                        break;
                    case "solarPanelDimensionYmm":
                        solarPanelDimensionYmm = Integer.parseInt(parameters[1]);
                        break;
                    case "Vmpp":
                        Vmpp = Double.parseDouble(parameters[1]);
                        break;
                    case "Impp":
                        Impp = Double.parseDouble(parameters[1]);
                        break;
                    case "batteryCapacity":
                        batteryCapacity = Double.parseDouble(parameters[1]);
                        break;
                    case "sensorModuleEnergyCost":
                        sensorModuleEnergyCost = Double.parseDouble(parameters[1]);
                        break;
                    case "ledEnergyCost":
                        ledEnergyCost = Integer.parseInt(parameters[1]);
                        break;
                    case "nightDuration":
                        nightDuration = Integer.parseInt(parameters[1]);
                        break;
                    case "speedLimit":
                        speedLimit = Integer.parseInt(parameters[1]);
                        break;
                    case "LEDVoltage":
                        LEDVoltage = Integer.parseInt(parameters[1]);
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
        env.initEnvironment(solarPanelDimensionXmm,
                            solarPanelDimensionYmm,
                            Vmpp, Impp,
                            batteryCapacity,
                            sensorModuleEnergyCost,
                            ledEnergyCost,
                            nightDuration,
                            speedLimit,
                            LEDVoltage);
        env.runSimulation(); //does nothing at the moment.
    }
    
}
