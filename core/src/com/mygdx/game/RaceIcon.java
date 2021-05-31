package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class RaceIcon extends Icon{

    AdditionalMenu additionalMenu;

    public RaceIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        additionalMenu = new AdditionalMenu((int)(w / 1.83), (int)(h/ 1.27), (Gdx.graphics.getWidth() - (int)(w / 1.83)) / 2,  (Gdx.graphics.getHeight() - (int)(h/ 1.27)) / 2, new Texture(Gdx.files.internal("Map/raceMenu.png"))){
            @Override
            public void buttonDo(Main game){
                game.setScreen(new RaceGame(game, 500, (PlayerCar) loadPlayerCar(), (EnemyCar) loadEnemyCar("race_game/data/nissan_350z.txt"), false));
            }
        };
    }

    public void draw(Batch batch){
        super.draw(batch);
        if(additionalMenu.opened){
            additionalMenu.draw(batch);
        }
    }

    public void onClick(){
        additionalMenu.opened = true;
    }
}
