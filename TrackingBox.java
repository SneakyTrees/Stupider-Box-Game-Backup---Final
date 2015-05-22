package stupiderboxgame;

import java.util.Random;

public class TrackingBox extends EnemyBox{
    public TrackingBox(float w, float h, Coordinates center, float sped, float hp, boolean invincible, float val, float dam){
        super(w, h, center, sped, hp, invincible, val, dam);
    }
    
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        if(pb instanceof PlayerBox){
            //Hit player box, remove it cuz its deaded :(
            s.removePlayerBox();
        }
        else if(pb instanceof DamagingProjectile){
                DamagingProjectile dp = (DamagingProjectile) pb;
                if(!(dp.getSourceBox() instanceof ShootingEnemy)){
                    this.damageBox(dp.getDamage(), s, em);
                    s.removeProjectileBox(dp);
                }
        }
    }
    
    @Override
    public void damageBox(float damage, Screen s, EventManager em){
        health -= damage;
        if(health <= 0){
            s.getPlayerBox().addPoints(pointVal);
            s.getPlayerBox().trackingBoxesEliminated++;
            Random rand = new Random();

            int spawnSide = rand.nextInt((3 - 0) + 1) + 0;

            //Later just change these to the diff of maxX-minX; too tired to do this now
            Coordinates spawnPoint = new Coordinates(500, 500);
            if(spawnSide == 0){
                spawnPoint = new Coordinates(0.1f*s.getMaxX(), 0.1f*s.getMaxY());    
            }
            else if(spawnSide == 1){
                spawnPoint = new Coordinates(0.1f*s.getMaxX(), 0.9f*s.getMaxY());
            }
            else if(spawnSide == 2){
                spawnPoint = new Coordinates(0.9f*s.getMaxX(), 0.9f*s.getMaxY());
            }
            else if(spawnSide == 3){
                spawnPoint = new Coordinates(0.9f*s.getMaxX(), 0.1f*s.getMaxY());
            }
            else{

            }
            TrackingBox newBox = new TrackingBox(width, height,  spawnPoint, speed, 30, isInvincible, pointVal, this.damage);
            s.removeEnemyBox(this);
            s.addEnemyBox(newBox);
            em.addEvent(new TrackBoxTowardsPlayerBoxEvent(false, newBox, s, em));
            
        }
    }
    
    public void trackTowardsPlayerBox(float dist, Screen s, EventManager em){
        this.translateBoxAtAngle(centerPoint.findAngleToPoint(s.getPlayerBox().getCenterPoint()), dist, s, em);
    }
}
