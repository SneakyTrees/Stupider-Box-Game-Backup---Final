package stupiderboxgame;

//This is an event type that needs to use a time delta; essentially just a way of expressing that a particular set of instructions
//need to be continually executed across frames
public abstract class DeltaEvent extends Event{
    
    boolean isNeverEnding;
    
    public DeltaEvent(boolean neverending){
        isNeverEnding = neverending;
    }
    
    @Override
    public abstract boolean executeEvent(double timeDelta);
}
