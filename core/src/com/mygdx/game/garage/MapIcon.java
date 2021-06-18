package com.mygdx.game.garage;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Icon;
import com.mygdx.game.Main;
import com.mygdx.game.map.Map;

public class MapIcon extends Icon {
    public MapIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
    }

    public void onClick(Main game){
        Main.buttonPressed.play(1f);
        game.setScreen(new Map(game));
    }
}
