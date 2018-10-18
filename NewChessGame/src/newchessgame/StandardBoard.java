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
        
        setWidth(WIDTH);
        setHeight(HEIGHT);
        clear();
        
        for (int x = 0; x < WIDTH; x++){
            setPiece(x, wPawns, new Pawn(Piece.Side.WHITE));
            setPiece(x, bPawns, new Pawn(Piece.Side.BLACK));
        } 
        
        setPiece(lRook,     wRow, new ROOK(Piece.Side.WHITE));
        setPiece(rRook,     wRow, new ROOK(Piece.Side.WHITE));
        setPiece(lRook,     bRow, new ROOK(Piece.Side.BLACK));
        setPiece(rRook,     bRow, new ROOK(Piece.Side.BLACK));
        setPiece(lKnight,   wRow, new Knight(Piece.Side.WHITE));
        setPiece(rKnight,   wRow, new Knight(Piece.Side.WHITE));
        setPiece(lKnight,   bRow, new Knight(Piece.Side.BLACK));
        setPiece(rKnight,   bRow, new Knight(Piece.Side.BLACK));
        setPiece(lBishop,   wRow, new Bishop(Piece.Side.WHITE));
        setPiece(rBishop,   wRow, new Bishop(Piece.Side.WHITE));
        setPiece(lBishop,   bRow, new Bishop(Piece.Side.BLACK));
        setPiece(rBishop,   bRow, new Bishop(Piece.Side.BLACK));
        setPiece(Queen,     wRow, new Queen(Piece.Side.WHITE));
        setPiece(Queen,     bRow, new Queen(Piece.Side.BLACK));
        setPiece(King,      wRow, new King(Piece.Side.WHITE));
        setPiece(King,      bRow, new King(Piece.Side.BLACK));
        
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
        
        for(int y = 0; y < getHeight(); y++){
            
            for(int x = 0; x < getWidth(); x++){
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
        
        for(int y = 0; y < getHeight(); y++){
            
            for(int x = 0; x < getWidth(); x++){
                
                Piece p = getPiece( new Location(x, y));
                
                if((p != null) && (p.getSide() == attack) && p.getMoves(false).containsDest(kingLoc))
                    return true;
            }
        }
        return false;
    }
}
