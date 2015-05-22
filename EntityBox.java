package stupiderboxgame;

import java.util.*;

public class EntityBox extends PerfectBox {
    /*
    Well, I really didn't want to take this approach, but here we are, with a screen variable for all EntityBoxes :(
    
    In the future when I'm hopefully less dumb and lazy, I can skip this entirely, and have all interactions between the boxes and the screen take
    place through events, which have knowledge, by default, of a a screen that they are generated for. Also introduce triggers, which are like events
    but are registered to GamePanel so that, on creation and calling, they trigger an immediate event. Or something, IDK. The challenge of doing all of shit
    and figuring it out for yourself sucks sometimes, but it's really been informative and rewarding, much more so than just looking up how to structure
    games online, I think.
    
    here's what it might look like I guess
        3 event types
            ->Triggers
                -generate objects that are registered to listeners in gamePanel; used for communicating outside the event/game code framework,
                directly to the GUI to let it know to do something (ex. player dies and the game needs to stop with a GUI prompt generated)
            ->Events
                -execute a given behavior until a conditional is met (ex. special enemyBox will track towards playerBox until it collides with it)
            ->TimerEvents 
                -same as events, but also include a check for if their time of running has been completed
    */
    
    float speed;

    public EntityBox(float w, float h, Coordinates center, float sped) {
        super(w, h, center);
        speed = sped;
    }
    
    public EntityBox(){
        super();
    }
    
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        System.out.println("handlecoll called in entbox");
    }
    
    public void handleCollisionWithScreenBorder(float xDist, float yDist, Screen s) {
        //Do I actually need these? Fuck it im lazy and tired well do it anyway
        int xDirectionalMult;
        int yDirectionalMult;
        
        float remainingXDist = 0;
        if(Math.abs(xDist-0) < UNIVERSAL_EPSILON_VAL){
            xDirectionalMult = 1;
            remainingXDist = 0;
        }
        else if(xDist == Math.abs(xDist)){
            //If the box's moving by this xDirection would not violate things, just move it by that x dist
            if(this.findGreatestX()+xDist < s.getMaxX()){
                remainingXDist = xDist;
                xDirectionalMult = 1;
            }
            else if(Math.abs(this.findGreatestX()-s.getMaxX()) < UNIVERSAL_EPSILON_VAL){
                remainingXDist = 0;
                xDirectionalMult = 1;
            }
            else{
                remainingXDist = s.getMaxX()-this.findGreatestX();
                xDirectionalMult = 1;
            }
        }
        else if(xDist != Math.abs(xDist)){
            if(this.findLeastX()+xDist > s.getMinX()){
                remainingXDist = xDist;
                xDirectionalMult = 1;
            }
            //You're already at the border and trying to move even more negatively, so no distance is to be moved
            else if(Math.abs(this.findLeastX()-s.getMinX()) < UNIVERSAL_EPSILON_VAL){
                remainingXDist = 0;
                xDirectionalMult = 1;
            }
            //Move the box that remaining negative distance to just be on the border edge of the screen
            else{
                remainingXDist = this.findLeastX()-s.getMinX();
                xDirectionalMult = -1;
            }
        }
        else{
            return;
        }
        
        float remainingYDist = 0;
        if(Math.abs(yDist-0) < UNIVERSAL_EPSILON_VAL){
            remainingYDist = 0;
            yDirectionalMult = 1;
        }
        else if(yDist == Math.abs(yDist)){
            if(this.findGreatestY()+yDist < s.getMaxY()){
                remainingYDist = yDist;
                yDirectionalMult = 1;
            }
            else if(Math.abs(this.findGreatestY()-s.getMaxY()) < UNIVERSAL_EPSILON_VAL){
                remainingYDist = 0;
                yDirectionalMult = 1;
            }
            else{
                remainingYDist = s.getMaxY()-this.findGreatestY();
                yDirectionalMult = 1;
            }
        }
        else if(yDist != Math.abs(yDist)){
            if(this.findLeastY()+yDist > s.getMinY()){
                remainingYDist = yDist;
                yDirectionalMult = 1;
            }
            else if(Math.abs(this.findLeastY()-s.getMinY()) < UNIVERSAL_EPSILON_VAL){
                remainingYDist = 0;
                yDirectionalMult = 1;
            }
            else{
                remainingYDist = this.findLeastY()-s.getMinY();
                yDirectionalMult = -1;
            }
        }
        else{
            return;
        }
        this.translateBox(remainingXDist*xDirectionalMult, remainingYDist*yDirectionalMult);
    }

    public void translateBox(float xDist, float yDist, Screen subjectScreen, EventManager em) {
        Coordinates[] trialCoords = {new Coordinates(SW.getX() + xDist, SW.getY() + yDist), new Coordinates(NW.getX() + xDist, NW.getY() + yDist), new Coordinates(SE.getX() + xDist, SE.getY() + yDist), new Coordinates(NE.getX() + xDist, NE.getY() + yDist)};
        if (subjectScreen.findPointsInsideScreen(trialCoords).length != 4) {
            this.handleCollisionWithScreenBorder(xDist, yDist, subjectScreen);
            return;
        }
        
        this.translateBox(xDist, yDist);

        ArrayList<PerfectBox> allBoxesInScreenListCopy = (ArrayList<PerfectBox>) subjectScreen.getAllBoxesInScreenList().clone();
        for(PerfectBox eb : allBoxesInScreenListCopy){
            if(eb == null){
                continue;
            }
            //Later change to remove this, iterate, re add this
            if(this.equals(eb)){
                continue;
            }
            if(this.hasCollidedWith(eb)){
                //System.out.println("has collided");
                this.handleCollisionWithBox(eb, subjectScreen, em);
                eb.handleCollisionWithBox(this, subjectScreen, em);
            }
        }
    }
    
    public void translateBoxAtAngle(float angle, float dist, Screen s, EventManager em){
        //Convert angle/dist combo into a stock x/y translation and then translate
        float xDist = (float) Math.cos(Math.toRadians(angle))*dist;
        float yDist = (float) Math.sin(Math.toRadians(angle))*dist;
        this.translateBox(xDist, yDist, s, em);
        
        ArrayList<PerfectBox> allBoxesInScreenListCopy = (ArrayList<PerfectBox>) s.getAllBoxesInScreenList().clone();
        for(PerfectBox eb : allBoxesInScreenListCopy){
            if(eb == null){
                continue;
            }
            //Later change to remove this, iterate, re add this
            if(this.equals(eb)){
                continue;
            }
            if(this.hasCollidedWith(eb)){
                this.handleCollisionWithBox(eb);
                eb.handleCollisionWithBox(this);
            }
        }
    }

    public void setSpeed(float sped) {
        speed = sped;
    }

    public float getSpeed() {
        return speed;
    }
}
