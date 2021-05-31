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
    int w,h;

    public UI(){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        pedal = new PlayerCar.Pedal((int)RaceGame.SCREEN_WIDTH - (int)(w / 8.53), (int)(h / 24), (int)(w / 15.8), (int)(h / 4.5), new Texture(Gdx.files.internal("race_game/gas_pedal_on.png")));
        transMinus = new PlayerCar.TransmissionButton(0,(int)(w / 21.33),(int)(h / 15),(int)RaceGame.SCREEN_WIDTH - (int)(w / 5.12), (int)(h / 14.4));
        transPlus = new PlayerCar.TransmissionButton(1,(int)(w / 21.33),(int)(h / 15),(int)RaceGame.SCREEN_WIDTH - (int)(w / 5.12), (int)(h / 6.54));
        speedometer = new Texture(Gdx.files.internal("race_game/cars/speed.png"));
    }

    public void draw(Batch batch, BitmapFont font, BitmapFont font2, PlayerCar playerCar){
        pedal.draw(batch);
        transMinus.draw(batch);
        transPlus.draw(batch);
        batch.draw(speedometer,0,0,(int)(w / 8.53),(int)(w / 8.53));
        batch.draw(speedometer,(int)(w / 8.53),0,(int)(w / 17),(int)(w / 17));
        String str = String.valueOf((int)playerCar.curSpeed);
        font.draw(batch, str, (int)(w / 25.6) - (str.length() - 1)* (int)(w / 106.66),(int)(h / 9));
        font2.draw(batch, playerCar.curTransmission + "", (int)(w / 7.31),(int)(h / 19.45));
    }
    public void checkTouchPedal(PlayerCar playerCar){
        pedal.checkTouch(playerCar);
    }
    public void checkTouchTransmissions(float x, float y, PlayerCar playerCar){
        transPlus.checkTouch(x,y, playerCar);
        transMinus.checkTouch(x,y, playerCar);
    }

}
