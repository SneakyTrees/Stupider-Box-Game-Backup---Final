package stupiderboxgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GamePanel extends JPanel implements KeyListener, MouseListener, ComponentListener{
    
    GameManager panelGameManager;
    float lastPlayerPoints;
    int lastTrackingBoxesEliminatedCount;
    
    public GamePanel(GameManager pgm){
        panelGameManager = pgm;
        this.setFocusable(true);
        this.setBackground(Color.white);
    }
    
    public void addListeners(){
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addComponentListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        //Fresh coat of paint (heh); clears off previous graphics-generated objects like lines, rectangles, etc.
        super.paintComponent(g);
        
        //One possible problem with drawing is that the size of the window != the size of the component because of the fact that the window's borders
        //count as actual, pixel space
        /*
        Because Java's basic graphics library is so sane, things aren't drawn from the lower left, no that would be intuitive and sensible.
        No, they draw from the upper left, which means that all y-wise values will be completely fucked up. This bit fixes that by
        translating the origin of drawing down to the lower left, where goddamn should be, and then reversing the y-scale to 1.0, from -1.0.
        
        JavaFX save us all. 
       */           
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0, this.getSize().getHeight());
        g2d.scale(1.0, -1.0);
        
        //Self-explanatory
        this.drawBoxesOnScreen(g2d);
    }
    
    public void drawBoxesOnScreen(Graphics2D g2d){
        
        if(panelGameManager.getGameScreen().getAllBoxesInScreenList().isEmpty()){
            return;
        }
        
        //Percentage scaling of the boxes' actual coordinates down/up to be able to fit in the JPanel
        float xScale = (float) this.getSize().getWidth()/(Math.abs(panelGameManager.getGameScreen().getMaxX()-panelGameManager.getGameScreen().getMinX()));
        float yScale = (float) this.getSize().getHeight()/(Math.abs(panelGameManager.getGameScreen().getMaxY()-panelGameManager.getGameScreen().getMinY()));
        
        ArrayList<PerfectBox> allBoxesCopyList = (ArrayList<PerfectBox>) panelGameManager.getGameScreen().getAllBoxesInScreenList().clone();
        
        for(PerfectBox pb : allBoxesCopyList){
            if(pb == null){
                System.out.println("Box is null; inside gamePanel drawBoxes");
                continue;
            }
            
            int[] scaledXCoords = {(int) (pb.getSW().getX()*xScale), (int) (pb.getSE().getX()*xScale), (int) (pb.getNE().getX()*xScale), (int) (pb.getNW().getX()*xScale)};
            int[] scaledYCoords = {(int) (pb.getSW().getY()*yScale), (int) (pb.getSE().getY()*yScale), (int) (pb.getNE().getY()*yScale), (int) (pb.getNW().getY()*yScale)};
            
            //Precaution; all boxes on the list should have been added at initialization and removed instead of set to null
            //Also, fuck yo exceptions bitch #printlnDebugSwag

            //Eventually all this stuff will just get converted to straight images/sprites, but this works for now
            if(pb.getClass() == (new PlayerBox(0, 0, new Coordinates(0, 0), 0, 0, false)).getClass()){
                g2d.setColor(new Color(23, 166, 10));
            }
            else if(pb instanceof EnemyBox){
                if(pb instanceof TrackingBox){
                    g2d.setColor(Color.ORANGE);
                }
                else{
                    g2d.setColor(new Color(184, 33, 59));
                }
            }
            else if(pb instanceof ProjectileBox){
                if(pb instanceof DamagingProjectile){
                    DamagingProjectile copy = (DamagingProjectile) pb;
                    if(copy.getSourceBox() instanceof EnemyBox){
                        g2d.setColor(Color.red);
                    }
                    else if(copy.getSourceBox() instanceof PlayerBox){
                        g2d.setColor(Color.green);
                    }
                    else{
                        g2d.setColor(Color.BLACK);
                    }
                }
            }
            else{
                g2d.setColor(Color.black);
            }
            g2d.fillPolygon(scaledXCoords, scaledYCoords, 4);
        }
    }
    
    //I'll get around to using adapter classes eventually
    @Override
    public void keyPressed(KeyEvent ke){
        //System.out.println("keypressed called n GP");
        panelGameManager.handleKeyPressed(ke.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent ke){
        
    }
    
    @Override
    public void keyTyped(KeyEvent ke){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        //panelGameManager.handleMouseButtonPressed(me, (int) this.getSize().getWidth(), (int) this.getSize().getHeight());
    }
    
    @Override
    public void mouseEntered(MouseEvent me){
        
    }
    
    @Override
    public void mouseExited(MouseEvent me){
        
    }
    
    @Override
    public void mousePressed(MouseEvent me){
        panelGameManager.handleMouseButtonPressed(me, (int) this.getSize().getWidth(), (int) this.getSize().getHeight());
    }
    
    @Override
    public void mouseReleased(MouseEvent me){
        
    }
    @Override
    public void componentHidden(ComponentEvent e) {

    }
@Override
    public void componentMoved(ComponentEvent e) {

    }
@Override
    public void componentResized(ComponentEvent e) {
                this.addKeyListener(this);
    }

    @Override
    public void componentShown(ComponentEvent e) {

        this.addKeyListener(this);
    }    
    
    public void checkForGameOver(){
        if(panelGameManager.getGameScreen().getPlayerBox() == null){
            this.repaint();
            GameWindow notifierWindow = new GameWindow((int) (0.5f * this.getSize().getWidth()), (int) (0.5f * this.getSize().getHeight()));
            notifierWindow.getContentPane().setBackground(Color.white);
            if(lastTrackingBoxesEliminatedCount == 1){
                notifierWindow.add(new JLabel("Game over. " + lastTrackingBoxesEliminatedCount + " pursuing box eliminated in total. :(", JLabel.CENTER));
            }
            else{
                notifierWindow.add(new JLabel("Game over. " + lastTrackingBoxesEliminatedCount + " pursuing boxes eliminated in total.", JLabel.CENTER));
            }
        }
        lastPlayerPoints = panelGameManager.getGameScreen().getPlayerBox().playerPoints;
        lastTrackingBoxesEliminatedCount = panelGameManager.getGameScreen().getPlayerBox().trackingBoxesEliminated;
    }
    
    public GameManager getPanelGameManager(){
        return panelGameManager;
    }
}
