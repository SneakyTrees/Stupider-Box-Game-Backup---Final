package stupiderboxgame;

public class GameAssembler {
    
    GamePanel gamePanel;
    EventManager gameEventManager;
    
    public GameAssembler(GamePanel gp, EventManager gem){
        gamePanel = gp;
        gameEventManager = gem;
    }
    
    public void handleKeyPressedA(){
        
    }
    
    public GamePanel getGamePanel(){
        return gamePanel;
    }
    
    public EventManager getGameEventManager(){
        return gameEventManager;
    }
}
