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
public class KING extends Piece{
    
    public KING(Side s){
        
        super(s, "KING");
        
        //we need to have a check check
        
        
    }
    
    //we need to have a check check
    public Boolean inCheck;

    public AvailMove enemy;
    
    
    @Override
    public AvailMove getMoves(boolean checkCheck){
        
        AvailMove list = new AvailMove(getBoard(), checkCheck);
        Location l = getLocation();
        
        for (int y = -1; y <= 1; y++){
            for(int x = -1; x <= 1; x++){
                
                if(x != 0 || y != 0) {
                    
                    list.addTaken(new Move(l, new Location(l, x, y)));
                }
            }
        }
        
        enemy = null;
        inCheck = null;
        
        if(checkCheck && !moved()){
            Move left = castle(-1);
            
            if(left != null){
                list.add(left);
            }
            
            Move r = castle(1);
            if(r != null){
                list.add(r);
                
            }
        }
        return list;
        
        
    }
    
    public Move castle(int direction){
        int distance = getBoard().geWidth() / 2 - 2;
        
        Location l = getLocation();
        
        int max;
        
        if(direction < 0 ){
            
            max = 0;
            
        }
        else{
            max = getBoard().getWidth() - 1;
        }
        
        Location rookLoc = new Location(max, l.getY());
        Piece ROOK = getBoard().getPiece(rookLoc);
        
        if(ROOK == null || ROOK.moved())
            return null;
        
        if(emptyRow(getLocation(), direction, max) && !inCheck()){
            
            Location kingLoc = new Location(l , direction * distance, 0);
            Move kingCast = new Move(l, kingLoc);
            Location newRookLoc = new Location(l, direction * distance, 0);
            Move rookCast = new Move(rookLoc, newRookLoc);
            kingCast.setNext(rookCast);
            
            return kingCast;
        }
        
        public boolean emptyRow(Location begin, int direction, int max){
            
            for(int i = begin.getX() + direction; i != max; i+= direction){
                Location l = new Location(i, begin.getY());
                
                if(getBoard().getPiece(l) != null || enemyMoves().containsDest(l)){
                
                return false;
            }
                
            }
            return true;
        }
        
        public AvailMove enemyMoves(){
            
            if(enemy != null){
                
                return enemy;
            }
            
            enemy = getBoard().allMoves(opposite(getSide()), false);
            return enemy;
        }
        
        public boolean inCheck(){
            
            if(inCheck != null){
                return inCheck;
            }
            
            inCheck = getboard().check(getSide());
            return inCheck;
        }
        
}
