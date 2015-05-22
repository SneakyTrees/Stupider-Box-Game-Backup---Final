package stupiderboxgame;

public class TranslateBoxTimerEvent extends TimerEvent{
    
    Screen translateScreen;
    EventManager translateManager;
    EntityBox translatingBox;
    float xDist;
    float yDist;
    boolean removeOnEnd;
    
    public TranslateBoxTimerEvent(boolean neverending, double duration, Screen s, EventManager em, EntityBox transbox, float xdist, float ydist, boolean remove){
        super(neverending, duration);
        translateScreen = s;
        translateManager = em;
        translatingBox = transbox;
        xDist = xdist;
        yDist = ydist;
        removeOnEnd = remove;
    }
    
    @Override
    public void executeTimerEvent(double timeDelta){
        //dist is known in units, time is known in seconds, now find distance in units/second
        //find the number of units to translate based on this many seconds (delta)
        //speedX = xDist/eventDuration -> speedX*delta
        translatingBox.translateBox((float) (xDist/eventDuration*timeDelta), (float) (yDist/eventDuration*timeDelta), translateScreen, translateManager);
    }
    
    @Override
    public void executeEndingProcess(){
        if(removeOnEnd){
            //Ned to also remove from special box list thing
            translateScreen.getAllBoxesInScreenList().remove(translatingBox);
        }
    }
}
