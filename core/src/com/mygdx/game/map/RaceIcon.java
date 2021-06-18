package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.menu.GameMenu;
import com.mygdx.game.Icon;
import com.mygdx.game.Main;
import com.mygdx.game.raceGame.EnemyCar;
import com.mygdx.game.raceGame.PlayerCar;
import com.mygdx.game.raceGame.RaceGame;

public class RaceIcon extends Icon {

    AdditionalMenu additionalMenu;

    public RaceIcon(int width, int height, int xPosition, int yPosition, String name, Texture texture) {
        super(width, height, xPosition, yPosition, name, texture);
        additionalMenu = new AdditionalMenu((int)(w / 1.83), (int)(h/ 1.27), (Gdx.graphics.getWidth() - (int)(w / 1.83)) / 2,  (Gdx.graphics.getHeight() - (int)(h/ 1.27)) / 2, new Texture(Gdx.files.internal("Map/raceMenu.png"))){
            @Override
            public void buttonDo(Main game){
                GameMenu.music.pause();
                game.setScreen(new RaceGame(game, 500, (PlayerCar) loadPlayerCar(), (EnemyCar) loadEnemyCar("race_game/data/nissan_350z.txt"), false));
            }
        };
    }

    public void draw(Batch batch){
        super.draw(batch);
        if(additionalMenu.opened){
            additionalMenu.draw(batch);
        }
    }

    public void onClick(){
        Main.buttonPressed.play(1f);
        additionalMenu.opened = true;
    }

    public static com.mygdx.game.raceGame.EnemyCar loadEnemyCar(String dataPath){
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
            wheelXPosition[i] = (int)(w / ( 1280f / Float.parseFloat(str[13 + i])));
        }
        int[] wheelYPosition = new int[2];
        for(int i = 0; i < 2; i++){
            wheelYPosition[i] = (int)(h / (720f / Float.parseFloat(str[15 + i])));
        }
        float size = Float.parseFloat(str[17]);
        int type = Integer.parseInt(str[18]);



        return new EnemyCar(width,height,xPosition,yPosition, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath, wheelXPosition,wheelYPosition, size,type);

    }
    public static com.mygdx.game.raceGame.PlayerCar loadPlayerCar(){
        Preferences prefs = Gdx.app.getPreferences("data");
        int weight = 1100 - prefs.getInteger("Корпус", 0 ) * 15;
        int steps = prefs.getInteger("Двигатель", 0) + prefs.getInteger("Трансмисия") + prefs.getInteger("Сцепление");
        int maxSpeed = (int)(120 + steps * 1.8);
        int[] speed_change = new int[]{30 + (int)(steps / 1.5), 50 + steps, 80 + (int)(steps * 1.4), 100 + (int)(steps * 1.8)};
        String bodyPath = "race_game/cars/toyota_supra.png";
        String wheelPath = "race_game/cars/wheel.png";
        return new PlayerCar(494,141,300,20, 5, speed_change, maxSpeed, weight, bodyPath, wheelPath);
    }
}
