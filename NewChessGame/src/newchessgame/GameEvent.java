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
public class GameEvent {
    
    public static int TURN = 0;
    
    public static int STATUS = 1;
    
    public static int GAME_END = 2;
    
    public int type;
    
    public Rules game;
    
    
    public GameEvent(Rules w, int eventType){
        
        game = w;
        type = eventType;
        
    }
    
    
    public int getType(){
        
        return type;
        
    }
    
    public Rules getGame(){
        
        return game;
    }
}
