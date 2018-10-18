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
public class PieceFactory {
    
    public PieceFactory(){
        
    }
    
    public static Piece creation(String name, Piece.Side s){
        
        if("QUEEN".equals(name)){
            return new QUEEN(s);
            
        }
        else
            return null;
    }
    
}
