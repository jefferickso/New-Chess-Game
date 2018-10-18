/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author JLErickso
 */
public class Applet extends JApplet implements GameListener{
    
    private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.ChessApplet");


    @Override
    public void init() {
        try {
            String lnf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lnf);
        } catch (IllegalAccessException e) {
            LOG.warning("Failed to access 'Look and Feel'");
        } catch (InstantiationException e) {
            LOG.warning("Failed to instantiate 'Look and Feel'");
        } catch (ClassNotFoundException e) {
            LOG.warning("Failed to find 'Look and Feel'");
        } catch (UnsupportedLookAndFeelException e) {
            LOG.warning("Failed to set 'Look and Feel'");
        }

        StandardBoard board = new StandardBoard();
        BoardPanel panel = new BoardPanel(board);
        add(panel);
        Rules game = new Rules(board);
        game.seat(panel, new Minimax(game));
        game.addGameListener(this);
        game.addGameListener(panel);
        game.begin();
    }

    @Override
    public void gameEvent(final GameEvent e) {
        if (e.getGame().isDone()) {
            String message;
            Piece.Side winner = e.getGame().getWinner();
            if (winner == Piece.Side.WHITE) {
                message = "White wins";
            } else if (winner == Piece.Side.BLACK) {
                message = "Black wins";
            } else {
                message = "Stalemate";
            }
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    
    
}
