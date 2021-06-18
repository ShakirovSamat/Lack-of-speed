package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.menu.GameMenu;
import com.mygdx.game.Icon;
import com.mygdx.game.Main;
import com.mygdx.game.factory.FactoryGame;

public class FactoryIcon extends Icon {

    AdditionalMenu additionalMenu;

    public FactoryIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        additionalMenu = new AdditionalMenu((int)(w / 1.83), (int)(h/ 1.27), (Gdx.graphics.getWidth() - (int)(w / 1.83)) / 2,  (Gdx.graphics.getHeight() - (int)(h/ 1.27)) / 2,
                new Texture(Gdx.files.internal("Map/factoryMenu.png"))){
            @Override
            public void buttonDo(Main game){
                GameMenu.music.pause();
                game.setScreen(new FactoryGame(game));
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
        Main.buttonPressed.play(1f);
        additionalMenu.opened = true;
    }
}
