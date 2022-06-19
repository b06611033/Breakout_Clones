package BreakoutClones;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.Arrays;


    public class Bricks {
        public boolean[][] bricksArray;
        public int brickWidth;
        public int brickHeight;
        public Bricks(int rows, int cols) {
            bricksArray = new boolean[rows][cols];
            brickWidth = 600/cols;
            brickHeight = 240/rows;
        }
    
        public void doDrawing(Graphics2D g) {
            for (int i = 0; i < bricksArray.length; i++) {
                for (int j = 0; j < bricksArray[0].length; j++) {
                    if (!bricksArray[i][j]) {
                        g.setColor(new Color(192,192,192));
                        g.fillRect(100 + brickWidth*j, 50 + brickHeight*i, brickWidth,brickHeight);
                        
                        g.setStroke(new BasicStroke(3));
                        g.setColor(new Color(240,255,240));
                        g.drawRect(100 + brickWidth*j, 50
                         + brickHeight*i, brickWidth,brickHeight);
                    }  
                }
             } 
        }

        public void brickDestroyed(int row, int col) {
             bricksArray[row][col] = true;
        }
       
    }
