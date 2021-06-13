package com.mygdx.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Garbage{
    public int width, height;
    public int xPosition, yPosition;

    public String type;
    public int weight;

    public boolean isFallen;
    public float fallingSpeed;
    long fallingLock;

    Random random;

    Texture texture;
    Sprite sprite;

    float w,h;

    public Garbage() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        width = (int)(w / 15);
        height = (int)(h / 16.36);
        xPosition = 0;
        yPosition = (int)(h / 2.55);
        isFallen = false;
        fallingSpeed = 1;
        fallingLock = 0;
        random = new Random();


        weight = 1 + random.nextInt(3);
        int ran = random.nextInt(4);
        String path = "Factory/garbage/";

        switch (ran){
            case 0:
                type = "metal";
                switch (weight){
                    case 1:
                        path += "pepsi.png";
                        yPosition = (int)(h / 2.5);
                        break;
                    case 2:
                        path += "saw.png";
                        yPosition = (int)(h / 2.62);
                        break;
                    case 3:
                        path += "weight.png";
                        yPosition = (int)(h / 2.55);
                        break;
                }
                break;
            case 1:
                type = "paper";
                switch (weight){
                    case 1:
                        path += "notebook.png";
                        break;
                    case 2:
                        path += "book.png";
                        break;
                    case 3:
                        path += "toilet_paper.png";
                        break;
                }
                break;
            case 2:
                type = "plastic";
                switch (weight){
                    case 1:
                        path += "bottle.png";
                        break;
                    case 2:
                        path += "shovel.png";
                        break;
                    case 3:
                        path += "water.png";
                        break;
                }
                break;
            case 3:
                type = "glass";
                switch (weight){
                    case 1:
                        path += "cup.png";
                        break;
                    case 2:
                        path += "glass_bank.png";
                        break;
                    case 3:
                        path += "vase.png";
                        break;
                }
                break;
        }
        texture = new Texture(Gdx.files.internal(path));
        width = (int)(w / (1920 / (texture.getWidth() / 1.9)));
        height = (int)(h / (1080 / (texture.getHeight() / 1.9)));
        sprite = new Sprite(texture);
        sprite.setSize(width,height);
        sprite.setOrigin(width/2,height/2);
    }
    public void draw(Batch batch){
        sprite.setPosition(xPosition,yPosition);
        sprite.draw(batch);
    }
    public void falling(){
        if(fallingLock <= System.currentTimeMillis()) {
            yPosition -= (int) (w / (1920 / (int) fallingSpeed));
            xPosition -= (int) (w / (1920 / (int) fallingSpeed)) / 3;
            fallingSpeed *= 1.15;
            sprite.setRotation(sprite.getRotation() - fallingSpeed / 3);
            fallingLock = System.currentTimeMillis() + 5;
        }
    }
}
