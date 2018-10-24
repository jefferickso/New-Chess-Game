/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author JLErickso
 */
public class Panel extends JComponent
    implements MouseListener, Player, GameListener {

    private static final Logger LOG =
        Logger.getLogger("newchessgame");

    private static final double TILE_SIZE = 200.0;

    private static final Shape TILE =
        new Rectangle2D.Double(0, 0, TILE_SIZE, TILE_SIZE);

    static final int HIGHLIGHT_PADDING = 15;

    static final Stroke HIGHLIGHT_STROKE = new BasicStroke(12);

    private static final Shape HIGHLIGHT =
        new RoundRectangle2D.Double(HIGHLIGHT_PADDING, HIGHLIGHT_PADDING,
                                    TILE_SIZE - HIGHLIGHT_PADDING * 2,
                                    TILE_SIZE - HIGHLIGHT_PADDING * 2,
                                    HIGHLIGHT_PADDING * 4,
                                    HIGHLIGHT_PADDING * 4);

    private static final long serialVersionUID = 1L;

    private Board board;

    private boolean flipped = true;

    private Location selected = null;

    private AvailMove moves = null;

    static final Color DARK = new Color(0, 0, 255);

    static final Color LIGHT = new Color(255, 255, 255);

    static final Color SELECTED = new Color(0x00, 0xFF, 0xFF);

    static final Color MOVEMENT = new Color(0x7F, 0x00, 0x00);

    static final Color LAST = new Color(0x00, 0x7F, 0xFF);

    static final int MIN_SIZE = 25;

    static final int PREF_SIZE = 75;

    private Mode mode = Mode.WAIT;

    private Piece.Side side;

    private CountDownLatch latch;

    private Move selectedMove;

    private enum Mode {

        WAIT,

        PLAYER;
    }

  
    protected Panel() {
    }


    public Panel(final Board displayBoard) {
        board = displayBoard;
        updateSize();
        addMouseListener(this);
    }


    private void updateSize() {
        setPreferredSize(new Dimension(PREF_SIZE * board.getW(),
                                       PREF_SIZE * board.getH()));
        setMinimumSize(new Dimension(MIN_SIZE * board.getW(),
                                     MIN_SIZE * board.getH()));
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(PREF_SIZE * board.getW(),
                             PREF_SIZE * board.getH());
    }

  
    public final void setBoard(final Board b) {
        board = b;
        updateSize();
        repaint();
    }


    public final Board getBoard() {
        return board;
    }

    public final AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.scale(getWidth() / (TILE_SIZE * board.getW()),
                 getHeight() / (TILE_SIZE * board.getH()));
        return at;
    }

    public final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        int h = board.getH();
        int w = board.getW();
        g.transform(getTransform());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                           RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                           RenderingHints.VALUE_RENDER_QUALITY);

        AffineTransform at = new AffineTransform();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if ((x + y) % 2 == 0) {
                    g.setColor(LIGHT);
                } else {
                    g.setColor(DARK);
                }
                at.setToTranslation(x * TILE_SIZE, y * TILE_SIZE);
                g.fill(at.createTransformedShape(TILE));
            }
        }

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Piece p = board.getPiece(new Location(x, y));
                if (p != null) {
                    Image tile = p.getImage();
                    int yy = y;
                    if (flipped) {
                        yy = board.getH() - 1 - y;
                    }
                    at.setToTranslation(x * TILE_SIZE, yy * TILE_SIZE);
                    g.drawImage(tile, at, null);
                }
            }
        }

        Move last = board.last();
        if (last != null) {
            g.setColor(LAST);
            highlight(g, last.getStart());
            highlight(g, last.getNew());
        }

        if (selected != null) {
            g.setColor(SELECTED);
            highlight(g, selected);

            if (moves != null) {
                g.setColor(MOVEMENT);
                for (Move move : moves) {
                    highlight(g, move.getNew());
                }
            }
        }
    }


    private void highlight(final Graphics2D g, final Location pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (flipped) {
            y = board.getH() - 1 - y;
        }
        g.setStroke(HIGHLIGHT_STROKE);
        AffineTransform at = new AffineTransform();
        at.translate(x * TILE_SIZE, y * TILE_SIZE);
        g.draw(at.createTransformedShape(HIGHLIGHT));
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        switch (e.getButton()) {
        case MouseEvent.BUTTON1:
            leftClick(e);
            break;
        default:
            /* do nothing */
            break;
        }
        repaint();
    }


    private void leftClick(final MouseEvent e) {
        if (mode == Mode.WAIT) {
            return;
        }

        Location pos = getPixelPosition(e.getPoint());
        if (!board.range(pos)) {
          
            return;
        }
        if (pos != null) {
            if (pos.equals(selected)) {
               
                selected = null;
                moves = null;
            } else if (moves != null && moves.isNext(pos)) {
            
                mode = Mode.WAIT;
                Move move = moves.getMoveByNext(pos);
                selected = null;
                moves = null;
                selectedMove = move;
                latch.countDown();
            } else {
              
                Piece p = board.getPiece(pos);
                if (p != null && p.getSide() == side) {
                    selected = pos;
                    moves = p.getMoves(true);
                }
            }
        }
    }

    private Location getPixelPosition(final Point2D p) {
        Point2D pout = null;
        try {
            pout = getTransform().inverseTransform(p, null);
        } catch (java.awt.geom.NoninvertibleTransformException t) {
            /* This will never happen. */
            return null;
        }
        int x = (int) (pout.getX() / TILE_SIZE);
        int y = (int) (pout.getY() / TILE_SIZE);
        if (flipped) {
            y = board.getH() - 1 - y;
        }
        return new Location(x, y);
    }

    @Override
    public final Move turn(final Board turnBoard,
                               final Piece.Side currentSide) {
        latch = new CountDownLatch(1);
        board = turnBoard;
        side = currentSide;
        repaint();
        mode = Mode.PLAYER;
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOG.warning("BoardPanel interrupted during turn.");
        }
        return selectedMove;
    }

    @Override
    public final void gameEvent(final GameEvent e) {
        board = e.getGame().getBoard();
        if (e.getType() != GameEvent.STATUS) {
            repaint();
        }
    }

  
    public final double getRatio() {
        return board.getW() / (1.0 * board.getH());
    }

    public final void setFlipped(final boolean value) {
        flipped = value;
    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    @Override
    public void mousePressed(final MouseEvent e) {
 
    }
}
