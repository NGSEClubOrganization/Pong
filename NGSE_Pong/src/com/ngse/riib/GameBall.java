
package com.ngse.riib;

import java.awt.geom.*;

//Hi!


public class GameBall {
    
    private double x, y, mvX, mvY, size;
    
    public GameBall(int parx, int pary, int parmvX, int parmvY, int parSize) {
        x = parx;
        y = pary;
        mvX = parmvX;
        mvY = parmvY;
        size = parSize;
    }
    
    public void move() {
        x += mvX;
        y += mvY;
    }
    
    //Sets
    public void setX(int parX) {x = parX;}
    public void setY(int parY) {x = parY;} 
    public void setmvX(int parmvX) { mvX = parmvX;}
    public void setmvY(int parmvY) { mvY = parmvY;}
    public void setSize(int parSize) {size = parSize;}
    
    //Gets
    public double getX() {return x;}
    public double getY() {return y;}
    public double getmvX() {return mvX;}
    public double getmvY() {return mvY;}
    public double getSize() {return size;}
    public double getCenterX() {return x+(size/2);}
    public double getCenterY() {return y+(size/2);}
    
    public Ellipse2D getEllipse() {
        Ellipse2D elip = new Ellipse2D.Double(x,y,size,size);
        return elip;
    }
}