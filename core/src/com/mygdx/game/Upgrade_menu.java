package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

public class Upgrade_menu extends  Unit {

    Button[] buttons = new Button[4];
    Texture background, engine_button, body_button, clutch_button, transmission_button;
    boolean opened;
    int player_money;

    //Data
    Preferences prefs;

    public Upgrade_menu(int width, int height, int xPosition, int yPosition) {
        super(width, height, xPosition, yPosition);

        prefs = Gdx.app.getPreferences("data");
        player_money = prefs.getInteger("money",0);

        engine_button = new Texture(Gdx.files.internal("garage/engine_button.png"));
        body_button = new Texture(Gdx.files.internal("garage/body_button.png"));
        clutch_button = new Texture(Gdx.files.internal("garage/clutch_button.png"));
        transmission_button = new Texture(Gdx.files.internal("garage/transmission_button.png"));
        background = new Texture(Gdx.files.internal("garage/upgrade_background.png"));
        buttons[0] = new Button(276,228,xPosition + 30, yPosition + height - 20 - 228, engine_button,"Двигатель");
        buttons[1] = new Button(276,228,xPosition  + width - 30 - 276, yPosition + height - 20 - 228, transmission_button,"Трансмисия");
        buttons[2] = new Button(276,228,xPosition + 30, yPosition + 20, body_button,"Корпус");
        buttons[3] = new Button(276,228,xPosition + width - 30 - 276, yPosition + 20, clutch_button,"Сцепление");

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
        int step;
        int money;
        public Button(int width, int height, int xPosition, int yPosition, Texture texture, String text) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
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
        }

        public void draw(Batch batch, BitmapFont font){
            batch.draw(texture,xPosition,yPosition,width, height);
            font.draw(batch, String.valueOf(step) + "/10" ,xPosition + width - 90, yPosition + 30);
            if(step >= 10){
                font.draw(batch, "Max" ,xPosition + 40, yPosition + 70);
            }
            else{
                font.draw(batch, money + " rubles" ,xPosition + 40, yPosition + 70);
            }

        }
        public void isTouched(float x, float y){
            if(xPosition <= x && x <= xPosition + width
                    && yPosition <= y && y <= yPosition + height) {
                if(step < 10 && player_money >= money){
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
