/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author JLErickso
 */

public class GUIFrame extends JFrame
        implements ComponentListener, GameListener {

    private final Panel display;

    private final Bar progress;
    
    private Rules game;

   
    public GUIFrame() {
        
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Images.getTile("King.WHITE"));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        MenuHandler handler = new MenuHandler(this);
        handler.setUpMenu();

        display = new Panel(new EmptyBoard());
        progress = new Bar(null);
        add(display);
        add(progress);
        pack();

        addComponentListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public final void newGame() {
        ChessGame ngFrame = new ChessGame(this);
        ngFrame.setVisible(true);
        Rules newGame = ngFrame.getGame();
        if (newGame == null) {
            return;
        }
        if (game != null) {
            game.end();
        }
        game = newGame;
        Board board = game.getBoard();
        display.setBoard(board);
        display.invalidate();
        setSize(getPreferredSize());

        progress.setGame(game);
        game.addGameListener(this);
        game.addGameListener(display);
        game.start();
    }

    public final Player getPlayer() {
        return display;
    }

    private class MenuHandler implements ActionListener {

        private JMenu game;

        private final GUIFrame frame;
        
        public MenuHandler(final GUIFrame parent) {
            frame = parent;
        }

        @Override
        public final void actionPerformed(final ActionEvent e) {
            if ("New Game".equals(e.getActionCommand())) {
                frame.newGame();
            } else if ("Exit".equals(e.getActionCommand())) {
                System.exit(0);
            }
        }

       
        public final void setUpMenu() {
            JMenuBar menuBar = new JMenuBar();

            game = new JMenu("Game");
            game.setMnemonic('G');
            JMenuItem newGame = new JMenuItem("New Game");
            newGame.addActionListener(this);
            newGame.setMnemonic('N');
            game.add(newGame);
            game.add(new JSeparator());
            JMenuItem exitGame = new JMenuItem("Exit");
            exitGame.addActionListener(this);
            exitGame.setMnemonic('x');
            game.add(exitGame);
            menuBar.add(game);

            setJMenuBar(menuBar);
        }
    }

    @Override
    public final void componentResized(final ComponentEvent e) {
        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
           
            return;
        }
        double ratio = display.getRatio();
        double barh = progress.getPreferredSize().getHeight();
        Container p = getContentPane();
        Dimension d = null;
        if (p.getWidth() * ratio < (p.getHeight() - barh)) {
            d = new Dimension((int) ((p.getHeight() - barh) * ratio),
                              p.getHeight());
        } else if (p.getWidth() * ratio > (p.getHeight() - barh)) {
            d = new Dimension(p.getWidth(),
                              (int) (p.getWidth() / ratio + barh));
        }
        if (d != null) {
            p.setPreferredSize(d);
            pack();
        }
    }

    @Override
    public final void gameEvent(final GameEvent e) {
        progress.repaint();
    }

    @Override
    public void componentHidden(final ComponentEvent e) {
       
    }

    @Override
    public void componentMoved(final ComponentEvent e) {
    
    }

    @Override
    public void componentShown(final ComponentEvent e) {
       
    }
}
