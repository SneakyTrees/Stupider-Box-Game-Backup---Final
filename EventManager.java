package stupiderboxgame;

import java.util.*;
import java.util.concurrent.*;

public class EventManager {
   
    Screen eventManagerScreen;
    ArrayList<Event> eventQueueList = new ArrayList<Event>();
    
    public EventManager(Screen es){
        eventManagerScreen = es;
    }
    
    public void processEvents(double timeDelta){
        //No need for copying over eventQueuList to another clone for events that may be added while this is processing during the main loop
        //Never mind, concurrent modificatino is a thing
        
        //This took way longer than it should have jesus h christo
        
        //Copy eventQueue over to a static array, avoiding concurrent mod exception while keeping a deep copy of all eventQueueList's events
        //While this is going, new events may be added to eventQueueList, which will all be processed on the next frame of the game
        Event[] eventQueueArr = new Event[0];
        eventQueueArr = (Event[]) eventQueueList.toArray(eventQueueArr);
        
        //When events return false, indicating they are done doing w/e, they need to be removed, which is what this does
        //We add those events to a list so the relevant, real copies in the actual queue can be deleted as they are needed, thank fuckin
        //god for removeAll()
        ArrayList<Event> eventsToBeRemoved = new ArrayList<Event>();
        for(int i = 0; i < eventQueueArr.length; i++){
            if(!(eventQueueArr[i].executeEvent(timeDelta))){
                eventsToBeRemoved.add(eventQueueArr[i]);
            }
        }
        
        //Actually remove all the completed events; again, concurrent mod avoided by using removeAll 
        eventQueueList.removeAll(eventsToBeRemoved);
        
        /*
        ArrayList<Event> eventQueueListCopy = new ArrayList<Event>();
        eventQueueList.clear();
        Event[] c = (Event[]) eventQueueListCopy.toArray();
        
        ArrayList<Event> eventsToBeRemoved = new ArrayList<Event>();
        for(int i = 0; i < c.length; i ++){
            if(!(c[i].executeEvent(timeDelta))){
                eventsToBeRemoved.add(c[i]);
            }
        }
        eventQueueListCopy.removeAll(eventsToBeRemoved);
        eventQueueListCopy.addAll(eventQueueList);
        eventQueueList = eventQueueListCopy;
                */
    }
    
    public void addEvent(Event e){
        eventQueueList.add(e);
    }
    
    public void addEvents(Event[] e){
        for(Event event : e){
            eventQueueList.add(event);
        }
    }
    
    public void addScreen(Screen s){
        eventManagerScreen = s;
    }
    
    public void removeEvent(Event e){
        eventQueueList.remove(e);
    }
    
    public Screen getEventManagerScreen(){
        return eventManagerScreen;
    }
    
    public ArrayList<Event> getEventQueueList(){
        return eventQueueList;
    }
    
    public void sortEvents(){
        //IDK, maybe I'll use this one day? Probably not, considering how important proper event order is (events that happen first need to be executed first)
    }
}
