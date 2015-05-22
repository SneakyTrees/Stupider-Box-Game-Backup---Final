package stupiderboxgame;

public class PlayerBox extends LivingBox{
    
    float playerPoints = 0;
    int trackingBoxesEliminated = 0;
    boolean isAlive;
    
    public PlayerBox(float w, float h, Coordinates center, float sped, float hp, boolean invincible){
        super(w, h, center, sped, hp, invincible);
    }
    
    public void fireProjectile(Screen s, EventManager em){
        DamagingProjectile firedProjectile = new DamagingProjectile(10, 10, new Coordinates(centerPoint.getX(), this.getCenterPoint().getY()+0.5f*height), 15, 50, this);
        s.addProjectileBox(firedProjectile);
        em.addEvent(new TranslateBoxTimerEvent(false, 1, s, em, firedProjectile, 0, 300, true));
    }
    
    public void fireProjectileFromCenter(Coordinates targetPoint, Screen s, EventManager em){
        //Later the firedProjectile will be depending on the weapon the playerBox has currently equipped
        //Also have these fire on the mouse rleased button, rather than proper right click and things
        DamagingProjectile firedProjectile = new DamagingProjectile(7.5f, 7.5f, new Coordinates(centerPoint.getX(), centerPoint.getY()), 250, 10, this);
        s.addProjectileBox(firedProjectile);
        //System.out.println(centerPoint.findAngleToPoint(targetPoint));
        em.addEvent(new TranslateBoxAtAngleEvent(false, s, em, firedProjectile, centerPoint.findAngleToPoint(targetPoint), true));
    }
    
    /*
    Could do this with a shitton of overriden methods, each with different type than PerfectBox, but I'm lazy and this is clearne rlooking
    -If this was C++, I probably wouldnt do that, couldn't get away with the anonymous, undeletable declarations of (new x).getClass(),
    without maybe declaring a bunch of objects and deleting them all at the end of the method, IDK, maybe I'm an idiot
    */
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        
        //Check for the major subtypes of EntityBox (ex. Projectile, LivingBox, etc.) first
            //System.out.println("handleColl in playerbox");
            if(pb.getClass() == (new DamagingProjectile()).getClass()){
                DamagingProjectile dp = (DamagingProjectile) pb;
                System.out.println(dp.getDamage());
                //Later will be if(dp.getSourceBox() != this || any "friendly objects"
                //this.translateBox(20, 20);
                if(dp.getSourceBox() != this){
                    //this.damageBox(dp.getDamage(), s, em);
                    s.removePlayerBox();
                    s.removeProjectileBox(dp);
                    isAlive = false;
                }
                else{
                    //System.out.println("inside playerbox; colliding projectile is from playerbox");
                }
        }
        else if(pb.getClass() == (new ShootingEnemy()).getClass()){
            s.removePlayerBox();
        }
        //Then check for just plain PerfectBox type, whatever that can be used for (ex. maybe eventually some sort of terrain object?)
    }
    
    public void addPoints(float points){
        playerPoints += points;
    }
    
    public float getPlayerPoints(){
        return playerPoints;
    }
}
