/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stupiderboxgame;

/**
 *
 * @author Duncan
 */
public class ShootingEnemy extends EnemyBox{
    
    public ShootingEnemy(float w, float h, Coordinates center, float sped, float hp, boolean invincible, float val, float dam){
        super(w, h, center, sped, hp, invincible, val, dam);
        //System.out.println("shoot dam: " + dam);
    }
    
    public ShootingEnemy(){
        super();
    }
    
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        if(pb.getClass() == (new DamagingProjectile()).getClass()){
            DamagingProjectile dp = (DamagingProjectile) pb;
            
            if(dp.getSourceBox() != this || (!(dp.getSourceBox() instanceof ShootingEnemy)) ){
                 //this.damageBox(dp.getDamage(), s, em);
                 //s.getPlayerBox().addPoints(pointVal);
                 s.removeProjectileBox(dp);
                 dp = null;
                 //s.removeEnemyBox(this);
            }
        }
    }
    
    public void fireProjectileAtPlayer(Screen s, EventManager em){
        //System.out.println(damage);
        DamagingProjectile firedProjectile = new DamagingProjectile(15, 15, centerPoint, 175, damage, this);
        s.addProjectileBox(firedProjectile);
        em.addEvent(new TranslateBoxAtAngleEvent(false, s, em, firedProjectile,  centerPoint.findAngleToPoint(s.getPlayerBox().getCenterPoint()), true));
    }
}
