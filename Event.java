package stupiderboxgame;

public abstract class Event {
    
    public static final double UNIVERSAL_EPSILON = 0.00000000001;

    public abstract boolean executeEvent(double timeDelta);
}
