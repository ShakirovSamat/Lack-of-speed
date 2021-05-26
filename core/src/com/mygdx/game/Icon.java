package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Icon extends Unit{

    public String name;
    public Texture texture;

    public Icon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition);
        this.name = name;
        this.texture = texture;
    }


    public void draw(Batch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
    }


    public boolean isClicked(int x, int y){
        return xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height;
    }


    public static EnemyCar loadEnemyCar(String dataPath){
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


        return new EnemyCar(487,126,300,120, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath, new int[]{0,0},new int[]{0,0});

    }
    public static PlayerCar loadPlayerCar(){
        Preferences prefs = Gdx.app.getPreferences("data");
        int weight = 1000 - prefs.getInteger("Корпус", 0 ) * 15;
        int steps = prefs.getInteger("Двигатель", 0) + prefs.getInteger("Трансмисия") + prefs.getInteger("Сцепление");
        int maxSpeed = 120 + steps * 3;
        int[] speed_change = new int[]{30 + (int)(steps / 1.5), 50 + steps, 80 + (int)(steps * 1.4), 100 + (int)(steps * 2.2)};
        String bodyPath = "race_game/cars/toyota_supra.png";
        String wheelPath = "race_game/cars/wheel.png";
        return new PlayerCar(487,126,300,25, 5, speed_change, maxSpeed, weight, bodyPath, wheelPath);
    }
}
