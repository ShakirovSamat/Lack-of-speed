package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UI {

    // Content
    PlayerCar.Pedal pedal;
    PlayerCar.TransmissionButton transPlus, transMinus;
    Texture speedometer;

    public UI(){
        pedal = new PlayerCar.Pedal((int)RaceGame.SCREEN_WIDTH - 150, 30, 81, 160, new Texture(Gdx.files.internal("race_game/gas_pedal_on.png")));
        transMinus = new PlayerCar.TransmissionButton(0,60,48,(int)RaceGame.SCREEN_WIDTH - 250, 50);
        transPlus = new PlayerCar.TransmissionButton(1,60,48,(int)RaceGame.SCREEN_WIDTH - 250, 110);
        speedometer = new Texture(Gdx.files.internal("race_game/cars/speed.png"));
    }

    public void draw(Batch batch, BitmapFont font, BitmapFont font2, PlayerCar playerCar){
        pedal.draw(batch);
        transMinus.draw(batch);
        transPlus.draw(batch);
        batch.draw(speedometer,0,0,150,150);
        batch.draw(speedometer,150,0,75,75);
        String str = String.valueOf((int)playerCar.curSpeed);
        font.draw(batch, str, 50 - (str.length() - 1)* 12,80);
        font2.draw(batch, playerCar.curTransmission + "", 175,37);
    }
    public void checkTouchPedal(PlayerCar playerCar){
        pedal.checkTouch(playerCar);
    }
    public void checkTouchTransmissions(float x, float y, PlayerCar playerCar){
        transPlus.checkTouch(x,y, playerCar);
        transMinus.checkTouch(x,y, playerCar);
    }

}
