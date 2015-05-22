package stupiderboxgame;

class ShootAtPlayerBoxTimerEvent extends TimerEvent{

    ShootingEnemy shootingEnemy;
    Screen shootingScreen;
    EventManager shootingManager;
    
    public ShootAtPlayerBoxTimerEvent(boolean neverending, double duration, Screen s, EventManager em, ShootingEnemy shoten){
        super(neverending, duration);
        shootingScreen = s;
        shootingManager = em;
        shootingEnemy = shoten;
    }

    @Override
    public boolean executeEvent(double timeDelta){
        //Is this sloppy and poorly desiged? Absolutely! On the other hand, this thing is due tomorrow so fuck you
        
        if(shootingScreen.getPlayerBox() == null){
            return false;
        }
        
        if(!shootingScreen.getAllBoxesInScreenList().contains(shootingEnemy)){
            return false;
        }
        remainingTime -= timeDelta;
        if(remainingTime <= 0){
            if(isNeverEnding){
                //Do all yer shananigans and what not; ex. remainingTime = timeDuration
                this.executeEndingProcess();
                remainingTime = eventDuration;
                return true;
            }
            double remainingDelta = remainingTime-Math.abs(remainingTime);

            //Execute the normal code, but with remainingDelta
            this.executeTimerEvent(remainingDelta);
            this.executeEndingProcess();
            
            return false;
        }

        //Execute normal code
        this.executeTimerEvent(timeDelta);
        return true;
    }
    
    @Override
    public void executeTimerEvent(double timeDelta){
        //later just redo executeEVent to return false if playerbo ix null
    }
    
    @Override
    public void executeEndingProcess(){
        System.out.println("ending execute");
        shootingEnemy.fireProjectileAtPlayer(shootingScreen, shootingManager);
    }
}
