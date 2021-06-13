package com.mygdx.game.raceGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;


public class Landscape {
    private Texture[] first_plan;
    private Texture middle, back, clouds;
    private float[] positions;
    private Random random = new Random();
    public boolean isFinishing, isFinished;
    int cur_first, next_first;
    int w,h;



    public Landscape(){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        positions = new float[]{0,0,0};
        cur_first = 0;
        isFinishing = false;
        isFinished = false;
        next_first = 1 + random.nextInt(7);

        first_plan = new Texture[9];
        for(int i = 0; i < 9; i++){
            first_plan[i] = new Texture(Gdx.files.internal("race_game/landscapes/front_" + (i + 1) + ".png"));
        }
        middle = new Texture(Gdx.files.internal("race_game/landscapes/middle_1.png"));
        back = new Texture(Gdx.files.internal("race_game/landscapes/back_1.png"));
        clouds = new Texture(Gdx.files.internal("race_game/landscapes/static_1.png"));

    }

    public void draw(Batch batch, com.mygdx.game.raceGame.PlayerCar playerCar){

        batch.draw(clouds,0,0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);
        if(playerCar.curSpeed <= 140){
            positions[0] -= (int)(w / (1280 / (playerCar.curSpeed / 3)));
            positions[1] -= (int)(w / (1280 / (playerCar.curSpeed / 9)));
            positions[2] -= (int)(w / (1280 / (playerCar.curSpeed / 30)));
        }
        else{
            positions[0] -= (int)(w / (1280 / (140 / 3)));
            positions[1] -= (int)(w / (1280 / (140 / 9)));
            positions[2] -= (int)(w / (1280 / (140 / 30)));
        }
        for(int i = 0; i < 3; i++){
            if(positions[i] <= -com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH){
                positions[i] = 0;
                if(i == 0 && isFinishing && !playerCar.isFinished && !isFinished){
                    isFinished = true;
                    cur_first = next_first;
                    next_first = 8;
                }
                else if(i == 0 && next_first == 8){
                    cur_first = next_first;
                    next_first = 1 + random.nextInt(7);
                    playerCar.isFinished = true;
                }
                else if(i == 0){
                    cur_first = next_first;
                    next_first = 1 + random.nextInt(7);
                }

            }
        }
        batch.draw(back,positions[2],0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);
        batch.draw(back,positions[2] + com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH,0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);

        batch.draw(middle, positions[1], 0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);
        batch.draw(middle,positions[1] + com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH,0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);

        batch.draw(first_plan[cur_first],positions[0],0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, com.mygdx.game.raceGame.RaceGame.SCREEN_HEIGHT);
        batch.draw(first_plan[next_first],positions[0] + com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH,0, com.mygdx.game.raceGame.RaceGame.SCREEN_WIDTH, RaceGame.SCREEN_HEIGHT);

    }
    public void finish(){
        isFinishing = true;
    }
}
