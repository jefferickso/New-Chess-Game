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
public class BISHOP extends Piece{
    
    public BISHOP(Side s){
        
        super(s, "BISHOP");
        
    }
    
    @Override
    public AvailMove getMoves(boolean inCheck){
        
        AvailMove list = new AvailMove(getBoard, inCheck);
        list = getMoves(this, list);
        return list;
    }
    
    public static AvailMove getMoves(Piece p, AvailMove list){
        
        Location start = p.getLocation();
        
        int x = start.getX();
        int y = start.getY();
        
        while( x >= 0 && y >= 0){
            
            x--;
            y--;
            Location l = new Location(x, y);
            
            if(!list.addTaken(new Move(start, l))){
                break;
            }
            
            if(!p.getBoard().isFree(l)){
                break;
            }
            
            
            }
        
        x = start.getX();
        y = start.getY();
        
        while( x >= 0 && y < p.getBoard.getHeight()){
            
            x--;
            y++;
            Location l = new Location(x, y);
            
            if(!list.addTaken(new Move(start, l))){
                break;
            }
            
            if(!p.getBoard().isFree(l)){
                break;
            }
            
            
            }
        
        x = start.getX();
        y = start.getY();
        
        while( x < p.getBoard().getWidth() && y >= 0){
            
            x++;
            y--;
            Location l = new Location(x, y);
            
            if(!list.addTaken(new Move(start, l))){
                break;
            }
            
            if(!p.getBoard().isFree(l)){
                break;
            }
            
            
            }
        
        x = start.getX();
        y = start.getY();
        
        while( x < p.getBoard().getWidth()&& y < p.getBoard.getHeight()){
            
            x++;
            y++;
            Location l = new Location(x, y);
            
            if(!list.addTaken(new Move(start, l))){
                break;
            }
            
            if(!p.getBoard().isFree(l)){
                break;
            }
            
            
            }
        return list;
        }
    
    
}
