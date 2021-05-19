package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

class Icon {
    public int width;
    public int height;
    public int xPosition;
    public int yPosition;
    public String name;
    public Texture texture;

    public Icon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
        this.texture = texture;
    }


    public void draw(Batch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
    }

    public void onClick(Main game) {
        float x = Gdx.input.getX();
        float y = 1280 * (RaceGame.SCREEN_HEIGHT / RaceGame.SCREEN_WIDTH) - Gdx.input.getY();
        if (xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height) {
            switch (name) {
                case "Race":
                    game.setScreen(new RaceGame(game, 400, (PlayerCar) Map.loadCar("race_game/data/main.txt", 0), (EnemyCar) Map.loadCar("race_game/data/samat.txt", 1)));
                    break;
                case "Store":
                    game.setScreen(new CardGame(game));
                    break;
                case "Garage":
                    game.setScreen(new Garage(game));
                    break;
                case "Map":
                    game.setScreen(new Map(game));
                    break;
            }
        }

    }
}