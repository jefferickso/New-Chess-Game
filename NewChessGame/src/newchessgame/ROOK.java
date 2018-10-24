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
public class ROOK extends Piece{
    
    public ROOK(Side s){
        
        super(s, "ROOK");
        
    }
    
    @Override
    public AvailMove getMoves(boolean inCheck){
        
        AvailMove list = new AvailMove(getBoard(), inCheck);
        list = getMoves(this, list);
        return list;
        
    }
    
    public static AvailMove getMoves(Piece p, AvailMove list){
        
        Location start = p.getLocation();
        int x = start.getX();
        int y = start.getY();
        
        while( x >= 0){
            x--;
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
            
            while(x < p.getBoard().getW()){
                x++;
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
            
        while(y < p.getBoard().getH()){
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
            
        while(y >= 0){
            y--;
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
