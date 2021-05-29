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


    public boolean isClicked(float x, float y){
        return xPosition <= x && x <= xPosition + width
                && yPosition <= y && y <= yPosition + height;
    }


    public static EnemyCar loadEnemyCar(String dataPath){
        FileHandle fileHandle = Gdx.files.internal(dataPath);
        String[] str = fileHandle.readString().split(" ");
        int width = Integer.parseInt(str[0]);
        int height = Integer.parseInt(str[1]);
        int xPosition = Integer.parseInt(str[2]);
        int yPosition = Integer.parseInt(str[3]);
        String bodyPath = str[4];
        String wheelPath = str[5];
        int weight = Integer.parseInt(str[6]);
        int transmissions = Integer.parseInt(str[7]);
        int[] speedChange = new int[transmissions - 1];
        for(int i = 0; i < transmissions - 1; i++){
            speedChange[i] = Integer.parseInt(str[8 + i]);
        }
        int maxSpeed = Integer.parseInt(str[12]);
        int[] wheelXPosition = new int[2];
        for(int i = 0; i < 2; i++){
            wheelXPosition[i] = Integer.parseInt(str[13 + i]);
        }
        int[] wheelYPosition = new int[2];
        for(int i = 0; i < 2; i++){
            wheelYPosition[i] = Integer.parseInt(str[15 + i]);
        }
        float scale = Float.parseFloat(str[17]);
        int type = Integer.parseInt(str[18]);



        return new EnemyCar(width,height,xPosition,yPosition, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath, wheelXPosition,wheelYPosition, scale,type);

    }
    public static PlayerCar loadPlayerCar(){
        Preferences prefs = Gdx.app.getPreferences("data");
        int weight = 1000 - prefs.getInteger("Корпус", 0 ) * 15;
        int steps = prefs.getInteger("Двигатель", 0) + prefs.getInteger("Трансмисия") + prefs.getInteger("Сцепление");
        int maxSpeed = 120 + steps * 3;
        int[] speed_change = new int[]{30 + (int)(steps / 1.5), 50 + steps, 80 + (int)(steps * 1.4), 100 + (int)(steps * 2.2)};
        String bodyPath = "race_game/cars/toyota_supra.png";
        String wheelPath = "race_game/cars/wheel.png";
        return new PlayerCar(494,141,300,20, 5, speed_change, maxSpeed, weight, bodyPath, wheelPath);
    }
}
