package stupiderboxgame;

public class TrackBoxTowardsPlayerBoxEvent extends DeltaEvent{
    TrackingBox trackingBox;
    Screen trackScreen;
    EventManager trackManager;
    
    public TrackBoxTowardsPlayerBoxEvent(boolean neverending, TrackingBox tb, Screen s, EventManager em){
        super(neverending);
        trackingBox = tb;
        trackScreen = s;
        trackManager = em;
    }
    
    @Override
    public boolean executeEvent(double timeDelta){
        if(trackScreen.getPlayerBox() == null){
            return false;
        }
        
        if(!trackScreen.getAllBoxesInScreenList().contains(trackingBox)){
            return false;
        }
        
        trackingBox.trackTowardsPlayerBox((float) (trackingBox.getSpeed()*timeDelta), trackScreen, trackManager);
        return true;
    }
}
