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
public class Location implements Comparable<Location> {
    
    private final int x, y;
    
    public Location(final int x0, final int y0){
        
        x = x0;
        y = y0;
}
    
    public Location(final Location l, int deltax, int deltay){
        this(l.x + deltax, l.y + deltay);
    }
    
    public int getX(){
        
        return x;
        
    }
    
    public int getY(){
        
        return y;
        
    }
    
    public String toString(){
        
        return "" + ((char)('a' + (char) x)) + (y + 1);
        
    }
    
    public boolean equals(final Location l){
        
        return (l != null) && (l.x == x) && (l.y == y);
        
    }
    
    public boolean equals (final Object obj){
        
        if(this == obj)
            return true;
        if(!(obj instanceof Location))
            return false;
        return equals((Location) obj);
        
        }
    
    public int hashCode(){
        return x ^ y;
        
    
        }
    
    public int comapreTo(final Location l){
        
        if(l.y == y)
            return x - l.x;
        else
            return y - l.y;
    }
    



    
}
