package stupiderboxgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  

public class GameWindow extends JFrame{
    
    public int width;
    public int height;
    
    GameWindow(int w, int h){
        width = w;
        height = h;
        
        this.setTitle("Testing Window");
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
