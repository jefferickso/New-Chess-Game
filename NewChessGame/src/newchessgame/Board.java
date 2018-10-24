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
public abstract class Board {
    
    private Piece [] [] board;
    
    int boardW, boardH;
    
    private AvailMove moveList = new AvailMove(this);
    
    public void clear(){
        
        board = new Piece[boardW][boardH];
      
    }
    
    public abstract Boolean checkMate(Piece.Side s);
    
    public abstract Boolean staleMate(Piece.Side s);
    
    public abstract Boolean inCheck(Piece.Side s);
    
    public Boolean inCheck(){
        
        return inCheck(Piece.Side.WHITE) || inCheck(Piece.Side.BLACK);
        
    }
    
    public Boolean staleMate(){
        
        return staleMate(Piece.Side.BLACK) || staleMate(Piece.Side.WHITE);
        
    }
    
    public Location findKing(Piece.Side s){
        
        for(int y =0; y < getH(); y++){
            
            for(int x = 0; x < getW(); x++){
                
                Location l = new Location(x, y);
                Piece p = getPiece(l);
                
                if(p instanceof KING && p.getSide() == s){
                    
                    return l;
                    
                }
            }
        }
        return null;
    }
    
    public void setW(int w){
        
        boardW = w;
        
    }
    
    public void setH(int h){
        
        boardH = h;
        
    }
    
    public int getW(){
        
        return boardW;
        
    }
    
    public int getH(){
        
        return boardH;
        
    }
    
    public void setPiece(int x, int y, Piece p){
        
        setPiece(new Location(x, y), p);
        
    }
    
    public void setPiece(Location l, Piece p){
        
        board[l.getX()][l.getY()] = p;
        
        if(p != null){
            p.setLocation(l);
            p.setBoard(this);
        
        }
    
    }
    
    public Piece getPiece(Location l){
        
        return board[l.getX()][l.getY()];
        
    }
    
    public void move(Move move){
        
        moveList.add(move);
        execMove(move);
        
    }
    
    public void execMove(Move move){
        
        if(move == null){
            
            return;
            
        }
        
        Location a = move.getStart();
        Location b = move.getNew();
        
        if(a != null && b != null){
            move.setTaken(getPiece(b));
            setPiece(b, getPiece(a));
            setPiece(a, null);
            getPiece(b).setLocation(b);
            getPiece(b).moveInc();
        }
        else if ( b == null && a != null){
            move.setTaken(getPiece(a));
            setPiece(a, null);
        }
        
        else{
            setPiece(b, PieceFactory.creation(move.getReplacement(), move.getReplaceSide()));
            
        }
        
        execMove(move.getNext());
        
        }
    
    public void undo(){
        
        execUndo(moveList.pop());
        
    }
    
    public void execUndo(Move move){
        
        if(move == null){
            
            return;
        }
        
        execUndo(move.getNext());
        Location a = move.getStart();
        Location b = move.getNew();
        
        if(b != null && a != null){
            setPiece(a, getPiece(b));
            setPiece(b, move.getTaken());
            getPiece(a).setLocation(a);
            getPiece(a).moveDec();
            
        }
        
        else if(b == null && a != null){
            
            setPiece(a, move.getTaken());
            
        }
        
        else{
            setPiece(b, null);
            
        }
    }
    
    public Move last(){
        
        return moveList.peek();
        
    }
    
    public Boolean isEmpty(Location l){
        
        return getPiece(l) == null;
        
    }
    
    public Boolean isEmpty(Location l, Piece.Side s){
        
        Piece p = getPiece(l);
        
        if(p == null){
            
            return true;
            
        }
        
        return p.getSide() != s;
        
       }
    
    public Boolean range(Location l){
        
        return (l.getX() >= 0) &&(l.getX() < boardW) && (l.getY() >= 0) && (l.getY() < boardH);
        
        
    }
    
    public Boolean isFree(Location l){
        
        return range(l) && isEmpty(l);
        
    }
    
    public Boolean isFree(Location l, Piece.Side s){
        
        return range(l) && isEmpty(l, s);
        
    }
    
    public Board copy(){
        
        Board b = BoardFactory.creation(this.getClass());
        
        for(Move move : moveList){
            b.move( new Move(move));
            
        }
        
        return b;
        
    }
    
    public AvailMove allMoves(Piece.Side s, boolean inCheck){
        
        AvailMove list = new AvailMove(this, false);
        
        for(int y = 0; y < boardH; y++){
            
            for(int x = 0 ; x < boardW; x++){
                Piece p = board[x][y];
                
                if(p != null && p.getSide() == s){
                    
                    list.addAll(p.getMoves(inCheck));
                    
                }
                
            }
            
        }
        return list;
    }
    
    public int moveCount(){
        
        return moveList.size();
        
    }
    


    
    
}
