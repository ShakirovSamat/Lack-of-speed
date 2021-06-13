package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.raceGame.EnemyCar;
import com.mygdx.game.raceGame.PlayerCar;

public class Icon extends Unit{

    public String name;
    public Texture texture;
    public static int w,h;

    public Icon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition);
        this.name = name;
        this.texture = texture;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }


    public void draw(Batch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
    }


    public boolean isClicked(float x, float y){
        return xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height;
    }

}
