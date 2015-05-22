package stupiderboxgame;

public class TranslateBoxEvent extends DeltaEvent{
    
    EntityBox translatingBox;
    Screen translateScreen;
    EventManager translateManager;
    Coordinates previousCenter;
    
    public TranslateBoxEvent(boolean neverending, EntityBox tb, Screen s, EventManager em){
        super(neverending);
        translatingBox = tb;
        translateScreen = s;
        translateManager = em;
        previousCenter = null;
    }
    
    @Override
    public boolean executeEvent(double timeDelta){
        previousCenter = translatingBox.getCenterPoint();
        if(translatingBox == null){
            this.executeEndingProcess();
            return false;
        }
        
        translatingBox.translateBox((float) (translatingBox.getSpeed()*timeDelta), (float) (translatingBox.getSpeed()*timeDelta), translateScreen, translateManager);
        
        if(translatingBox.getCenterPoint().doCoordinatesMatch(previousCenter)){
            this.executeEndingProcess();
            return false;
        }
        
        return true;
    }
    
    public void executeEndingProcess(){
        translateManager.removeEvent(this);
    }
}
