package stupiderboxgame;

public class TranslateBoxInstantEvent extends InstantEvent{
    Screen translateScreen;
    EventManager translateManager;
    EntityBox translatedBox;
    float xDist;
    float yDist;
    
    public TranslateBoxInstantEvent(Screen s, EventManager em, EntityBox tb, float xdist, float ydist){
        translateScreen = s;
        translateManager = em;
        translatedBox = tb;
        xDist = xdist;
        yDist = ydist;
    }
    
    //Needs no paramaters, gets all relevant data from its instance variables (hopefully this sort of design setup works, IDK =/)
    @Override
    public void executeInstantEvent(){
        translatedBox.translateBox(xDist, yDist, translateScreen, translateManager);
    }
}
