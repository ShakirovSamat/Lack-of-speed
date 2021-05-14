package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Menu {

    // Sizes
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;

    // Data
    String[] results;

    //Graphic
    Texture background, button;

    //Buttons
    Button restart, ok;

    public Menu(int width, int height, String[] results) {
        this.width = width;
        this.height = height;
        this.results = results;
        xPosition = (int)(RaceGame.SCREEN_WIDTH / 2 - width / 2);
        yPosition = (int)(RaceGame.SCREEN_HEIGHT / 2 - height / 2);
        background = new Texture(Gdx.files.internal("menu_background.png"));
        button = new Texture(Gdx.files.internal("button.png"));
        restart = new Button(170,50,xPosition + 70, yPosition + 40, "Restart");
        ok = new Button(170,50, xPosition + width - 70 - 170,yPosition + 40, "Ok");
    }

    public void draw(Batch batch, BitmapFont font, BitmapFont font2){
        batch.draw(background,xPosition,yPosition,width,height);
        restart.draw(batch,font2);
        ok.draw(batch,font2);

        for(int i = 0 ; i < results.length; i++){
            font.draw(batch,results[i],xPosition + 100, yPosition + height - (i + 1) * 100);
        }
    }

    class Button{
        private int width;
        private int height;
        private int xPosition;
        private int yPosition;
        private String hint;

        public Button(int width, int height, int xPosition, int yPosition, String hint) {
            this.width = width;
            this.height = height;
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.hint = hint;
        }

        public void draw(Batch batch, BitmapFont font){
            batch.draw(button,xPosition,yPosition,width,height);
            font.draw(batch,hint,xPosition + (int)((width / 2) + 30), yPosition + 35);
        }

        public void isTouched(Main game, float x, float y, PlayerCar playerCar, EnemyCar enemyCar){
            if(xPosition <= x && x <= xPosition + width && yPosition <= y && y <= yPosition + height){
                switch (hint){
                    case "Restart":
                        playerCar.isFinished = false;
                        playerCar.isStarted = false;
                        playerCar.cur_distance = 0;
                        playerCar.curTransmission = 1;
                        playerCar.body_rotation = 0;
                        playerCar.curSpeed = 0;
                        enemyCar.isFinished = false;
                        enemyCar.isStarted = false;
                        enemyCar.cur_distance = 0;
                        enemyCar.curTransmission = 1;
                        enemyCar.body_rotation = 0;
                        enemyCar.curSpeed = 0;
                        game.setScreen(new RaceGame(game, 400, playerCar, enemyCar));
                        break;
                     case "Ok":
                         game.setScreen(new Map(game));
                         break;

                }
            }
        }
    }
}
