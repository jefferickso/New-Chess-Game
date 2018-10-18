/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

/**
 *
 * @author JLErickso
 */
public class Move {
    
    private final Location start;
    
    private final Location newLoc;
    
    private Move next;
    
    private Piece taken;
    
    private String replacement;
    
    private Piece.Side replaceSide;
    
    private double score;
    
    public Move(final Location s, final Location n){
        
        newLoc = n;
        start = s;
        next = null;
    }
    
    public Move(final Move move){
        
        this(move.getStart(), move.getNew());
        taken = move.getTaken();
        replacement = move.getReplacement();
        replaceSide = move.getReplaceSide();
        
        if(move.getNext() != null){
            
            next = new Move(move.getNext());
            
        }
    }
    
    public void setNext(Move move){
        
        next = move;
        
    }
    
    public Move getNext(){
        
        return next;
        
    }
    
    public Location getStart(){
        
        return start;
        
    }
    
    public Location getNew(){
     
        return newLoc;
    }
    
    public void setTaken(final Piece p){
        
        taken = p;
        
    }
    
    public Piece getTaken(){
        
        return taken;
        
    }
    
    public void setReplacement(String name){
        
        replacement = name;
        
    }
    
    public String getReplacement(){
        
        return replacement;
       
    }
    
    public Piece.Side getReplaceSide(){
        
        return replaceSide;
        
    }
    
    public void setReplaceSide(final Piece.Side s){
        
        replaceSide = s;
        
    }
    
    @Override
    public String toString(){
        
        return "" + start + newLoc;
        
    }
    
    public double getScore(){
        
        return score;
        
    }
    
    public void setScore(double newScore){
        
        this.score = newScore;
        
    }
            
            
}
