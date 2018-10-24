/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author JLErickso
 */
public class ChessGame extends JDialog implements ActionListener {
    
    private final GUIFrame parent;

    private final SelectingPlayers whitePanel;

    private final SelectingPlayers blackPanel;

    private final Selector boardPanel;
    
    static final int V_PADDING = 15;

    static final int H_PADDING = 10;

    private boolean cancelled = true;

    public ChessGame(final GUIFrame owner) {
        super(owner, "New game", true);
        parent = owner;
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        whitePanel = new SelectingPlayers("White:", true);
        blackPanel = new SelectingPlayers("Black:", false);
        add(whitePanel, BorderLayout.LINE_START);
        add(blackPanel, BorderLayout.CENTER);
        boardPanel = new Selector();
        add(boardPanel, BorderLayout.LINE_END);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(this);
        cancel.addActionListener(this);
        getRootPane().setDefaultButton(ok);
        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));
        buttonRow.setBorder(BorderFactory.createEmptyBorder(H_PADDING,
                            V_PADDING,
                            H_PADDING,
                            V_PADDING));
        buttonRow.add(Box.createHorizontalGlue());
        buttonRow.add(ok);
        buttonRow.add(cancel);
        add(buttonRow, BorderLayout.PAGE_END);

        pack();
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            cancelled = false;
        }
        setVisible(false);
        dispose();
    }

    private Player createPlayer(final Rules game, final String name) {
        if ("human".equals(name)) {
            return parent.getPlayer();
        } else {
            return new Minimax(game, name);
        }
    }

    private Board createBoard(final String name) {
        if ("chess".equals(name)) {
            return new StandardBoard();
        } 
        else {
            return null;
        }
    }

    public Rules getGame() {
        if (cancelled) {
            return null;
        }
        Rules game = new Rules(createBoard(boardPanel.getBoard()));
        Player white = createPlayer(game, whitePanel.getPlayer());
        Player black = createPlayer(game, blackPanel.getPlayer());
        game.seat(white, black);
        return game;
    }
}