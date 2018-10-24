/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;

/**
 *
 * @author JLErickso
 */
public class Bar extends JComponent {

    private static long REPAINT_DELAY = 1000L;

    private static int MIN = 60;

    private Rules game;

    static Color BACKGROUND = new Color(0xAA, 0xAA, 0xAA);

    static Color BAR_COLOR = new Color(0x40, 0x00, 0xFF);

    static Color STATUS_COLOR = new Color(0x00, 0x00, 0x00);

    static int BAR_HEIGHT = 3;

    static int BAR_PADDING_Y = 3;

    static int BAR_PADDING_X = 10;

    private Timer timer = new Timer(true);

 
    public Bar(Rules observed) {
        super();
        setBackground(BACKGROUND);
        setPreferredSize(null);
        setMinimumSize(null);
        setMaximumSize(null);
        game = observed;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0L, REPAINT_DELAY);
    }

    public void setGame(Rules observed) {
        game = observed;
    }

    @Override
    public final Dimension getPreferredSize() {
        Graphics g = getGraphics();
        if (g != null) {
            FontMetrics fm = g.getFontMetrics();
            int bar = BAR_PADDING_Y * 2 + BAR_HEIGHT;
            return new Dimension(0, fm.getAscent() + bar);
        }
        return null;
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE,
                             (int) getPreferredSize().getHeight());
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (game == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g.getFontMetrics();
        int fh = fm.getAscent();

        g.setColor(BAR_COLOR);
        int barWidth = getWidth() - BAR_PADDING_X * 2;
        int progress = (int) (game.getProgress() * barWidth);
        g.fillRect(BAR_PADDING_X, fh + BAR_PADDING_Y,
                   progress, BAR_HEIGHT);

        double secs = game.getETA();
        String eta = " ";
        if (secs < Double.POSITIVE_INFINITY) {
            int min = (int) (secs / MIN);
            int sec = (int) (secs - min * MIN);
            eta = String.format(" (est %d:%02d)", min, sec);
        }

        String status = game.getStatus() + eta;
        g.setColor(STATUS_COLOR);
        int width = fm.stringWidth(status);
        int height = fm.getHeight();
        Font f = fm.getFont();
        g.setFont(f.deriveFont(Font.BOLD));
        g.drawString(status, getWidth() / 2 - width / 2, height);
    }
}
