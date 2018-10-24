/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author JLErickso
 */
public class AIConfig extends HashMap<String, Double> {

    static final String[] PLIST = {
        "depth", "Pawn", "Knight", "Bishop", "Rook", "Queen", "King",
          "material", "safety", "mobility"
    };

    
    public AIConfig() {
        super();
    }

    public AIConfig(final Properties props) {
        for (String prop : PLIST) {
            put(prop, Double.parseDouble(props.getProperty(prop)));
        }
    }

    public final Properties getProperties() {
        Properties props = new Properties();
        for (String prop : PLIST) {
            props.setProperty(prop, "" + get(prop));
        }
        return props;
    }

    @Override
    public final String toString() {
        StringBuilder str = new StringBuilder();
        for (String prop : PLIST) {
            str.append(prop + "=" + get(prop) + ",");
        }
        return str.toString();
    }
}
