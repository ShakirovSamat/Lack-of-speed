package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

public class Upgrade_menu extends  Unit {

    Button[] buttons = new Button[4];
    Texture background, engine_button, body_button, clutch_button, transmission_button, level_up_button;
    boolean opened;
    int player_money;
    int w,h;

    //Data
    Preferences prefs;

    public Upgrade_menu(int width, int height, int xPosition, int yPosition) {
        super(width, height, xPosition, yPosition);

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        prefs = Gdx.app.getPreferences("data");
        player_money = prefs.getInteger("money",0);;

        engine_button = new Texture(Gdx.files.internal("garage/engine_button.png"));
        body_button = new Texture(Gdx.files.internal("garage/body_button.png"));
        clutch_button = new Texture(Gdx.files.internal("garage/clutch_button.png"));
        transmission_button = new Texture(Gdx.files.internal("garage/transmission_button.png"));
        level_up_button = new Texture(Gdx.files.internal("garage/levelUpButton.png"));
        background = new Texture(Gdx.files.internal("garage/upgrade_background.png"));
        buttons[0] = new Button((int)(w / 4.63),(int)(h / 3.15),xPosition + (int)(w / 42.6), yPosition + height - (int)(h / 2.9), engine_button, level_up_button,"Двигатель");
        buttons[1] = new Button((int)(w / 4.63),(int)(h / 3.15),xPosition  + width - (int)(w / 4.18), yPosition + height - (int)(h / 2.9), transmission_button, level_up_button,"Трансмисия");
        buttons[2] = new Button((int)(w / 4.63),(int)(h / 3.15),xPosition + (int)(w / 42.6), yPosition + (int)(h / 36), body_button, level_up_button,"Корпус");
        buttons[3] = new Button((int)(w / 4.63),(int)(h / 3.15),xPosition + width - (int)(w / 4.18), yPosition + (int)(h / 36), clutch_button, level_up_button,"Сцепление");

        opened = false;
    }

    public void draw(Batch batch, BitmapFont font){
        batch.draw(background, xPosition, yPosition, width ,height);
        for(int i = 0; i < buttons.length; i++){
            buttons[i].draw(batch,font);
        }
    }

    class Button extends Unit{
        Texture texture, touched;
        String text;
        int step;
        int money;
        long timeLock;

        public Button(int width, int height, int xPosition, int yPosition, Texture texture, Texture touched, String text) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
            this.touched = touched;
            this.text = text;
            step = prefs.getInteger(text,0);
            switch (text){
                case "Двигатель":
                    money = 2000;
                    break;
                case "Трансмисия":
                    money = 1800;
                    break;
                case "Корпус":
                    money = 1400;
                    break;
                case "Сцепление":
                    money = 1600;
                    break;
            }
            money = (int)(money * Math.pow(1.4,step));
            timeLock = 0;
        }

        public void draw(Batch batch, BitmapFont font){
            if(timeLock <= System.currentTimeMillis()){
                batch.draw(texture,xPosition,yPosition,width, height);
                font.draw(batch, String.valueOf(step) + "/10" ,xPosition + width - (int)(w / 14.2), yPosition + (int)(h / 24));

                if(step >= 10){
                    font.draw(batch, "Max" ,xPosition + (int)(w / 32), yPosition + (int)(h / 10.28));
                }
                else{
                    font.draw(batch, money + " rubles" ,xPosition + (int)(w / 32), yPosition + (int)(h / 10.28));
                }
            }
            else{
                batch.draw(touched,xPosition,yPosition,width, height);
            }

        }
        public void isTouched(float x, float y){

            if( timeLock <= System.currentTimeMillis() && xPosition <= x && x <= xPosition + width
                    && yPosition <= y && y <= yPosition + height) {
                if(step < 10 && player_money >= money){
                    timeLock = System.currentTimeMillis() + 500;
                    player_money -= money;
                    money *= 1.4;
                    step++;
                    prefs.putInteger("money", player_money);
                    prefs.putInteger(text, prefs.getInteger(text) + 1);
                    prefs.flush();
                }
            }
        }

    }
}
