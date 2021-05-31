package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Menu {

    // Sizes
    int width;
    int height;
    int xPosition;
    int yPosition;

    // Data
    String[] results;

    //Graphic
    Texture background;
    static Texture button;

    //Buttons
    Button restart, ok;

    public static int w,h;

    public Menu(int width, int height, String[] results) {
        this.width = width;
        this.height = height;
        this.results = results;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        xPosition = (int)(w / 2 - width / 2);
        yPosition = (int)(h / 2 - height / 2);
        background = new Texture(Gdx.files.internal("menu_background.png"));
        button = new Texture(Gdx.files.internal("button.png"));
        restart = new Button((int)(w / 7.52),(int)(h / 14.4),xPosition + (int)(w / 18.28), yPosition + (int)(h / 18), "Restart");
        ok = new Button((int)(w / 7.52),(int)(h / 14.4), xPosition + width - (int)(w / 5.33),yPosition + (int)(h / 18), "Ok");
    }

    public void draw(Batch batch, BitmapFont font, BitmapFont font2){
        batch.draw(background,xPosition,yPosition,width,height);
        restart.draw(batch,font2);
        ok.draw(batch,font2);

        for(int i = 0 ; i < results.length; i++){
            font.draw(batch,results[i],xPosition + (int)(w / 18.28), yPosition + height + (int)(h / 36) - (i + 1) * (int)(h / 9));
        }
    }



    static class Button{
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
            if(hint.equals("Restart") && !RaceGame.isTournament){
                batch.draw(button,xPosition,yPosition,width,height);
                font.draw(batch,hint,xPosition + (int)(w / 85.3), yPosition + (int)(h / 20.57));
            }
            else if(hint.equals("Ok")){
                batch.draw(button,xPosition,yPosition,width,height);
                font.draw(batch,hint,xPosition + (int)(w / 85.3), yPosition + (int)(h / 20.57));
            }
        }

        public void isTouched(Main game, float x, float y, PlayerCar playerCar, EnemyCar enemyCar){
            if(xPosition <= x && x <= xPosition + width && yPosition <= y && y <= yPosition + height){
                switch (hint){
                    case "Restart":
                        if(!RaceGame.isTournament){
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
                            game.setScreen(new RaceGame(game, 400, playerCar, enemyCar, false));
                        }
                        break;
                    case "Ok":
                        if(RaceGame.isTournament && RaceGame.prefs.getString("status","").equals("defeat")){
                            game.setScreen(new DefeatWindow(game));
                        }
                        else{
                            game.setScreen(new Map(game));
                        }
                        break;

                }
            }
        }
        public boolean isTouched(Main game, float x, float y){
            if(xPosition <= x && x <= xPosition + width && yPosition <= y && y <= yPosition + height){
                switch (hint){
                    case "Restart":
                        game.setScreen(new CardGame(game));
                        break;
                    case "Ok":
                        game.setScreen(new Map(game));
                        break;

                }
                return true;
            }
            return false;
        }
    }
}
