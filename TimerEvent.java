package stupiderboxgame;

public abstract class TimerEvent extends DeltaEvent{
    
    double eventDuration; //In seconds
    double remainingTime;
    
    public TimerEvent(boolean neverEnds, double duration){
        super(neverEnds);
        eventDuration = duration;
        remainingTime = duration;
    }
    
    /*
    In this case, timerEvents need this checking for time and making sure the right delta is passed on
    -Actual functionality is bundled instead in executeTimerEvent()
    */
    @Override
    public boolean executeEvent(double timeDelta){
        //Should probably find a way to methodize this code but I'm lazy
        remainingTime -= timeDelta;
        if(remainingTime <= 0){
            if(isNeverEnding){
                //Do all yer shananigans and what not; ex. remainingTime = timeDuration
                this.executeEndingProcess();
                remainingTime = eventDuration;
                return true;
            }
            double remainingDelta = remainingTime-Math.abs(remainingTime);

            //Execute the normal code, but with remainingDelta
            this.executeTimerEvent(remainingDelta);
            this.executeEndingProcess();
            
            return false;
        }

        //Execute normal code
        this.executeTimerEvent(timeDelta);
        return true;
    }
    
    /*This is only called by those neverEnding TimerEvents if there needs to be a task accomplished when the timer has ended
    //ex. executeTimerEvent method can be empty and only handleNeverEndingTimerEventEnd
    public void handleTimerEventEnd(){
        if(!isNeverEnding){
            return;
        }
        
    }
    */
    
    public abstract void executeTimerEvent(double timeDelta);
    public abstract void executeEndingProcess();
}
