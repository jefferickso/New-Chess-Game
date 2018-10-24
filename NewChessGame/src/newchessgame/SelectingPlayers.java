/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;

/**
 *
 * @author JLErickso
 */
public class SelectingPlayers extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final String[] LABELS_AI = {
        "Fool (2 plies)",
        "Very Easy (3 plies)",
        "Easy (4 plies)",
        "Medium (5 plies)",
        "Hard (6 plies)",
        "Harder (7 plies)",
        "Challenging (8 plies)"
    };

    private static final String[] NAMES_AI = {
        "depth2", "depth3", "depth4", "depth5", "depth6", "depth7", "depth8",
    };

    private static final int DEFAULT_AI = 2;

    private final JRadioButton human = new JRadioButton("Human");;

    private final JRadioButton minimax = new JRadioButton("Computer");
    
    private final JList ai = new JList(LABELS_AI);

    static final int V_PADDING = 15;


    static final int H_PADDING = 10;
    
    public SelectingPlayers(final String title, final boolean humanSet) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(title);
        add(label);

     
        ButtonGroup group = new ButtonGroup();
        group.add(human);
        group.add(minimax);
        human.setSelected(humanSet);
        minimax.setSelected(!humanSet);
        ai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ai.setSelectedIndex(DEFAULT_AI);
        ai.setEnabled(!humanSet);

        human.setAlignmentX(Component.LEFT_ALIGNMENT);
        minimax.setAlignmentX(Component.LEFT_ALIGNMENT);
        ai.setAlignmentX(Component.LEFT_ALIGNMENT);

     
        human.addActionListener(new ActionListener() {
            public final void actionPerformed(final ActionEvent e) {
                ai.setEnabled(!human.isSelected());
            }
        });
        minimax.addActionListener(new ActionListener() {
            public final void actionPerformed(final ActionEvent e) {
                ai.setEnabled(minimax.isSelected());
            }
        });

        add(human);
        add(minimax);
        add(ai);
        setBorder(BorderFactory.createEmptyBorder(H_PADDING, V_PADDING,
                  H_PADDING, V_PADDING));
    }


    public final String getPlayer() {
        if (human.isSelected()) {
            return "human";
        } else {
            int i = ai.getSelectedIndex();
            if (i < 0) {
                return "default";
            } else {
                return NAMES_AI[i];
            }
        }
    }
}
