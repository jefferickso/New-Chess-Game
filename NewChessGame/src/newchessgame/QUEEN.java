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
public class QUEEN extends Piece{
    
    public QUEEN(Side s){
        
        super(s, "QUEEN");
        
    }
    
    @Override
    public AvailMove getMoves(boolean inCheck){
        
        AvailMove list = new AvailMove(getBoard(), inCheck);
        
        //We already have the movements of the queen in rook and bishop classes
        list = ROOK.getMoves(this, list);
        list = BISHOP.getMoves(this, list);
        
        return list;
    }
    
}
