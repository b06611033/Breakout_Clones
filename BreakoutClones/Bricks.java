package BreakoutClones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Bricks {
    private boolean[][] bricksArray;
    private int brickWidth;
    private int brickHeight;
    private int rows;
    private int cols;

    public Bricks(int rows, int cols) {
        bricksArray = new boolean[rows][cols];
        brickWidth = 600 / cols;
        brickHeight = 240 / rows;
        this.rows = rows;
        this.cols = cols;
    }

    public void doDrawing(Graphics2D g, int mode) {
        for (int i = 0; i < bricksArray.length; i++) {
            for (int j = 0; j < bricksArray[0].length; j++) {
                if (!bricksArray[i][j]) {
                    if (mode == 1)
                        g.setColor(new Color(50, 165, 50));
                    else if (mode == 2)
                        g.setColor(new Color(100, 149, 237));
                    else
                        g.setColor(new Color(75, 0, 130));
                    g.fillRect(100 + brickWidth * j, 50 + brickHeight * i, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(new Color(240, 255, 240));
                    g.drawRect(100 + brickWidth * j, 50
                            + brickHeight * i, brickWidth, brickHeight);
                }
            }
        }
    }

    public boolean brickExists(int row, int col) {
        return !bricksArray[row][col];
    }

    public boolean surrounded(int row, int col) {
        if (row == rows - 1 || row == 0 || col == cols - 1 || col == 0)
            return false;
        return !bricksArray[row][col - 1] && !bricksArray[row][col + 1] && !bricksArray[row + 1][col]
                && !bricksArray[row - 1][col];
    }

    public void brickDestroyed(int row, int col) {
        bricksArray[row][col] = true;
    }

}
