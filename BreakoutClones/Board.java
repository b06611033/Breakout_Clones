package BreakoutClones;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel implements KeyListener, ActionListener {
    private int score = 0;
    private int highScore = 0;
    private int brickNum = 100;
    private int brickWidth;
    private int brickHeight;
    private int row;
    private int col;
    private Timer timer;
    private int delay = 8;
    private int paddleX;
    private final int paddleLength = 100;
    Random r = new Random();
    private int ballPosX;
    private int ballPosY;
    private int ballDirX;
    private int ballDirY;
    private boolean play = false;
    private boolean showInstructions = false;
    private boolean newGame = false;
    private boolean firstGame = true;
    private Bricks bricks;
    private boolean easyMode = false;
    private boolean mediumMode = false;
    private boolean hardMode = false;

    BufferedImage img1;
    BufferedImage img2;
    BufferedImage img3;

    public Board() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        try {
            img3 = ImageIO.read(new File("BreakoutClones/Stars.jpg"));
            img2 = ImageIO.read(new File("BreakoutClones/Ocean.png"));
            img1 = ImageIO.read(new File("BreakoutClones/Forest.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        if (newGame) {
            if (hardMode) {
                // background
                g.drawImage(img3, 1, 1, 792, 792, 1, 1, img3.getWidth(), img3.getHeight(), this);
                // border
                g.setColor(new Color(51, 0, 102));
                drawBorder(g);
                // paddle
                g.setColor(new Color(25, 25, 112));
                g.fillRect(paddleX, 753, paddleLength, 10);
                // ball
                g.setColor(new Color(255, 255, 255));
                g.fillOval(ballPosX, ballPosY, 20, 20);
                // bricks
                bricks.doDrawing((Graphics2D) g, 3);
            } else if (mediumMode) {
                g.drawImage(img2, 1, 1, 792, 792, 300, 1, 300 + img2.getHeight(), img2.getHeight(), this);
                // border
                g.setColor(new Color(0, 0, 102));
                drawBorder(g);
                // paddle
                g.setColor(new Color(255, 127, 80));
                g.fillRect(paddleX, 753, paddleLength, 10);
                // ball
                g.setColor(new Color(127, 255, 212));
                g.fillOval(ballPosX, ballPosY, 20, 20);
                // bricks
                bricks.doDrawing((Graphics2D) g, 2);
            } else if (easyMode) {
                g.drawImage(img1, 1, 1, 792, 792, 300, 1, 300 + img1.getHeight(), img1.getHeight(), this);
                // border
                g.setColor(new Color(34, 139, 34));
                drawBorder(g);
                // paddle
                g.setColor(new Color(165, 42, 42));
                g.fillRect(paddleX, 753, paddleLength, 10);
                // ball
                g.setColor(new Color(0, 255, 0));
                g.fillOval(ballPosX, ballPosY, 20, 20);
                // bricks
                bricks.doDrawing((Graphics2D) g, 1);
            }
        }
        // score
        if (mediumMode)
            g.setColor(new Color(0, 0, 0));
        else
            g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 620, 450);
        g.drawString("High Score: " + highScore, 620, 25);
        // game states
        if (firstGame) {
            g.setColor(new Color(0, 0, 0));
            showMode(g);
        }
        if (showInstructions) {
            if (mediumMode)
                g.setColor(new Color(0, 0, 0));
            else
                g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press left or right arrow to start!", 220, 25);
        } else if (score == brickNum) {
            play = false;
            newGame = false;
            g.setColor(new Color(255, 215, 0));
            g.setFont(new Font("serif", Font.BOLD, 72));
            g.drawString("You won!", 150, 450);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("serif", Font.BOLD, 25));
            showMode(g);
        } else if (ballPosY > 765) {
            play = false;
            newGame = false;
            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("serif", Font.BOLD, 72));
            g.drawString("Game Over!", 150, 400);
            g.setFont(new Font("serif", Font.BOLD, 36));
            if (score == highScore)
                g.drawString("New High Score!", 150, 450);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("serif", Font.BOLD, 25));
            showMode(g);
            g.setFont(new Font("serif", Font.BOLD, 50));
        }
    }

    public void drawBorder(Graphics g) {
        g.fillRect(0, 0, 792, 5);
        g.fillRect(782, 0, 5, 792);
        g.fillRect(0, 0, 5, 792);
    }

    public void showMode(Graphics g) {
        g.drawString("Press 1 to restart in easy mode!", 150, 510);
        g.drawString("Press 2 to restart in medium mode!", 150, 540);
        g.drawString("Press 3 to restart in hard mode!", 150, 570);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if (ballPosY > 730 && ballPosY < 740 && ballPosX + 15 > paddleX && ballPosX + 5 < paddleX + paddleLength)
                ballDirY = -ballDirY; // collision with paddle
            if (ballPosX > 765 || ballPosX < 0)
                ballDirX = -ballDirX; // collision with boundaries
            if (ballPosY < 10)
                ballDirY = -ballDirY; // collision with boundaries
            // collision with bricks
            for (int i = 0; i < row; i++) {
                boolean collided = false;
                for (int j = 0; j < col; j++) {
                    if (bricks.brickExists(i, j)) {
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = new Rectangle(100 + brickWidth * j, 50 + brickHeight * i,
                                brickWidth, brickHeight);
                        if (ballRect.intersects(brickRect) && !bricks.surrounded(i, j)) {
                            score++;
                            if (score > highScore)
                                highScore = score;
                            if ((ballPosX + 20 >= 100 + brickWidth * j && ballPosX <= 100 + brickWidth * j - 15)
                                    || (ballPosX <= 100 + brickWidth * (j + 1)
                                            && ballPosX + 20 >= 100 + brickWidth * (j + 1) + 15))
                                ballDirX = -ballDirX;

                            if ((ballPosY + 20 >= 50 + brickHeight * i && ballPosY <= 50 + brickHeight * i - 15)
                                    || (ballPosY <= 50 + brickHeight * (i + 1)
                                            && ballPosY + 20 >= 50 + brickHeight * (i + 1) + 15))
                                ballDirY = -ballDirY;

                            bricks.brickDestroyed(i, j);
                            collided = true;
                            break;
                        }
                    }
                }
                if (collided)
                    break;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && newGame) {
            if (paddleX > 680)
                paddleX = 700;
            else
                moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && newGame) {
            if (paddleX < 25)
                paddleX = 5;
            else
                moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_1 && !newGame) {
            if (!play) {
                reset(-3 - r.nextInt(1), -3 - r.nextInt(1), 5, 6, true, false, false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_2 && !newGame) {
            if (!play) {
                reset(-4 - r.nextInt(1), -4 - r.nextInt(1), 6, 8, false, true, false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_3 && !newGame) {
            if (!play) {
                reset(-5, -5, 6, 10, false, false, true);
            }
        }

    }

    public void reset(int ballDirX, int ballDirY, int row, int col, boolean easy, boolean medium, boolean hard) {
        score = 0;
        paddleX = 310;
        ballPosX = 60 + r.nextInt(500);
        ballPosY = 350 + r.nextInt(200);
        this.ballDirX = ballDirX;
        this.ballDirY = ballDirY;
        this.row = row;
        this.col = col;
        bricks = new Bricks(row, col);
        brickWidth = 600 / col;
        brickHeight = 240 / row;
        brickNum = row * col;
        firstGame = false;
        showInstructions = true;
        newGame = true;
        easyMode = easy;
        mediumMode = medium;
        hardMode = hard;
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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
