
package com.ngse.riib;

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
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class TestPong extends JFrame implements ActionListener, KeyListener {

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
    public TestPong() {
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
        if( new Rectangle(p1X,p1Y,pSizeX,pSizeY).contains(
            new Point(
                (int) (gameBall.getCenterX()-(gameBall.getSize()/2)), //left-middle X of ball
                (int) (gameBall.getCenterY())) //left-middle Y of ball
        )
        ) {

            double p1Cent = p1Y + ( pSizeY / 2 );
            double xDiff1 = pSizeX / 2 + gameBall.getSize()/2;
            double yDiff1 = p1Cent - (bCent-gameBall.getSize());
            double angle1 = Math.atan2(yDiff1, xDiff1);

            gameBall.setmvX( (int) (2 * Math.cos(angle1)));
            gameBall.setmvY( (int) (2 * Math.sin(angle1) * -1));

        }
		//Hits p2 paddle
        if( new Rectangle(p2X,p2Y,pSizeX,pSizeY).contains(
            new Point(
                (int) (gameBall.getCenterX()+(gameBall.getSize()/2)), //right-middle X of ball
                (int) (gameBall.getCenterY())) //right-middle Y of ball
        )
        ) {

            double p2Cent = p2Y + ( pSizeY / 2 );
            double xDiff2 = pSizeX / 2 + gameBall.getSize()/2;
            double yDiff2 = p2Cent - (bCent-gameBall.getSize());
            double angle2 = Math.atan2(yDiff2, xDiff2);

            gameBall.setmvX( (int) (2 * Math.cos(angle2)));
            gameBall.setmvY( (int) (2 * Math.sin(angle2) * -1));

        }
        //If the gameBall hits a wall
        double getY = gameBall.getY();
        if ((getY <= 0 + (gameBall.getSize()) || getY >= fSizeY - (gameBall.getSize()))) {
            gameBall.setmvY( (int) (gameBall.getmvY() * -1));
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
        gameBall.setX(fSizeX / 2);
        gameBall.setY( (int) ( (fSizeY / 2) + ( ((Math.random() * 19) + 1) * (int) ((Math.random()) * 2) - 1 ) ) );
        
        double x = ( (Math.random()*Math.PI/2)-Math.PI/4 );
        gameBall.setmvX( (int) ((Math.cos(x) * ((((int) (Math.random()*2) - 1) + 1)*2))));
        gameBall.setmvY( (int) ((Math.sin(x) * ((((int) (Math.random()*2) - 1) + 1)*2))));
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
        JFrame frame = new TestPong();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pong");

        frame.setSize(fSizeX, fSizeY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        
        //Reset gameball
        gameBall.setX(fSizeX / 2);
        gameBall.setY( (int) ( (fSizeY / 2) + ( ((Math.random() * 19) + 1) * (int) ((Math.random()) * 2) - 1 ) ) );
        
        double x = ( (Math.random()*Math.PI/2)-Math.PI/4 );
        gameBall.setmvX( (int) ((Math.cos(x) * ((((int) (Math.random()*2) - 1) + 1)*2))));
        
<<<<<<< HEAD
        //Math.cos(x) * (int) (Math.random()*2)
=======
        Math.cos(x) * (int) (Math.random()*2)
>>>>>>> FETCH_HEAD
        
        gameBall.setmvY( (int) ((Math.sin(x) * ((((int) (Math.random()*2) - 1) + 1)*2))));

        frame.setVisible(true);
        System.out.println(x);
        System.out.println(gameBall.getmvX());
        System.out.println(gameBall.getmvY());
    }

}
