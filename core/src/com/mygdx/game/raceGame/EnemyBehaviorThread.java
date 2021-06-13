package com.mygdx.game.raceGame;


import com.badlogic.gdx.Gdx;

public class EnemyBehaviorThread implements Runnable {
    Thread thread;
    public com.mygdx.game.raceGame.EnemyCar enemyCar;
    float time;
    @Override
    public void run() {
            enemyCar.go();
            enemyCar.rest();
            enemyCar.changeDistance();
            if(!enemyCar.isFinished && enemyCar.cur_distance >= RaceGame.distance + 27){
                enemyCar.isFinished = true;
            }
            if(!enemyCar.isFinished && enemyCar.isStarted){
                time += Gdx.graphics.getDeltaTime();
            }
    }

    public void start(com.mygdx.game.raceGame.EnemyCar enemyCar){
        this.enemyCar = enemyCar;
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    public EnemyCar getCar(){
       return enemyCar;
    }
}
