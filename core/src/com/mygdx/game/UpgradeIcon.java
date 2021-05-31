package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class UpgradeIcon extends Icon{

    Upgrade_menu upgrade_menu;

    public UpgradeIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        upgrade_menu = new Upgrade_menu((int)(w / 1.83), (int)(h/ 1.27), (Gdx.graphics.getWidth() - (int)(w / 1.83)) / 2,  (Gdx.graphics.getHeight() - (int)(h/ 1.27)) / 2);
    }

    public void draw(Batch batch, Main game){
        draw(batch);
        if(upgrade_menu.opened){
            upgrade_menu.draw(batch, game.font_trans);
        }
    }

    public void onClick(){
        upgrade_menu.opened = !upgrade_menu.opened;
    }
}
