/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;


import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author JLErickso
 */
public class NewChessGame {

private static final Logger LOG =
        Logger.getLogger("newchessgame");

    private NewChessGame() {
    }

    public static void main(final String[] args) {
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
        new GUIFrame();
    }



    
}
