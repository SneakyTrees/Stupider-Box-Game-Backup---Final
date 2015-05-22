package stupiderboxgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
import java.util.*;

public class GameManager {
    EventManager gameEventManager;
    Screen gameScreen;
    
    public GameManager(EventManager gem){
        gameEventManager = gem;
        gameScreen = gameEventManager.getEventManagerScreen();
    }
    
    public void initializeGameManager(PlayerBox pb, ArrayList<EnemyBox> eb){
        gameScreen.addPlayerBox(pb);
        gameScreen.addEnemyBoxes(eb);
        //Later add a neverending event that checks for player death and ends game upon it
    }
    
    public void handleKeyPressed(int keyCode){
        //System.out.println("key pressed");
        if(keyCode == KeyEvent.VK_W){
            this.handleWKeyPressed();
        }
        else if(keyCode == KeyEvent.VK_A){
            this.handleAKeyPressed();
        }
        else if(keyCode == KeyEvent.VK_S){
            this.handleSKeyPressed();
        }
        else if(keyCode == KeyEvent.VK_D){
            this.handleDKeyPressed();
        }
        else if(keyCode == KeyEvent.VK_F){
            this.handleFKeyPressed();
        }
        else{
            //Do noffin
        }
        
    }
    
    public void handleWKeyPressed(){
        if(gameScreen.getPlayerBox() == null){
            return;
        }
        gameEventManager.addEvent(new TranslateBoxAtAngleEvent(false, gameScreen, gameEventManager, gameScreen.getPlayerBox(), 90, false));
    }
    
    public void handleAKeyPressed(){
        if(gameScreen.getPlayerBox() == null){
            return;
        }
        gameEventManager.addEvent(new TranslateBoxAtAngleEvent(false, gameScreen, gameEventManager, gameScreen.getPlayerBox(), 180, false));
    }
        
    public void handleSKeyPressed(){
        if(gameScreen.getPlayerBox() == null){
            return;
        }
        gameEventManager.addEvent(new TranslateBoxAtAngleEvent(false, gameScreen, gameEventManager, gameScreen.getPlayerBox(), 270, false));
    }
            
    public void handleDKeyPressed(){
        if(gameScreen.getPlayerBox() == null){
            return;
        }
        gameEventManager.addEvent(new TranslateBoxAtAngleEvent(false, gameScreen, gameEventManager, gameScreen.getPlayerBox(), 0, false));
    }
    
    public void handleFKeyPressed(){
        this.spawnEnemies();
        gameEventManager.addEvent(new ShootAtPlayerBoxTimerEvent(true, 1.25, gameScreen, gameEventManager, gameScreen.getShootingEnemyAt(0)));
        gameEventManager.addEvent(new ShootAtPlayerBoxTimerEvent(true, 1.25, gameScreen, gameEventManager, gameScreen.getShootingEnemyAt(1)));
        //Add tracking event
        gameEventManager.addEvent(new TrackBoxTowardsPlayerBoxEvent(false, (TrackingBox) gameScreen.getEnemyBoxAt(0), gameScreen, gameEventManager));
        
    }
    
    public void spawnEnemies(){
        gameScreen.addShootingEnemy(new ShootingEnemy(25, 25, new Coordinates(750, 750), 25, 100, false, 0, 10));
        gameScreen.addShootingEnemy(new ShootingEnemy(25, 25, new Coordinates(250, 250), 25, 100, false, 0, 10));
        //Add tracking enemy
        Random rand = new Random();
        
        //int between 0-3
        //0 spawns at the lower fith of the screen, 1 at the right side, 2 at the top, 3 at the left; set min and max x's accordintly
        int spawnSide = rand.nextInt((3 - 0) + 1) + 0;

        //Later just change these to the diff of maxX-minX; too tired to do this now
        Coordinates spawnPoint = new Coordinates(500, 500);
        if(spawnSide == 0){
            spawnPoint = new Coordinates(0.1f*gameScreen.getMaxX(), 0.1f*gameScreen.getMaxY());    
        }
        else if(spawnSide == 1){
            spawnPoint = new Coordinates(0.1f*gameScreen.getMaxX(), 0.9f*gameScreen.getMaxY());
        }
        else if(spawnSide == 2){
            spawnPoint = new Coordinates(0.9f*gameScreen.getMaxX(), 0.9f*gameScreen.getMaxY());
        }
        else if(spawnSide == 3){
            spawnPoint = new Coordinates(0.9f*gameScreen.getMaxX(), 0.1f*gameScreen.getMaxY());
        }
        else{

        }
        
        gameScreen.addEnemyBox(new TrackingBox(50, 50, spawnPoint, 300, 30, false, 150, 50));
    }
    
    public void handleMouseButtonPressed(MouseEvent me, int panelWidth, int panelHeight){
        this.handleLeftMouseButtonClicked(me, panelWidth, panelHeight);
    }
    
    public void handleLeftMouseButtonClicked(MouseEvent me, int panelWidth, int panelHeight){
        float xScale = Math.abs(gameScreen.getMaxX()-gameScreen.getMinX())/panelWidth;
        float yScale = Math.abs(gameScreen.getMaxY()-gameScreen.getMinY())/panelHeight;
        gameScreen.getPlayerBox().fireProjectileFromCenter(new Coordinates(me.getX()*xScale, (Math.abs(panelHeight-me.getY()))*yScale), gameScreen, gameEventManager);
    }
    
    public void setGameEventManager(EventManager gem){
        gameEventManager = gem;
    }
    
    public void setGameScreen(Screen gs){
        gameScreen = gs;
    }
    
    public EventManager getGameEventManager(){
        return gameEventManager;
    }
    
    public Screen getGameScreen(){
        return gameScreen;
    }
}
