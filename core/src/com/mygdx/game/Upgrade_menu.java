package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Upgrade_menu extends  Unit {

    Button[] buttons = new Button[4];
    Texture background,button;
    boolean opened;

    public Upgrade_menu(int width, int height, int xPosition, int yPosition) {
        super(width, height, xPosition, yPosition);
        button = new Texture(Gdx.files.internal("button.png"));
        background = new Texture(Gdx.files.internal("menu_background.png"));
        buttons[0] = new Button(250,210,xPosition + 30, yPosition + height - 20 - 210, button,"Двигатель");
        buttons[1] = new Button(250,210,xPosition  + width - 30 - 250, yPosition + height - 20 - 210, button,"Трансмисия");
        buttons[2] = new Button(250,210,xPosition + 30, yPosition + 20, button,"Корпус");
        buttons[3] = new Button(250,210,xPosition + width - 30 - 250, yPosition + 20, button,"Сцепление");

        opened = false;
    }

    public void draw(Batch batch, BitmapFont font){
        batch.draw(background, xPosition, yPosition, width ,height);
        for(int i = 0; i < buttons.length; i++){
            buttons[i].draw(batch,font);
        }
    }

    class Button extends Unit{
        Texture texture;
        String text;
        public Button(int width, int height, int xPosition, int yPosition, Texture texture, String text) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
            this.text = text;
        }

        public void draw(Batch batch, BitmapFont font){
            batch.draw(texture,xPosition,yPosition,width, height);
            font.draw(batch,text,xPosition + 20, yPosition + 10);
        }
        public void isTouched(float x, float y){
            if(xPosition <= x && x <= xPosition + width
                    && yPosition <= y && y <= yPosition + height) {
                onClick();
            }
        }
        public void onClick(){

        }
    }
}
