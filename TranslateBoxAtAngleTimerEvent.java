package stupiderboxgame;

public class TranslateBoxAtAngleTimerEvent extends TimerEvent{
    
    Screen translateScreen;
    EventManager translateManager;
    EntityBox translatingBox;
    float translateAngle;
    float translateDist;
    boolean removeOnEnd;
    
    public TranslateBoxAtAngleTimerEvent(boolean neverending, double time, Screen s, EventManager em, EntityBox eb, float angle, float dist, boolean remove){
        super(neverending, time);
        translateScreen = s;
        translateManager = em;
        translatingBox = eb;
        translateAngle = angle;
        translateDist = dist;
        removeOnEnd = remove;
    }
    
    @Override
    public boolean executeEvent(double timeDelta){
        //Should probably find a way to methodize this code but I'm lazy
        remainingTime -= timeDelta;
        if(remainingTime <= 0){
            if(isNeverEnding){
                //Do all yer shananigans and what not; ex. remainingTime = timeDuration
                return true;
            }
            double remainingDelta = remainingTime-Math.abs(remainingTime);

            //Execute the normal code, but with remainingDelta
            this.executeTimerEvent(remainingDelta);
            this.executeEndingProcess();
            
            return false;
        }

        if(!translateScreen.getAllBoxesInScreenList().contains(translatingBox)){
            translateManager.removeEvent(this);
        }
        
        //Execute normal code
        this.executeTimerEvent(timeDelta);
        return true;
    }
    
    @Override
    public void executeTimerEvent(double timeDelta){
        translatingBox.translateBoxAtAngle(translateAngle, (float) (translateDist/eventDuration*timeDelta), translateScreen, translateManager);
    }
    
    @Override
    public void executeEndingProcess(){
        if(removeOnEnd){
            translateScreen.getAllBoxesInScreenList().remove(translatingBox);
        }
    }
}
