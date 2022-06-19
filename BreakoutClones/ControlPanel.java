package BreakoutClones;
import javax.swing.*;
import java.awt.event.*;


public class ControlPanel extends JPanel {
    
    public ControlPanel() {
        initUI();
    }

    private void initUI() {
        JPanel ControlPanel = new JPanel();
        ControlPanel.setFocusable(false);
        add(ControlPanel);
        JButton quitButton = new JButton("Quit");
        JLabel infoLabel = new JLabel("Please press left arrow or right arrow to start!");
        ControlPanel.add(infoLabel);
        infoLabel.setBounds(0, 0, 100, 30);
        ControlPanel.add(quitButton);
        ControlPanel.setSize(800, 40);
        ControlPanel.setVisible(true);
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(new ButtonClickListener()); 
    }
    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
           String command = e.getActionCommand();  
           if( command.equals( "quit" ) )  {
             System.exit(0);
           }
        }		
     }
    
    public static void main(String[] args) {
       ControlPanel cp =new ControlPanel();
       JFrame f = new JFrame();
       f.setSize(600,200);
       f.add(cp);
       f.setVisible(true);
    }
}