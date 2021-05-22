package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

    //Data
    Preferences prefs;


    public Icon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
        this.texture = texture;

        prefs = Gdx.app.getPreferences("data");
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
                    game.setScreen(new RaceGame(game, 400, (PlayerCar) loadPlayerCar(), (EnemyCar) loadEnemyCar("race_game/data/samat.txt")));
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


    public EnemyCar loadEnemyCar(String dataPath){
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


        return new EnemyCar(487,126,300,120, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath);

    }
    public PlayerCar loadPlayerCar(){
        int weight = 1000 - prefs.getInteger("Корпус", 0 ) * 15;
        int steps = prefs.getInteger("Двигатель", 0) + prefs.getInteger("Трансмисия") + prefs.getInteger("Сцепление");
        int maxSpeed = 120 + steps * 3;
        int[] speed_change = new int[]{30 + (int)(steps / 1.5), 50 + steps, 80 + (int)(steps * 1.4), 100 + (int)(steps * 2.2)};
        String bodyPath = "race_game/cars/toyota_supra.png";
        String wheelPath = "race_game/cars/wheel.png";
        return new PlayerCar(487,126,300,40, 5, speed_change, maxSpeed, weight, bodyPath, wheelPath);
    }
}
