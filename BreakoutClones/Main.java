package BreakoutClones;
import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        JFrame mainFrame = new JFrame("Breakout Clones!");
        mainFrame.setBounds(10,10, 800, 800);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board b = new Board();
        mainFrame.add(b);
        mainFrame.setVisible(true);
        
    }
}
