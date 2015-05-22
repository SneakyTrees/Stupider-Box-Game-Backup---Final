package stupiderboxgame;

public class DamagingProjectile extends ProjectileBox{
    float damage;
    EntityBox sourceBox;
    
    public DamagingProjectile(float w, float h, Coordinates center, float sped, float dam, EntityBox sb){
        super(w, h, center, sped);
        damage = dam;
        sourceBox = sb;
    }
    
    public DamagingProjectile(){
        super();
    }
    
    @Override
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        //if(this.getSourceBox() instanceof PlayerBox){
        //    s.removeProjectileBox(this);
        //}
    }
    
    public float getDamage(){
        return damage;
    }
    
    public EntityBox getSourceBox(){
        return sourceBox;
    }
}
