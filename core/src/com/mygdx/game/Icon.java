package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

    public boolean onClick(Main game) {
        float x = Gdx.input.getX();
        float y = 1280 * (RaceGame.SCREEN_HEIGHT / RaceGame.SCREEN_WIDTH) - Gdx.input.getY();
        if (xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height) {
            switch (name) {
                case "Race":
                    game.setScreen(new RaceGame(game, 400, (PlayerCar) loadCar("race_game/data/main.txt", 0), (EnemyCar) loadCar("race_game/data/samat.txt", 1)));
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
                case "Upgrade":
                    return true;


            }
        }
        return false;
    }


    public Car loadCar(String dataPath, int type){
        FileHandle fileHandle = Gdx.files.internal(dataPath);
        String[] str = fileHandle.readString().split(" ");
        String name = str[0];
        String car = str[1];
        String bodyPath = str[2];
        String wheelPath = str[3];
        int weight = Integer.parseInt(str[4]);
        int transmissions = Integer.parseInt(str[5]);
        int[] speedChange = new int[transmissions - 1];
        for(int i = 0; i < transmissions - 1; i++){
            speedChange[i] = Integer.parseInt(str[6 + i]);
        }
        int maxSpeed = Integer.parseInt(str[5 + transmissions]);
        float[] speeds = new float[transmissions];
        for(int i = 0; i < transmissions; i++){
            speeds[i] = Float.parseFloat(str[5 + transmissions + i + 1]);
        }
        if(type == 0){
            return new PlayerCar(487,126,300,20, transmissions, speedChange, maxSpeed, speeds, weight, bodyPath, wheelPath);
        }
        else{
            return new EnemyCar(487,126,300,120, transmissions, speedChange, maxSpeed, speeds, weight, bodyPath, wheelPath);
        }
    }
}