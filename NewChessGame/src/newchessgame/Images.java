/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.imageio.ImageIO;



/**
 *
 * @author JLErickso
 */
public class Images {
    
    
    private static final Logger LOG =
        Logger.getLogger("newchessgame");


    private static Map<String, Image> cache =
        new HashMap<String, Image>();


    private Images() {
    }

  
    public static Image getTile(final String name) {
        
        
        Image cached = cache.get(name);
        if (cached != null) {
            return cached;
        }

        String file = name + ".png";
        try {
            Image i = ImageIO.read(Images.class.getResource(file));
            cache.put(name, i);
            return i;
        } catch (java.io.IOException e) {
            String message = "Failed to read image: " + file + ": " + e;
            LOG.severe(message);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            String message = "Failed to find image: " + file + ": " + e;
            LOG.severe(message);
            System.exit(1);
        }
        return null;
    }
}
