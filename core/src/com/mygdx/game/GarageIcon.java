package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GarageIcon extends Icon{
    public GarageIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
    }
    public void onClick(Main game){
        game.setScreen(new Garage(game));
    }
}
