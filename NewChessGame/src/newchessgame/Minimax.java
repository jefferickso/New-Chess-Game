/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 *
 * @author JLErickso
 */
public class Minimax implements Player {

    private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.ai.Minimax");

    private static final int NTHREADS =
        Runtime.getRuntime().availableProcessors();

    private final Rules game;

    private Piece.Side side = null;

    private volatile Move bestMove;

    private final Executor executor = Executors.newFixedThreadPool(NTHREADS);

    private Map<Class, Double> values;

    static final double MILLI = 1000.0;

    private int maxDepth;

    private double wMaterial;

    private double wSafety;

    private double wMobility;

    public Minimax(final Rules active) {
        this(active, "default");
    }

 
    public Minimax(final Rules active, final String name) {
        this(active, getConfig(name));
    }


    public Minimax(final Rules active, final Properties props) {
        game = active;
        values = new HashMap<Class, Double>();
        Properties config = props;

        values.put((new PAWN(side)).getClass(),
                   Double.parseDouble(config.getProperty("Pawn")));
        values.put((new KNIGHT(side)).getClass(),
                   Double.parseDouble(config.getProperty("Knight")));
        values.put((new BISHOP(side)).getClass(),
                   Double.parseDouble(config.getProperty("Bishop")));
        values.put((new ROOK(side)).getClass(),
                   Double.parseDouble(config.getProperty("Rook")));
        values.put((new QUEEN(side)).getClass(),
                   Double.parseDouble(config.getProperty("Queen")));
        values.put((new KING(side)).getClass(),
                   Double.parseDouble(config.getProperty("King")));

        maxDepth = (int) Double.parseDouble(config.getProperty("depth"));
        wMaterial = Double.parseDouble(config.getProperty("material"));
        wSafety = Double.parseDouble(config.getProperty("safety"));
        wMobility = Double.parseDouble(config.getProperty("mobility"));
    }

    public static Properties getConfig(final String name) {
        Properties props;
        if ("default".equals(name)) {
            props = new Properties();
        } else {
            props = new Properties(getConfig("default"));
        }

        String filename = name + ".properties";
        InputStream in = Minimax.class.getResourceAsStream(filename);
        try {
            props.load(in);
        } catch (java.io.IOException e) {
            LOG.warning("Failed to load AI config: " + name + ": " + e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LOG.info("failed to close stream: " + e.getMessage());
            }
        }
        return props;
    }

    @Override
    public final Move turn(final Board board,
                               final Piece.Side currentSide) {
        side = currentSide;

        AvailMove moves = board.allMoves(side, true);
        moves.shuffle();

        if (game != null) {
            game.setProgress(0);
            game.setStatus("Thinking ...");
        }
        long startTime = System.currentTimeMillis();

        CompletionService<Move> service =
            new ExecutorCompletionService<Move>(executor);
        int submitted = 0;
        bestMove = null;
        for (final Move move : moves) {
            final Board callboard = board.copy();
            service.submit(new Callable<Move>() {
                public Move call() {
                    callboard.move(move);
                    double beta = Double.POSITIVE_INFINITY;
                    if (bestMove != null) {
                        beta = -bestMove.getScore();
                    }
                    double v = search(callboard, maxDepth - 1,
                                      Piece.enemy(side),
                                      Double.NEGATIVE_INFINITY, beta);
                    move.setScore(-v);
                    return move;
                }
            });
            submitted++;
        }

        for (int i = 0; i < submitted; i++) {
            try {
                Move m = service.take().get();
                if (bestMove == null || m.getScore() > bestMove.getScore()) {
                    bestMove = m;
                }
            } catch (ExecutionException e) {
                LOG.warning("move went unevaluated: " + e.getMessage());
            } catch (InterruptedException e) {
                LOG.warning("move went unevaluated: " + e.getMessage());
            }
            if (game != null) {
                game.setProgress(i / (1.0f * (submitted - 1)));
            }
        }

        long time = (System.currentTimeMillis() - startTime);
        LOG.info("AI took " + (time / MILLI) + " seconds (" +
                 NTHREADS + " threads, " + maxDepth + " plies)");
        return bestMove;
    }

    private double search(final Board b, final int depth, final Piece.Side s,
                          final double alpha, final double beta) {
        if (depth == 0) {
            double v = valuate(b);
            return (s != side) ? -v : v;
        }
        Piece.Side opps = Piece.enemy(s);  // opposite side
        double best = alpha;
        AvailMove list = b.allMoves(s, true);
        for (Move move : list) {
            b.move(move);
            best = Math.max(best, -search(b, depth - 1, opps, -beta, -best));
            b.undo();
            /* alpha-beta prune */
            if (beta <= best) {
                return best;
            }
        }
        return best;
    }

    private double valuate(final Board b) {
        double material = materialValue(b);
        double kingSafety = kingInsafetyValue(b);
        double mobility = mobilityValue(b);
        return material * wMaterial +
               kingSafety * wSafety +
               mobility * wMobility;
    }

    private double materialValue(final Board b) {
        double value = 0;
        for (int y = 0; y < b.getH(); y++) {
            for (int x = 0; x < b.getW(); x++) {
                Location pos = new Location(x, y);
                Piece p = b.getPiece(pos);
                if (p != null) {
                    value += values.get(p.getClass()) * p.getSide().value();
                }
            }
        }
        return value * side.value();
    }

    private double kingInsafetyValue(final Board b) {
        return kingInsafetyValue(b, Piece.enemy(side)) -
               kingInsafetyValue(b, side);
    }

    private double kingInsafetyValue(final Board b, final Piece.Side s) {
    
        Location king = b.findKing(s);
        if (king == null) {
        
            return Double.POSITIVE_INFINITY;
        }
        AvailMove list = new AvailMove(b, false);
      
        ROOK.getMoves(b.getPiece(king), list);
        BISHOP.getMoves(b.getPiece(king), list);
        return list.size();
    }

   
    private double mobilityValue(final Board b) {
        return b.allMoves(side, false).size() -
               b.allMoves(Piece.enemy(side), false).size();
    }
}