/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author JLErickso
 */
public class AvailMove implements Iterable<Move> {
    
    private Board board;
    
    private boolean isCheck;
    
    private List<Move> moveList = new ArrayList<Move>();
    
    public AvailMove(Board checkBoard){
        
        this(checkBoard, true);
        
    }
    
    public AvailMove (Board checkBoard, boolean checkCheck){
        
        board = checkBoard;
        isCheck = checkCheck;
        
    }
    
    public boolean add(Move move){
        
        moveList.add(move);
        return true;
        
    }
    
    public boolean addAll(Iterable<Move> list){
        
        for(Move move : list){
            moveList.add(move);
            
        }
        return true;
    }
    
    public boolean addMove(Move move){
        
        if(board.isFree(move.getNew())){
            if(!causesCheck(move)){
                add(move);
                return true;
                
            }
            return true;
            
        }
        return false;
    }
    
    public boolean addTaken(Move move){
        Piece p = board.getPiece(move.getStart));
        
        if(board.isfree(move.getNew(), p.getSide())){
            
            if(!causesCheck(move)){
                add(move);
                return true;
                
            }
            return true;
            
        }
        return false;
    }
    
    public boolean causesCheck(Move move){
        
        if(!isCheck){
            return false;
        }
        
        Piece p = board.getPiece(move.getStart());
        board.move(move);
        boolean ret = board.check(p.getSide());
        board.undo();
        return ret;
        
        }
    
    public boolean isNext(Location l){
        
        for(Move move : this){
            
            if(l.equeals(move.getNew()))
                return true;
            
        }
        return false;
    }
    
    public Move getMoveByNext(Location next){
        
        for(Move move : this){
            if(next.equals(move.getNew()))
                return move;
            
            
        }
        return null;
    }
    
    public Move pop(){
        
        if(isEmpty()){
            return null;
        }
        Move last = moveList.get(moveList.size() - 1);
        moveList.remove(moveList.size() - 1);
        return last;
    }
    
    public boolean isEmpty(){
        
        return moveList.isEmpty();
        
    }
    
    public int size(){
        
        return moveList.size();
        
    }
    
    public Move peek(){
        
        if(isEmpty())
            return null;
        return moveList.get(moveList.size() - 1);
        
    }
    
    @Override
    public Iterator<Move> iterator(){
        
        return moveList.iterator();
        
    }
    
    public void shuffle(){
        
        Collections.shuffle(moveList);
        
    }
    
}
