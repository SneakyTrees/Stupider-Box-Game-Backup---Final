package stupiderboxgame;

public class EnemyBox extends LivingBox{
    
    float pointVal;
    float damage;
    
    public EnemyBox(float w, float h, Coordinates center, float sped, float hp, boolean invincible, float val, float dam){
        super(w, h, center, sped, hp, invincible);
        pointVal = val;
        damage = dam;
    }
    
    public EnemyBox(){
        super();
    }
    

    
    @Override
    public void handleDeath(Screen s, EventManager em){
        //s.addEnemyBox(new EnemyBox(width, height, new Coordinates(0.25f*s.getMaxX(), 0.25f*s.getMaxY()), speed, health, false, 10, 10));
        s.removeEnemyBox(this);
    }
    
    //Fuck this is all so sloppy, I need to rework this/get a completely new system =/
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        if(pb.getClass() == (new DamagingProjectile()).getClass()){
            DamagingProjectile dp = (DamagingProjectile) pb;
            System.out.println(dp.getSourceBox());
            if(dp.getSourceBox() != this || !(dp.getSourceBox() instanceof EnemyBox) ){
                 this.damageBox(dp.getDamage(), s, em);
                 s.getPlayerBox().addPoints(pointVal);
                 s.removeProjectileBox(dp);
                 dp = null;
            }
        }
    }
    
    public float getPointVal(){
        return pointVal;
    }
}
