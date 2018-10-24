/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchessgame;

import java.awt.Image;

/**
 *
 * @author JLErickso
 */
public abstract class Piece {
    
    private Side z;
    
    private Location l;
    
    private Board board;
    
    private int move = 0;
    
    private String name;
    
    public enum Side{
        
        WHITE (1), BLACK(-1);
        
        private int value;
        
        private Side(final int v){
            
            value = v;
        }
        
        public int value(){
            return value;
            
        }
    }
    
    protected Piece(){
        
    }
    
    protected Piece(final Side player, final String pName){
        z = player;
        name = pName;
    }
    
    public final void setLocation(final Location location){
        
        l = location;
    }
    
    public abstract AvailMove getMoves(boolean ifCheck);
    
    public final Location getLocation(){
        
        return l;
        
    }
    
    public Board getBoard(){
        
        return board;
        
    }
    
    public void setBoard(final Board gui){
        
        board = gui;
    }
    
    public void setSide(final Side player){
        
        z = player;
        
    }
    
    public Side getSide(){
        
        return z;
        
    }
    
    public final Boolean moved(){
        
        return move != 0;
        
    }
    
    public final void moveInc(){
        
        move++;
        
    }
    
    public final void moveDec(){
        
        move--;
        
    }
    
    public static Side enemy(final Side f){
        
        if(f == Side.WHITE){
            return Side.BLACK;
        }
        else
            return Side.WHITE;
        }
    
      public Image getImage() {
          
        return Images.getTile(name + "." + z);
    }
    }

