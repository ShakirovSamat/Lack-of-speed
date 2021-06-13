package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button extends Unit {
    Texture texture, touched_texture;
    long timeLock;
    public Button(int width, int height, int xPosition, int yPosition, Texture texture, Texture touched_texture) {
        super(width, height, xPosition, yPosition);
        this.texture = texture;
        this.touched_texture = touched_texture;
    }
    public void draw(Batch batch){
        if(timeLock <= System.currentTimeMillis()){
            batch.draw(texture,xPosition,yPosition,width,height);
        }
        else{
            batch.draw(touched_texture,xPosition,yPosition,width,height);
        }
    }

    public void onClick(Main game){

    }

    public void isClicked(float x, float y, Main game){
        if(xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height) {
            timeLock = System.currentTimeMillis() + 300;
            onClick(game);
        }
    }
}
