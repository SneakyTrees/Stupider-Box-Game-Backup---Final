package stupiderboxgame;

public abstract class InstantEvent extends Event{
    /*
    I know this is kinda shit design, wasting needless paramaters, but seeing as how the only alternative is splitting up Event
    //into time and non-time types, which would mean that processEvents() has to check for which type and call the approrpiate executeEvent()
    , I don't really give a fuck considering how much more efficient and simpler this way is
    */
    
    //Execute the event for exactly one frame only
    @Override
    public boolean executeEvent(double timeDelta){
        this.executeInstantEvent();
        return false;
    }
    
    public abstract void executeInstantEvent();
}
