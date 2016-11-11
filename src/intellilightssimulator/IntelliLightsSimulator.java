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
        float Vmpp = 0, Impp = 0;
        int batteryCapacity = 0;
        int sensorModuleEnergyCost = 0;
        int ledEnergyCost = 0;
        
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
                
                switch(control){
                    case "solarPanelDimensionXmm":
                        solarPanelDimensionXmm = Integer.parseInt(parameters[1]);
                        break;
                    case "solarPanelDimensionYmm":
                        solarPanelDimensionYmm = Integer.parseInt(parameters[1]);
                        break;
                    case "Vmpp":
                        Vmpp = Integer.parseInt(parameters[1]);
                        break;
                    case "Impp":
                        Impp = Integer.parseInt(parameters[1]);
                        break;
                    case "batteryCapacity":
                        batteryCapacity = Integer.parseInt(parameters[1]);
                        break;
                    case "sensorModuleEnergyCost":
                        sensorModuleEnergyCost = Integer.parseInt(parameters[1]);
                        break;
                    case "ledEnergyCost":
                        ledEnergyCost = Integer.parseInt(parameters[1]);
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
                            ledEnergyCost);
        env.runSimulation(); //does nothing at the moment.
    }
    
}
