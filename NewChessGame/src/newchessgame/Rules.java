/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 *
 * @author JLErickso
 */
public class Rules implements Runnable{
    
    public stat final double seconds = 1000.0;
    
    public Board board;
    
    public Player white;
    
    public Player black;
    
    public Piece.Side turn;
    
    public String status = "";
    
    public static double ALPHA = 0.4;
    
    public volatile Boolean done = false; 
    
    public Piece.Side winner;
    
    private Collection<GameListener> listen = new CopyOnWriteArraySet<GameListener>();
    
    
    public Rules(){
        
        
    }
        
    public Rules(Board gBoard){
            
            board = gBoard;
            
    }
    
    public void seat(Player wPlayer, Player bPlayer){
        
        white = wPlayer;
        black = bPlayer;
    }
    
    public void end(){
        
        listen.clear();
        winner = null;
        done = true;
        
    }
    
    public void start(){
        
        done = false;
        turn = Piece.Side.BLACK;
        callGameListeners(GameEvent.TRUN);
        new Thread(this).start();
        
    }
    
    @Override
    public void run(){
        
        while(!done){
        
        Player player;
        
        if(turn == Piece.Side.WHITE){
            turn = Piece.Side.BLACK;
            setStatus("Black's Turn");
            player = black;
        }
        
        else {
            turn = Piece.Side.WHITE;
            setStatus("It's White's Turn");
            player = white;
            
        }
        
        Move move = player.takeTurn(getBoard(), turn);
        board.move(move);
        setProgess(0);
        if(done){
            
            return;
        }
        
        Piece.Side opp = Piece.opposite(turn);
        
        if(board.checkMate(opp)){
            done = true;
            
            if(opp == Piece.Side.BLACK){
                setStatus("White player wins");
                winner = Piece.Side.WHITE;
            }
            
            else{
                setStatus("Black player wins");
                winner = Piece.Side.BLACK;
            }
            callGameListeners(GameEvent.GAME_END);
            return;
            }
        else if (board.staleMate(opp)){
            done = true;
            setStatus("StaleMate");
            winner = null;
            callGameListeners(GameEvent.GAME_END);
            return;
        }
        calllGameListeners(GameEvent.TURN);
        
        }
    }
    
    public Board getBoard(){
        
        return board.copy();
        
    }
    
    public void addGameListener(GameListener listener){
        
        listen.add(listener);
        
    }
    
    public void callGameListeners(int type){
        
        for(GameListener listener : listen){
            
            listener.gameEvent(this, type);
        }
    }
    
    public boolean isDone(){
        
        return done;
        
    }
    
    public Piece.Side getWinner(){
        
        return winner;
        
    }
    
    private float progress;
    
    private long progressStart;
    
    private long privateUpdate;
    
    private double etaUnit;
    
    public void setStatus(String message){
        
        LOG.info("status: " + message);
        
        if(message == null){
            throw new IllegalArgumentException();
            
        }
        
        status = message;
        callGameListeners(GameEvent.STATUS);
        
    }
    
    public String getStatus(){
        
        return status;
        
    }
    
        public final void setProgress(final float value) {
        LOG.finest("Game progress: " + value);
        progress = value;
        if (value == 0) {
            progressStart = System.currentTimeMillis();
            etaUnit = 0;
        } else {
            long diff = System.currentTimeMillis() - progressStart;
            double unit = diff / value / MSEC_TO_SEC;
            if (etaUnit == 0) {
                etaUnit = unit;
            } else {
                etaUnit = ALPHA * etaUnit + (1 - ALPHA) * unit;
            }
        }
        progressUpdate = System.currentTimeMillis();
        callGameListeners(GameEvent.STATUS);
    }


    public final double getETA() {
        long now = System.currentTimeMillis();
        if (progress > 0) {
            double diff = (now - progressUpdate) / MSEC_TO_SEC;
            double t = (etaUnit * (1.0 - progress)) - diff;
            if (t < 0) {
                return 0;
            } else {
                return t;
            }
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public final float getProgress() {
        return progress;
    }
}
    
        
    
    
    
        

}




