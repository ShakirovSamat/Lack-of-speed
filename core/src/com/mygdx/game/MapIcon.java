package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class MapIcon extends Icon{
    public MapIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
    }

    public void onClick(Main game){
        game.setScreen(new Map(game));
    }
}
