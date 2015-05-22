package stupiderboxgame;

public class TrackBoxTowardsBoxTimerEvent extends TimerEvent{
    EntityBox trackingBox;
    EntityBox trackedBox;
    
    public TrackBoxTowardsBoxTimerEvent(boolean neverending, float duration, EntityBox trackingbox, EntityBox trackedbox){
        super(neverending, duration);
        trackingBox = trackingbox;
        trackedBox = trackedbox;
    }
    
    @Override
    public void executeTimerEvent(double timeDelta){
        //Translate trackingBox at translateBoxAtAngle(trackingBox.getAngleToPerfectBox(trackedBox), difference of center x's, difference of center y's)
    }
    
    @Override
    public void executeEndingProcess(){
        
    }
}
