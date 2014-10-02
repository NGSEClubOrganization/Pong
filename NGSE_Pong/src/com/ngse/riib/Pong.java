/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngse.riib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Henry
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Graphics2D.*;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong extends JFrame implements ActionListener , KeyListener
{

    Timer timer;
    ActionListener actLis;
    
    private static int fSizeX = 800;
    private static int fSizeY = 400;
    
    private static int pSizeX = 25;
    private static int pSizeY = 75;
    
    private static int ballSize = 50;
    private static int ballX = fSizeX/2;
    private static int ballY = fSizeY/2;
    
    private static int p1X = 50;
    private static int p1Y = fSizeY/2;
    private static int p1Dir = 0;
    private static int p1Speed = 1;
    private static Rectangle2D p1 = new Rectangle2D.Double(p1X-pSizeX/2, p1Y-pSizeY/2, pSizeX, pSizeY);
    
    private static int p2X = fSizeX-50;
    private static int p2Y = fSizeY/2;
    private static int p2Dir = 0;
    private static int p2Speed = 1;
    private static Rectangle2D p2 = new Rectangle2D.Double(p2X-pSizeX/2, p2Y-pSizeY/2, pSizeX, pSizeY);
    
    private static GameBall gameBall = new GameBall(fSizeX/2-5,fSizeY/2-5,0,0,5);
    
    private static Rectangle2D rect = new Rectangle2D.Double(0,0,fSizeX,fSizeY);
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Pong() {
        timer = new Timer(3,this);
        timer.setInitialDelay(200);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    @Override
    public void paint(Graphics g) {
        
        update();
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.WHITE);
        g2.fill(rect);
        
        g2.setColor(Color.BLACK);
        p1.setRect(p1X-pSizeX/2, p1Y-pSizeY/2, pSizeX, pSizeY);
        g2.fill(p1);
        p2.setRect(p2X-pSizeX/2, p2Y-pSizeY/2, pSizeX, pSizeY);
        g2.fill(p2);
    }
    
    public void update() {
        if(p1Dir != 0) {
            if(p1Y+pSizeY < fSizeY) {
                p1Y += p1Speed*p1Dir;
            } else {
                p1Y -= p1Speed;
            }
            if(p1Y-pSizeY > 0) { 
                p1Y += p1Speed*p1Dir;
            } else {
                p1Y += p1Speed;
            }
        }
        
        if(p2Dir != 0) {
            if(p2Y+pSizeY < fSizeY) {
                p2Y += p2Speed*p2Dir;
            } else {
                p2Y -= p2Speed;
            }
            if(p2Y-pSizeY > 0) { 
                p2Y += p2Speed*p2Dir;
            } else {
                p2Y += p2Speed;
            }
        }
        
        if(gameBall.getX() < 0) {
            playerScore("p1");
        } else
        if(x <= p1X) {
            if(
        }
        gameBall.move();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_DOWN) {
            p2Dir = 0;
        } else if(e.getKeyCode()== KeyEvent.VK_UP) {
            p2Dir = 0;
        }
        
        if(e.getKeyCode()== KeyEvent.VK_S) {
            p1Dir = 0;
        } else if(e.getKeyCode()== KeyEvent.VK_W) {
            p1Dir = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    // Main KeyEvent Method
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.print(e.getKeyCode());
        if(e.getKeyCode()== KeyEvent.VK_DOWN) {
            p2Dir = 1;
        } else if(e.getKeyCode()== KeyEvent.VK_UP) {
            p2Dir = -1;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_S) {
            p1Dir = 1;
        } else if(e.getKeyCode()== KeyEvent.VK_W) {
            p1Dir = -1;
        }
      
        repaint();
    }
    
    public void playerScore(String player) {
        if(player == "p1") {}
        if(player == "p2") {}
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new Pong();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(fSizeX,fSizeY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        frame.setVisible(true);
    }
    
}
