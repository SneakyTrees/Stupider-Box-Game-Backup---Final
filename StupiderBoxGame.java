
/************************************************************************************************
 
 5/20/2015, 3:04 AM, finally done \o/
 */

package stupiderboxgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  
import java.util.*;

public class StupiderBoxGame {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow(500, 400);
        GamePanel gamePanel = new GamePanel(new GameManager(new EventManager(new Screen(0, 1000, 0, 1000))));
        gamePanel.addListeners();
        gameWindow.add(gamePanel);

        //THIS IS WHAT YOU WROUGHT ON THIS CODE, SENIOR PROJECT, THIS IS THE MESS AND CHAOS ONLY THOU COULD BESTOWETH UPON
        //FROM HELL'S HEART I STAB AT THEE AND CURSE THY NAME A THOUSAND TIMES OVER
       
        
        //TODO/CLEANUP LIST. IDK Where to even start, rushing this has left it more fucked up than a third degree burn victim
        //-simplify paramaters of entityboxes to only gamemanager, gettin gamemanagers screen object
        //-simplify events to only have eventmanager object
        
        ArrayList<EnemyBox> startEnemies = new ArrayList<EnemyBox>();
        //gamePanel.getPanelGameManager().getGameScreen().addShootingEnemy(new ShootingEnemy(25, 25, new Coordinates(750, 750), 25, 100, false, 100, 10));
        //gamePanel.getPanelGameManager().getGameScreen().addShootingEnemy(new ShootingEnemy(25, 25, new Coordinates(250, 250), 25, 100, false, 100, 10));
        
        gamePanel.getPanelGameManager().initializeGameManager(new PlayerBox(75, 75, new Coordinates(500, 500), 150, 100, false), startEnemies);
        
        boolean gameRunning = true;
        double currentTimeDelta;
        long currentTime = System.nanoTime();
        long previousTime = System.nanoTime();
        while(gameRunning){
            currentTime = System.nanoTime();
            currentTimeDelta = (currentTime-previousTime)/1000000000.0;
            
            //Every time a box is moved, add the moving box and moved box to an array for collision box drawing later
                //*note this uses not perfect box classes, but rather box and triangle shapes, with respective collision methods (draw right tris, check, then check for mutual intersections)
            
            //Execute code
            gamePanel.getPanelGameManager().getGameEventManager().processEvents(currentTimeDelta);
            gamePanel.checkForGameOver();
            
            gameWindow.repaint();
            previousTime = currentTime;
        }
    }
}
