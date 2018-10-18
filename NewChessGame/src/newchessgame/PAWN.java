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
public class PAWN extends Piece{
    
    public PAWN ( Side s){
        
        super(s, "PAWN");
        
    }
    
    @Override
    public AvailMove getMoves(boolean inCheck){
        
        AvailMove list = new AvailMove(getBoard(), inCheck);
        Location l = getLocation();
        Board board = getBoard();
        int direction = direction();
        Location n = new Location(l, 0 , 1 * direction);
        Move first = new Move(l , n);
        addUpgrade(first);
        
        if(list.addMove(first) && !moved()){
            list.addMove(new Move(l , new Location(l, 0 , 2 * direction)));
            
        }
        Move takeLeft = new Move(l , new Location(l, -1 , 1 * direction));
        addUpgrade(takeLeft);
        list.addTakenOnly(takeLeft);
        Move takeRight = new Move(l , new Location(l , 1, 1 * direction));
        addUpgrade(takeRight);
        list.addTakeOnly(takeRight);
        
        Move last = board.last();
        
        if(last != null){
            Location r = new Location(l , 1 , 0);
            Location left = new Location(l, -1, 0);
            Location lastStart = last.getStart();
            Location lastNew = last.getNew();
            
            if(left.equals(lastNew) && (lastStart.getY() == lastNew.getY() + direction * 2)
                    && (board.getPiece(left) instanceof PAWN) && (lastStart.getX() == lastNew.getX())){
                
                Move passant = new Move(l, new Location(left, -1, direction));
                passant.setNext(new Move(l, null));
                list.addMove(passant);
            }
            
            else if (r.equals(lastNew) && (board.getPiece(r) instanceof PAWN) &&
                    (lastStart.getX() == lastNew.getX()) && (lastStart.getY() == lastNew.getY())){
                Move passant = new Move(l, new Location(l, 1, direction));
                passant.setNext(new Move(r, null));
                list.addMove(passant);
                
                
            }
        }
        return list;
        
    }
    
    public void addUpgrade(Move move){
        
        if (move.getNext().getY() != upgradeR())
            return;
        
        move.setNext(new Move(move.getDest(), null));
        Move upgrade = new Move(null, move.getDest());
        upgrade.setReplacement("QUEEN");
        upgrade.setReplaceSide(getSide());
        move.getNext().setNext(upgrade);
        
    }
    
    public int upgradeR(){
        
        if(getSide() == Side.BLACK){
            return 0;
        }
        
        else
            return getBoard().getHeight() - 1;
        
        }
    
    public int direction(){
        
        if(getSide() == Side.WHITE){
            return 1;
        }
        
        else
                return -1;
        }
    
    
    
    
}
