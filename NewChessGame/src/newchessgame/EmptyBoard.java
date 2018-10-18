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
public class EmptyBoard extends StandardBoard{
    
    static int WIDTH = 8;
    
    static int HEIGHT = 8;
    
    public EmptyBoard(){
        
        this(WIDTH, HEIGHT);
    }
    
    public EmptyBoard(int w, int h){
        
        setWidth(w);
        setHeight(h);
        clear();
    }
    
}
