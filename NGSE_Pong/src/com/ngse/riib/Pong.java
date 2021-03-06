package com.ngse.riib;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import com.ngse.riib.GameBall;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pong extends JFrame implements ActionListener, KeyListener {

    Timer timer;
    ActionListener actLis;
    
    private static boolean reset;

    private static int fSizeX = 800;
    private static int fSizeY = 400;

    private static int pSizeX = 25;
    private static int pSizeY = 75;

    private static int ballSize = 20;
    private static int ballX = fSizeX / 2;
    private static int ballY = fSizeY / 2;

    private static int p1X = 50;
    private static int p1Y = fSizeY / 2;
    private static int p1Dir = 0;
    private static int p1Speed = 3;
    private static Rectangle p1 = new Rectangle(p1X - pSizeX / 2, p1Y - pSizeY / 2, pSizeX, pSizeY);
    
    private static int p1Score;

    private static int p2X = fSizeX - 50;
    private static int p2Y = fSizeY / 2;
    private static int p2Dir = 0;
    private static int p2Speed = 3;
    private static Rectangle p2 = new Rectangle(p2X - pSizeX / 2, p2Y - pSizeY / 2, pSizeX, pSizeY);
    
    private static int p2Score;

    private static GameBall gameBall = new GameBall(fSizeX / 2 - 5, fSizeY / 2 - 5, 0, 0, 20);

    private static Rectangle2D rect = new Rectangle2D.Double(0, 0, fSizeX, fSizeY);

    @SuppressWarnings("LeakingThisInConstructor")
    public Pong() {
        timer = new Timer(10, this);
        timer.setInitialDelay(200);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        if(reset) {
            g2.drawString("Player 1 Score: " + p1Score, fSizeX*1/4-50, fSizeY*1/6);
            g2.drawString("Player 2 Score: " + p2Score, fSizeX*3/4-50, fSizeY*1/6);
            g2.drawString("3", fSizeX/2-5, fSizeY/2-40);
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            g2.drawString("2", fSizeX/2-5, fSizeY/2-20);
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            g2.drawString("1", fSizeX/2-5, fSizeY/2);
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            
            reset = false;
        }
        
        update();

        g2.setColor(Color.WHITE);
        g2.fill(rect);

        g2.setColor(Color.BLACK);
        p1.setRect(p1X, p1Y, pSizeX, pSizeY);
        g2.fill(p1);
        p2.setRect(p2X, p2Y, pSizeX, pSizeY);
        g2.fill(p2);

        g2.setColor(Color.RED);
        gameBall.setSize(ballSize);
        g2.fill(gameBall.getEllipse());
    }

    public void update() {
        double p1TopY = p1Y;
        double p1Bottom = p1Y + pSizeY;
        
        double p2TopY = p2Y;
        double p2Bottom = p2Y + pSizeY;        
        
        if (p1Dir != 0) {
            if (p1Bottom > fSizeY) {
                p1Y = fSizeY - pSizeY;
                p1Dir = 0;
            } else 
            if (p1TopY < 0){
                p1Y = 0;
                p1Dir = 0;
            } else {
                //normal update
                p1Y += p1Speed * p1Dir; //update
            }
        }

        if (p2Dir != 0) {
            if (p2Bottom > fSizeY) {
                p2Y = fSizeY - pSizeY;
                p2Dir = 0;
            } else 
            if (p2TopY < 0){
                p2Y = 0;
                p2Dir = 0;
            } else {
                //normal update
                p2Y += p2Speed * p2Dir; //update
            }
        }

        updateGameBall();
    }

    public void updateGameBall() {
        //If the gameBall has gotten past either player
        if (gameBall.getX() < 0) {
            score(1);
        }
        if (gameBall.getX() + gameBall.getSize() > fSizeX) {
            score(2);
        }

        //Collider points on gameBall
        double bCent = gameBall.getCenterY();

        //Hits p1 paddle
        if (p1.contains(
                new Point(
                        (int) (gameBall.getCenterX() - (gameBall.getSize() / 2)), //left-middle X of ball
                        (int) (gameBall.getCenterY())) //left-middle Y of ball
        )) {

            double p1Cent = p1Y + (pSizeY / 2);
            double xDiff1 = pSizeX / 2 + gameBall.getSize() / (100/pSizeY);
            double yDiff1 = p1Cent - (bCent);
            double angle1 = (Math.atan2(yDiff1, xDiff1)) / (100/pSizeY);

            gameBall.setmvX((int) (((Math.cos(angle1)))*5));
            gameBall.setmvY((int) (((Math.sin(angle1)))*5) * -1); // * -1

        }
        //Hits p2 paddle
        if (p2.contains(
                new Point(
                        (int) (gameBall.getCenterX() + (gameBall.getSize() / 2)), //right-middle X of ball
                        (int) (gameBall.getCenterY())) //right-middle Y of ball
        )) {

            double p2Cent = p2Y + (pSizeY / 2);
            double xDiff2 = pSizeX / 2 + gameBall.getSize() / (100/pSizeY);
            double yDiff2 = p2Cent - (bCent);
            double angle2 = (Math.atan2(yDiff2, xDiff2)) / (100/pSizeY);

            gameBall.setmvX((int) (((Math.cos(angle2)))*5) * -1);
            gameBall.setmvY((int) (((Math.sin(angle2)))*5) * -1); // * -1

        }
        //If the gameBall hits a wall
        double getY = gameBall.getY();
        if ((getY <= 0 + (gameBall.getSize()) || getY >= fSizeY - (gameBall.getSize()))) {
            gameBall.setmvY((int) (gameBall.getmvY() * -1));
        }
        
        int getX = (int) gameBall.getX();
        if (getX <= 0) {
            score(2);
        } else if (getX >= fSizeX - (gameBall.getSize())) {
            score(1);
        }

        gameBall.move();
    }

    public void score(int player) {

        switch(player) {
            case 1:
                p1Score++;
                break;
            case 2:
                p2Score++;
                break;
        }
        
        System.out.println("Player " + player + " scores!");
        reset();

    }

    public void reset() {
        //Reset gameball
            System.out.println("DURING: Resetting gameBall");
            
            reset = true;
        
            Random rand = new Random();
            int rInt;
            //rnum = rand.nextInt((max - min) + 1 + min);

            gameBall.setX(fSizeX/2 - (int)gameBall.getSize());
            System.out.println("Setting setX() to " + gameBall.getX());
            gameBall.setY(fSizeX/2 + (int)gameBall.getSize()/2);

            rInt = rand.nextInt(2)*2-1;
            double x = ((Math.PI / 4) * (double)rInt) - ((Math.PI / 4) * (double)rInt );
            System.out.println("rInt at x: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvX = (int) ( ((Math.cos(x) * rInt)) * 2 );
            System.out.println("mvX: " + mvX);
            System.out.println("rInt at mvX: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvY = (int) ( ((Math.sin(x) * rInt)) * 2 );
            System.out.println("mvY: " + mvY);
            System.out.println("rInt at mvY: " + rInt);
            
            gameBall.setmvX((int)mvX);
            gameBall.setmvY((int)mvY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p2Dir = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            p2Dir = 0;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            p1Dir = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            p1Dir = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Main KeyEvent Method
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.print(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p2Dir = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            p2Dir = -1;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            p1Dir = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            p1Dir = -1;
        }

        repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new Pong();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pong");

        frame.setSize(fSizeX, fSizeY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        p1Score = 0;
        p2Score = 0;

        //Reset gameball
        
            reset = true;
        
            System.out.println("START: Resetting gameBall");
        
            Random rand = new Random();
            int rInt;
            //rnum = rand.nextInt((max - min) + 1 + min);

            gameBall.setX(fSizeX/2 - (int)gameBall.getSize());
            System.out.println("Setting setX() to " + gameBall.getX());
            gameBall.setY(fSizeX/2 + (int)gameBall.getSize()/2);

            rInt = rand.nextInt(2)*2-1;
            double x = ((Math.PI / 4) * (double)rInt) - ((Math.PI / 4) * (double)rInt );
            System.out.println("rInt at x: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvX = (int) ( ((Math.cos(x) * rInt)) * 2 );
            System.out.println("mvX: " + mvX);
            System.out.println("rInt at mvX: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvY = (int) ( ((Math.sin(x) * rInt)) * 2 );
            System.out.println("mvY: " + mvY);
            System.out.println("rInt at mvY: " + rInt);
            
            gameBall.setmvX((int)mvX);
            gameBall.setmvY((int)mvY);
            
            System.out.println(p1.getBounds().toString());
            System.out.println(p2.getBounds().toString());
            
            
            
            
            frame.setVisible(true);
    }

}
