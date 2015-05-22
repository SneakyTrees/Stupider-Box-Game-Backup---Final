package stupiderboxgame;

public class PlayerProjectile extends DamagingProjectile{
    public PlayerProjectile(float w, float h, Coordinates center, float sped, float dam, EntityBox eb){
        super(w, h, center, sped, dam, eb);
    }
    
    @Override
    public void handleCollisionWithBox(PerfectBox pb){
        if(pb.getClass() == (new EnemyBox(0, 0 , new Coordinates(0, 0), 0, 0, false, 0, 0)).getClass()){
            
        }
        else{
            
        }
    }
}
