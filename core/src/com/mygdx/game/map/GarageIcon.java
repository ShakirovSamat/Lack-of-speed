package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.garage.Garage;
import com.mygdx.game.Icon;
import com.mygdx.game.Main;

public class GarageIcon extends Icon {
    public GarageIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
    }
    public void onClick(Main game){
        Main.buttonPressed.play(1f);
        game.setScreen(new Garage(game));
    }
}
