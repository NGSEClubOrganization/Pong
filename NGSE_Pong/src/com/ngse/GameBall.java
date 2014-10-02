/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngse;




public class GameBall {
    
    private int x, y, mvX, mvY, size;
    
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
    public int getX() {return x;}
    public int getY() {return y;}
    public int getmvX() {return mvX;}
    public int getmvY() {return mvY;}    
}