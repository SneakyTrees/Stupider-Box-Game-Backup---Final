package stupiderboxgame;

public class ProjectileBox extends EntityBox{
    public ProjectileBox(float w, float h, Coordinates center, float sped){
        super(w, h, center, sped);
    }
    
    public ProjectileBox(){
        super();
    }
    
    public void handleCollisionWithScreenBorder(Screen s){
        s.removeProjectileBox(this);
    }
}
