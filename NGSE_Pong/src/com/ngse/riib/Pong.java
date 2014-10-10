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

public class Pong extends JFrame implements ActionListener, KeyListener {

    Timer timer;
    ActionListener actLis;

    private static int fSizeX = 800;
    private static int fSizeY = 400;

    private static int pSizeX = 25;
    private static int pSizeY = 75;

    private static int ballSize = 50;
    private static int ballX = fSizeX / 2;
    private static int ballY = fSizeY / 2;

    private static int p1X = 50;
    private static int p1Y = fSizeY / 2;
    private static int p1Dir = 0;
    private static int p1Speed = 1;
    private static Rectangle p1 = new Rectangle(p1X - pSizeX / 2, p1Y - pSizeY / 2, pSizeX, pSizeY);

    private static int p2X = fSizeX - 50;
    private static int p2Y = fSizeY / 2;
    private static int p2Dir = 0;
    private static int p2Speed = 1;
    private static Rectangle p2 = new Rectangle(p2X - pSizeX / 2, p2Y - pSizeY / 2, pSizeX, pSizeY);

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

        update();

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fill(rect);

        g2.setColor(Color.BLACK);
        p1.setRect(p1X - pSizeX / 2, p1Y - pSizeY / 2, pSizeX, pSizeY);
        g2.fill(p1);
        p2.setRect(p2X - pSizeX / 2, p2Y - pSizeY / 2, pSizeX, pSizeY);
        g2.fill(p2);

        g2.setColor(Color.RED);
        g2.fill(gameBall.getEllipse());
    }

    public void update() {
        if (p1Dir != 0) {
            if (p1Y + pSizeY < fSizeY) {
                p1Y += p1Speed * p1Dir;
            } else {
                p1Y -= p1Speed;
            }
            if (p1Y - pSizeY > 0) {
                p1Y += p1Speed * p1Dir;
            } else {
                p1Y += p1Speed;
            }
        }

        if (p2Dir != 0) {
            if (p2Y + pSizeY < fSizeY) {
                p2Y += p2Speed * p2Dir;
            } else {
                p2Y -= p2Speed;
            }
            if (p2Y - pSizeY > 0) {
                p2Y += p2Speed * p2Dir;
            } else {
                p2Y += p2Speed;
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
            double xDiff1 = pSizeX / 2 + gameBall.getSize() / 2;
            double yDiff1 = p1Cent - (bCent - gameBall.getSize());
            double angle1 = Math.atan2(yDiff1, xDiff1);

            gameBall.setmvX((int) (((Math.cos(angle1)))*5));
            gameBall.setmvY((int) (((Math.sin(angle1)))*5) * -1);

        }
        //Hits p2 paddle
        if (p2.contains(
                new Point(
                        (int) (gameBall.getCenterX() + (gameBall.getSize() / 2)), //right-middle X of ball
                        (int) (gameBall.getCenterY())) //right-middle Y of ball
        )) {

            double p2Cent = p2Y + (pSizeY / 2);
            double xDiff2 = pSizeX / 2 + gameBall.getSize() / 2;
            double yDiff2 = p2Cent - (bCent - gameBall.getSize());
            double angle2 = Math.atan2(yDiff2, xDiff2);

            gameBall.setmvX((int) (((Math.cos(angle2)))*5) * -1);
            gameBall.setmvY((int) (((Math.sin(angle2)))*5) * -1);

        }
        //If the gameBall hits a wall
        double getY = gameBall.getY();
        if ((getY <= 0 + (gameBall.getSize()) || getY >= fSizeY - (gameBall.getSize()))) {
            gameBall.setmvY((int) (gameBall.getmvY() * -1));
        }

        int getX = (int) gameBall.getX();
        if (getX <= 0) {
            //gameBall.setmvX(gameBall.getmvX() * -1);
            score(1);
        } else if (getX >= fSizeX - (gameBall.getSize())) {
            score(2);
        }

        gameBall.move();
    }

    public void score(int player) {

        System.out.println("Player " + player + " scores!");
        reset();

    }

    public void reset() {
        //Reset gameball
            System.out.println("DURING: Resetting gameBall");
        
            Random rand = new Random();
            int rInt;
            //rnum = rand.nextInt((max - min) + 1 + min);

            gameBall.setX(fSizeX/2 - (int)gameBall.getSize());
            System.out.println("Setting setX() to " + gameBall.getX());
            gameBall.setY(fSizeX/2 + (int)gameBall.getSize()/2);

            rInt = rand.nextInt(2)*2-1;
            double x = ((Math.PI / 2) * (double)rInt) - ((Math.PI / 4) * (double)rInt );
            System.out.println("rInt at x: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvX = (int) ( ((Math.cos(x) * rInt)) * 10 );
            System.out.println("mvX: " + mvX);
            System.out.println("rInt at mvX: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvY = (int) ( ((Math.sin(x) * rInt)) * 10 );
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

    public static void main(String[] args) {
        JFrame frame = new Pong();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pong");

        frame.setSize(fSizeX, fSizeY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Reset gameball
            System.out.println("START: Resetting gameBall");
        
            Random rand = new Random();
            int rInt;
            //rnum = rand.nextInt((max - min) + 1 + min);

            gameBall.setX(fSizeX/2 - (int)gameBall.getSize());
            System.out.println("Setting setX() to " + gameBall.getX());
            gameBall.setY(fSizeX/2 + (int)gameBall.getSize()/2);

            rInt = rand.nextInt(2)*2-1;
            double x = ((Math.PI / 2) * (double)rInt) - ((Math.PI / 4) * (double)rInt );
            System.out.println("rInt at x: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvX = (int) ( ((Math.cos(x) * rInt)) * 3 );
            System.out.println("mvX: " + mvX);
            System.out.println("rInt at mvX: " + rInt);
            
            rInt = rand.nextInt(2)*2-1;
            double mvY = (int) ( ((Math.sin(x) * rInt)) * 3 );
            System.out.println("mvY: " + mvY);
            System.out.println("rInt at mvY: " + rInt);
            
            gameBall.setmvX((int)mvX);
            gameBall.setmvY((int)mvY);
            
            System.out.println(p1.getBounds().toString());
            System.out.println(p2.getBounds().toString());
            
            
            
            
            frame.setVisible(true);
    }

}
