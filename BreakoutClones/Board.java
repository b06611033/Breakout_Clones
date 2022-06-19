package BreakoutClones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.Random;

public class Board extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int highScore = 0;
    private int brickNum = 30;
    private Timer timer;
    private int delay = 8;
    private int paddleX = 310;
    private int paddleLength = 100;
    Random r = new Random();
    private int ballPosX;
    private int ballPosY;
    private int ballDirX;
    private int ballDirY;
    private boolean showInstructions = false;
    private boolean newGame = false;
    private boolean firstGame = true;
    private Bricks bricks;

    public Board() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (newGame) {
            //background
            g.setColor(new Color(135,206,235));
            g.fillRect(1, 1, 792, 792);
            //border
            g.setColor(new Color(51,0,102));
            g.fillRect(0, 0, 792, 5);
            g.fillRect(782, 0, 5, 792);
            g.fillRect(0, 0, 5, 792);
            //paddle
            g.setColor(new Color(34,139,34));
            g.fillRect(paddleX, 753, paddleLength, 10);
            //ball
            g.setColor(new Color(255,255,255));
            g.fillOval(ballPosX, ballPosY, 20, 20);
            //bricks
            bricks.doDrawing((Graphics2D)g);
        }
       
        //score
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 600, 450);
        g.drawString("High Score: " + highScore, 600, 25);
        //game states
        if (firstGame) {
            g.drawString("Press 1 to start in easy mode!" , 150, 510);
            g.drawString("Press 2 to start in medium mode!" , 150, 540);
            g.drawString("Press 3 to start in hard mode!" , 150, 570);
        }
        if (showInstructions) {
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press left or right arrow to start!" , 150, 25);
        }
        else if (score == brickNum) {
            play = false;
            newGame = false;
            g.setColor(new Color(255,215,0));
            g.setFont(new Font("serif", Font.BOLD, 72));
            g.drawString("You won!" , 150, 450);
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press 1 to restart in easy mode!" , 150, 510);
            g.drawString("Press 2 to restart in medium mode!" , 150, 540);
            g.drawString("Press 3 to restart in hard mode!" , 150, 570);
        }
        else if (ballPosY > 765) {
            play = false;
            newGame = false;
            g.setColor(new Color(255,0,0));
            g.setFont(new Font("serif", Font.BOLD, 72));
            g.drawString("Game Over!" , 150, 400);
            g.setFont(new Font("serif", Font.BOLD, 36));
            if (score == highScore)  g.drawString("New High Score!", 150, 450);
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press 1 to restart in easy mode!" , 150, 510);
            g.drawString("Press 2 to restart in medium mode!" , 150, 540);
            g.drawString("Press 3 to restart in hard mode!" , 150, 570);
            g.setFont(new Font("serif", Font.BOLD, 50));
        }
        //g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
           ballPosX += ballDirX;
           ballPosY += ballDirY; 
           if (ballPosY > 730 && ballPosY < 740 && ballPosX + 15 > paddleX && ballPosX + 5 < paddleX + paddleLength) ballDirY = -ballDirY;  // collision with paddle
           if (ballPosX > 765 || ballPosX < 0) ballDirX = -ballDirX; // collision with boundaries
           if (ballPosY < 10) ballDirY = -ballDirY; // collision with boundaries
           // collision with bricks
           for (int i = 0; i < bricks.bricksArray.length; i++) {
               boolean collided = false;
               for (int j = 0; j < bricks.bricksArray[0].length; j++) {
                   if (!bricks.bricksArray[i][j]) {
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = new Rectangle(100 + bricks.brickWidth*j, 50 + bricks.brickHeight*i, bricks.brickWidth, bricks.brickHeight);
                        if (ballRect.intersects(brickRect)) {
                            score++;
                            if (score > highScore) highScore = score;
                            if (ballPosX + 19 <= 100 + bricks.brickWidth*j || ballPosX + 1 >= 100 + bricks.brickWidth*(j + 1)) ballDirX = -ballDirX;
                            else ballDirY = -ballDirY;
                            bricks.brickDestroyed(i, j);
                            collided = true;
                            break;
                        }
                   }
               }
               if (collided) break;
           }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && newGame) {
            if (paddleX > 680) paddleX = 700;
            else moveRight();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && newGame) {
            if (paddleX < 25) paddleX = 5;
            else moveLeft();
        }
        else if (e.getKeyCode() == KeyEvent.VK_1 && !newGame) {
            if(!play) {
                score = 0;
                paddleX = 310;
                ballPosX = 60 + r.nextInt(500);
                ballPosY = 350 + r.nextInt(200);
                ballDirX = -3 - r.nextInt(1);
                ballDirY = -3 - r.nextInt(1);
                bricks = new Bricks(5,6);
                brickNum = 30;
                firstGame = false;
                showInstructions = true;
                newGame = true;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_2 && !newGame) {
            if(!play) {
                score = 0;
                paddleX = 310;
                ballPosX = 60 + r.nextInt(500);
                ballPosY = 350 + r.nextInt(200);
                ballDirX = -4 - r.nextInt(2);
                ballDirY = -4 - r.nextInt(2);
                bricks = new Bricks(6,8);
                brickNum = 48;
                firstGame = false;
                showInstructions = true;
                newGame = true;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_3 && !newGame) {
            if(!play) {
                score = 0;
                paddleX = 310;
                ballPosX = 60 + r.nextInt(500);
                ballPosY = 350 + r.nextInt(200);
                ballDirX = -6 - r.nextInt(1);
                ballDirY = -6 - r.nextInt(1);
                bricks = new Bricks(8,10);
                brickNum = 80;
                firstGame = false;
                showInstructions = true;
                newGame = true;
            }
        }

    }
    
    public void moveRight() {
        play = true;
        showInstructions = false;
        paddleX += 30;
    }
    private void moveLeft() {
        play = true;
        showInstructions = false;
        paddleX -= 30;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
