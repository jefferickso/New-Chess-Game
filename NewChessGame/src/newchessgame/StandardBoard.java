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
public class StandardBoard extends Board{
    
    static int WIDTH = 8;
    
    static int HEIGHT = 8;
    
    static int wPawns = 1;
    
    static int bPawns = 6;
    
    static int wRow = 0;
    
    static int bRow = 7;
    
    static int lRook = 0;
    
    static int rRook = 7;
    
    static int lKnight = 1;
    
    static int rKnight = 6;
    
    static int lBishop = 2;
    
    static int rBishop = 5;
    
    static int Queen = 3;
    
    static int King = 4;
    
    
    public StandardBoard(){
        
        setW(WIDTH);
        setH(HEIGHT);
        clear();
        
        for (int x = 0; x < WIDTH; x++){
            setPiece(x, wPawns, new PAWN(Piece.Side.WHITE));
            setPiece(x, bPawns, new PAWN(Piece.Side.BLACK));
        } 
        
        setPiece(lRook,     wRow, new ROOK(Piece.Side.WHITE));
        setPiece(rRook,     wRow, new ROOK(Piece.Side.WHITE));
        setPiece(lRook,     bRow, new ROOK(Piece.Side.BLACK));
        setPiece(rRook,     bRow, new ROOK(Piece.Side.BLACK));
        setPiece(lKnight,   wRow, new KNIGHT(Piece.Side.WHITE));
        setPiece(rKnight,   wRow, new KNIGHT(Piece.Side.WHITE));
        setPiece(lKnight,   bRow, new KNIGHT(Piece.Side.BLACK));
        setPiece(rKnight,   bRow, new KNIGHT(Piece.Side.BLACK));
        setPiece(lBishop,   wRow, new BISHOP(Piece.Side.WHITE));
        setPiece(rBishop,   wRow, new BISHOP(Piece.Side.WHITE));
        setPiece(lBishop,   bRow, new BISHOP(Piece.Side.BLACK));
        setPiece(rBishop,   bRow, new BISHOP(Piece.Side.BLACK));
        setPiece(Queen,     wRow, new QUEEN(Piece.Side.WHITE));
        setPiece(Queen,     bRow, new QUEEN(Piece.Side.BLACK));
        setPiece(King,      wRow, new KING(Piece.Side.WHITE));
        setPiece(King,      bRow, new KING(Piece.Side.BLACK));
        
    }
    
    @Override
    public Boolean checkMate(Piece.Side s){
        
        return inCheck(s) && (moveCount(s) == 0);
    }
    
    @Override
    public Boolean staleMate(Piece.Side s){
        
        return (!inCheck(s)) && (moveCount(s) == 0);
    }
    
    
    public int moveCount(Piece.Side s){
        int count  = 0;
        
        for(int y = 0; y < getH(); y++){
            
            for(int x = 0; x < getW(); x++){
                Piece p = getPiece(new Location(x, y));
                
                if((p != null) && (p.getSide() == s)) {
                    count += p.getMoves(true).size();
                }
            }
        }
        return count;
    }
    
    @Override
    public Boolean inCheck(Piece.Side s){
        Piece.Side attack;
        
        if(s == Piece.Side.WHITE){
            attack = Piece.Side.BLACK;
        }
        
        else
            attack = Piece.Side.WHITE;
        
        Location kingLoc = findKing(s);
        
        if(kingLoc == null)
            return false;
        
        for(int y = 0; y < getH(); y++){
            
            for(int x = 0; x < getW(); x++){
                
                Piece p = getPiece( new Location(x, y));
                
                if((p != null) && (p.getSide() == attack) && p.getMoves(false).isNext(kingLoc))
                    return true;
            }
        }
        return false;
    }
}
