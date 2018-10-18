/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author JLErickso
 */
public class NewChessGame {

private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.Chess");

    /** The program's running title, prefix only. */
    private static final String TITLE_PREFIX = "October Chess";

    /**
     * Hidden constructor.
     */
    private NewChessGame() {
    }

    /**
     * The main method of the Chess game application.
     *
     * @param args command line arguments
     */
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
        new ChessFrame();
    }

    /**
     * Returns the full title for the program, including version number.
     *
     * @return the title of the program
     */
    public static String getTitle() {
        String version = "";
        try {
            InputStream s = NewChessGame.class.getResourceAsStream("/version.txt");
            Reader isr = new InputStreamReader(s, "UTF-8");
            BufferedReader in = new BufferedReader(isr);
            version = in.readLine();
            in.close();
        } catch (java.io.IOException e) {
            LOG.warning("failed to read version info");
            version = "";
        }
        return TITLE_PREFIX + " " + version;
    }
    
}
