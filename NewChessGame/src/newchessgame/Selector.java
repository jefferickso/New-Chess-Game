/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author JLErickso
 */
public class Selector extends JPanel {


    private final JRadioButton standard;

    static final int V_PADDING = 15;

    static final int H_PADDING = 10;

    public Selector() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Game type:");
        add(label);

        standard = new JRadioButton("Standard");
        ButtonGroup group = new ButtonGroup();
        group.add(standard);
        standard.setSelected(true);

        add(standard);
    

        setBorder(BorderFactory.createEmptyBorder(H_PADDING, V_PADDING,
                  H_PADDING, V_PADDING));
    }

    public final String getBoard() {
        if (standard.isSelected()) {
            return "chess";
        } else {
            throw new AssertionError("Unknown board selected!");
        }
    }
}
