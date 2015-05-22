package stupiderboxgame;

public class TranslateBoxAtAngleEvent extends DeltaEvent{
    //Here we translate at distance and subtract it from the distToBeTraveled variable each iteration
    //That distance subtraced/traveled each time is equivalent to the speedOfBox*timeDelta
    Screen translateScreen;
    EventManager translateManager;
    EntityBox translatingBox;
    float translateAngle;
    float remainingDist;
    boolean removeOnEnd;
    Coordinates previousCenter = null;
    
    public TranslateBoxAtAngleEvent(boolean neverending, Screen s, EventManager em, EntityBox eb, float angle, boolean remove){
        super(neverending);
        translateScreen = s;
        translateManager = em;
        translatingBox = eb;
        translateAngle = angle;
        removeOnEnd = remove;
    }
    
    @Override
    public boolean executeEvent(double timeDelta){
        previousCenter = translatingBox.getCenterPoint();
        if(translatingBox == null){
            this.executeEndingProcess();
            System.out.println("trans box null");
            return false;
            
        }
        
        
        if(!(translateScreen.getAllBoxesInScreenList().contains(translatingBox))){
           return false;
        }

        translatingBox.translateBoxAtAngle(translateAngle, (float) (translatingBox.getSpeed()*timeDelta), translateScreen, translateManager);
        
        if(translatingBox.getCenterPoint().doCoordinatesMatch(previousCenter)){
            //System.out.println("matched");
            this.executeEndingProcess();
            return false;
        }
        
        return true;
    }
    
    public void executeEndingProcess(){
        if(removeOnEnd){
            System.out.println("removed" + translatingBox);
            translateScreen.getAllBoxesInScreenList().remove(translatingBox);
        }
    }
}

