package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class RaceIcon extends Icon{

    AdditionalMenu additionalMenu;

    public RaceIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        additionalMenu = new AdditionalMenu(696, 564, (int) (Gdx.graphics.getWidth() - 696) / 2, (int) (Gdx.graphics.getHeight() - 564) / 2, new Texture(Gdx.files.internal("Map/raceMenu.png"))){
            @Override
            public void buttonDo(Main game){
                game.setScreen(new RaceGame(game, 400, (PlayerCar) loadPlayerCar(), (EnemyCar) loadEnemyCar("race_game/data/samat.txt")));
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
