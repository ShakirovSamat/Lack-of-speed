package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TournamentIcon extends  Icon{
    AdditionalMenu additionalMenu;
    Preferences pref;
    int step;

    public TournamentIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        pref = Gdx.app.getPreferences("data");
        step = pref.getInteger("tournamentStep", 0);

        additionalMenu = new AdditionalMenu(696, 564, (int) (Gdx.graphics.getWidth() - 696) / 2, (int) (Gdx.graphics.getHeight() - 564) / 2, new Texture(Gdx.files.internal("Map/tournamentMenu.png"))){
            @Override
            public void buttonDo(Main game){
                String enemyCarPath = "race_game/data/";
                switch (step){
                    case 0:
                        enemyCarPath += "vaz.txt";
                        break;
                    case 1:
                        enemyCarPath += "toyota_AE86.txt";
                        break;
                    case 2:
                        enemyCarPath += "bmv_m3.txt";
                        break;
                    case 3:
                        enemyCarPath += "nissan_350z.txt";
                        break;

                }
                game.setScreen(new RaceGame(game, 500, (PlayerCar) loadPlayerCar(), (EnemyCar) loadEnemyCar(enemyCarPath), true));
            }
        };
    }
    public void draw(Batch batch, BitmapFont font){
        super.draw(batch);
        if(additionalMenu.opened){
            additionalMenu.draw(batch);
            if(step < 4){
                font.draw(batch, "Выиграно " + step + "/4 гонщиков",additionalMenu.xPosition + 20, additionalMenu.yPosition + 60);
            }
            else{
                font.draw(batch, "Вы абсолютный чемпион",additionalMenu.xPosition + 20, additionalMenu.yPosition + 60);
            }
        }
    }
    public void onClick(){
        additionalMenu.opened = true;
    }
}
