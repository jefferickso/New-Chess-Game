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
public class BoardFactory {
    
    
    public static Class standard = (new StandardBoard()).getClass();
    
    public static Class empty = (new EmptyBoard()).getClass();
    
    public BoardFactory(){
        
    }
    
    public static Board creation(Class board){
        
        if(board.equals(standard)){
            return new StandardBoard();
            
        }
        else if (board.equals("empty")){
            return new EmptyBoard();
        }
        else
            return null;
    }
}
