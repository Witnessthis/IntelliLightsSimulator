/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intellilightssimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nanna Dohn
 */
public class InteractWithAPI {

    /**
     * @param args the command line arguments
     */
    public void lookUpDaytime() {

        Scanner scanner = new Scanner(System.in);

        float latitude = scanner.nextFloat();
        float longitude = scanner.nextFloat();

        URL url;
        try {
            url = new URL("http://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude + "");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(InteractWithAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(InteractWithAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
