package stupiderboxgame;

public class LivingBox extends EntityBox{
    float health;
    boolean isAlive = true;
    boolean isInvincible;
    
    public LivingBox(float w, float h, Coordinates center, float sped, float hp, boolean invincible){
        super(w, h, center, sped);
        health = hp;
        isInvincible = invincible;
    }
    
    public LivingBox(){
        super();
    }
    
    public boolean hasDied(){
        if(health <= 0 && !isInvincible){
            //System.out.println("Box " + this + " has been deded :(");
            return true;
        }
        return false;
    }
    
    public void handleDeath(Screen s, EventManager em){
        System.out.println("Deleting box from screen");
        s.getAllBoxesInScreenList().remove(this);
    }
    
    public void damageBox(float damage, Screen s, EventManager em){
        health -= damage;
        if(this.hasDied()){
            this.handleDeath(s, em);
        }
    }
    
    public void setHealth(float hp){
        health = hp;
    }
    
    public float getHealth(){
        return health;
    }
    
    public boolean getIsAlive(){
        return isAlive;
    }
    
    public boolean getIsInvincible(){
        return isInvincible;
    }
}
