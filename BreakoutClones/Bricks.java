package BreakoutClones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

    public class Bricks {
        public boolean[][] bricksArray;
        public int brickWidth;
        public int brickHeight;
        public Bricks(int rows, int cols) {
            bricksArray = new boolean[rows][cols];
            brickWidth = 600/cols;
            brickHeight = 240/rows;
        }
    
        public void doDrawing(Graphics2D g, int mode) {
            for (int i = 0; i < bricksArray.length; i++) {
                for (int j = 0; j < bricksArray[0].length; j++) {
                    if (!bricksArray[i][j]) {
                        if (mode == 1) g.setColor(new Color(50,165,50));
                        else if (mode == 2)  g.setColor(new Color(100,149,237));
                        else g.setColor(new Color(75,0,130));
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
