package stupiderboxgame;

import java.util.ArrayList;

public class Screen {
    float minX;
    float maxX;
    float minY;
    float maxY;
    
    ArrayList<PerfectBox> allBoxesInScreen = new ArrayList<PerfectBox>();
    PlayerBox playerBox;
    ArrayList<EnemyBox> enemyBoxesInScreen = new ArrayList<EnemyBox>();
    ArrayList<ProjectileBox> projectileBoxesInScreen = new ArrayList<ProjectileBox>();
    ArrayList<ShootingEnemy> shootingEnemiesInScreen = new ArrayList<ShootingEnemy>();
    ArrayList<TrackingBox> trackingBoxesInScreen = new ArrayList<TrackingBox>();
    
    /*
    ArrayList<TerrainBox> terrainInScreen = new ArrayList<TerrainBox>();
    */
    
    public Screen(float minx, float maxx, float miny, float maxy){
        minX = minx;
        maxX = maxx;
        minY = miny;
        maxY = maxy;
    }
    
    public boolean anyCoordinatesWouldBeOutsideBorder(Coordinates[] coords){
        for(Coordinates c : coords){
            if(maxX < c.getX() || c.getX() < minX || maxY < c.getY() || c.getY() < minY){
                return true;
            }
        }
        return false;
    }
    
    public boolean pointIsInsideScreen(Coordinates testCoord){
        if(
            (minX < testCoord.getX() && testCoord.getX() < maxX) &&
            (minY < testCoord.getY() && testCoord.getY() < maxY)
        ){
            return true;
        }
        return false;
    }
    
    public void addBoxToScreen(PerfectBox pb){
        allBoxesInScreen.add(pb);
    }
    
    public void addPlayerBox(PlayerBox pb){
        playerBox = pb;
        allBoxesInScreen.add(playerBox);
    }
    
    public void addEnemyBox(EnemyBox eb){
        enemyBoxesInScreen.add(eb);
        allBoxesInScreen.add(eb);
    }
    
    public void addEnemyBoxes(ArrayList<EnemyBox> eb){
        for(EnemyBox e : eb){
            enemyBoxesInScreen.add(e);
            allBoxesInScreen.add(e);
        }
    }
    
    public void addProjectileBox(ProjectileBox pb){
        projectileBoxesInScreen.add(pb);
        allBoxesInScreen.add(pb);
    }
    
    public void addShootingEnemy(ShootingEnemy se){
        shootingEnemiesInScreen.add(se);
        allBoxesInScreen.add(se);
    }
    
    public void addTrackingBox(TrackingBox tb){
        trackingBoxesInScreen.remove(tb);
        allBoxesInScreen.remove(tb);
    }
    
    public void removePlayerBox(){
        allBoxesInScreen.remove(playerBox);
        playerBox = null;
    }
    
    public void removeEnemyBox(EnemyBox eb){
        enemyBoxesInScreen.remove(eb);
        allBoxesInScreen.remove(eb);
    }
    
    public void removeProjectileBox(ProjectileBox pb){
        projectileBoxesInScreen.remove(pb);
        allBoxesInScreen.remove(pb);
    }
    
    public void removeShootingEnemy(ShootingEnemy sb){
        allBoxesInScreen.remove(sb);
        shootingEnemiesInScreen.remove(sb);
    }
    
    public Coordinates[] findPointsInsideScreen(Coordinates[] testCoords){
        ArrayList<Coordinates> pointsInsideList = new ArrayList<Coordinates>();
        for(Coordinates testCoord : testCoords){
            if(
                (minX < testCoord.getX() && testCoord.getX() < maxX) &&
                (minY < testCoord.getY() && testCoord.getY() < maxY)
            ){
                pointsInsideList.add(testCoord);
            }
        }
        //System.out.println(pointsInsideList.size());
        Coordinates[] pointsInsideArr = new Coordinates[pointsInsideList.size()];
        for(int i = 0; i < pointsInsideArr.length; i++){
            pointsInsideArr[i] = pointsInsideList.get(i);
        }
        return pointsInsideArr;
    }
    
    public void setPlayerBox(PlayerBox pb){
        playerBox = pb;
    }
    
    public void setEnemyBoxes(ArrayList<EnemyBox> eb){
        enemyBoxesInScreen = eb;
    }
    
    public float getMinX(){
        return minX;
    }
    
    public float getMaxX(){
        return maxX;
    }
    
    public float getMinY(){
        return minY;
    }
    
    public float getMaxY(){
        return maxY;
    }
    
    public ArrayList<PerfectBox> getAllBoxesInScreenList(){
        return allBoxesInScreen;
    }
    
    public PerfectBox getAllBoxesInScreenAt(int index){
        return allBoxesInScreen.get(index);
    }
    
    public PlayerBox getPlayerBox(){
        return playerBox;
    }
    
    public EnemyBox getEnemyBoxAt(int index){
        return enemyBoxesInScreen.get(index);
    }
    
    public ShootingEnemy getShootingEnemyAt(int index){
        return shootingEnemiesInScreen.get(index);
    }
}
