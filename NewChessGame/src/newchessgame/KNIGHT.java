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
public class KNIGHT extends Piece{
    
    public KNIGHT(Side s){
        
        super(s, "KNIGHT");
        
    }
    
    static int shortL = 1;
    
    static int longL = 2;
    
    


@Override
public AvailMove getMoves(boolean inCheck){

    AvailMove list = new AvailMove(getBoard(), inCheck);
    list = getMoves(this, list);
    return list;

}

public static AvailMove getMoves(Piece p , AvailMove list){
    
    //Here is the special movements of the knight
    Location l = p.getLocation();
    list.addTaken(new Move(l, new Location(l, shortL, longL)));
    list.addTaken(new Move(l, new Location(l, longL, shortL)));
    list.addTaken(new Move(l, new Location(l, -shortL, longL)));
    list.addTaken(new Move(l, new Location(l, -shortL, -longL)));
    list.addTaken(new Move(l, new Location(l, shortL, -longL)));
    list.addTaken(new Move(l, new Location(l, longL, -shortL)));
    list.addTaken(new Move(l, new Location(l, -longL, shortL)));
    list.addTaken(new Move(l, new Location(l, -longL, -shortL)));
    
    return list;
}

}
