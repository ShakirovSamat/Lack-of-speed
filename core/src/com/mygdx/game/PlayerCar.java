package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerCar extends Car{

    public PlayerCar(int width, int height, int xPosition, int yPosition, int transmissions, int[] speedChange, int maxSpeed,  int weight, String bodyPath, String wheelPath) {
        super(width, height, xPosition, yPosition, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath);
        wheel.setScale(1.05f);
    }




    public void draw(Batch batch){
        body.setRotation(body_rotation);
        body.draw(batch);
        wheel.setPosition(xPosition + 76,yPosition);
        float wheel_rotation;
        if(isFinished){
            wheel_rotation = 0;
        }
        else if(curSpeed >= 100){
            wheel_rotation = 40;
        }
        else{
            wheel_rotation = curSpeed / 3;
        }
        if(wheel.getRotation() <= -360){
            wheel.setRotation(0);
        }

        wheel.setRotation(wheel.getRotation() - wheel_rotation);
        wheel.draw(batch);
        wheel.setPosition(xPosition + width - 146,yPosition);
        wheel.draw(batch);
    }



    static class Pedal{

        public int xPosition;
        public int yPosition;
        public int width;
        public int height;
        public boolean ispPressed;
        Texture texture;

        public Pedal(int xPosition, int yPosition, int width, int height, Texture texture){
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.width = width;
            this.height = height;
            this.texture = texture;
            ispPressed = false;
        }

        public void draw(Batch batch){
            if(!ispPressed){
                batch.draw(texture,xPosition, yPosition, width, height);
            }
            else{
                batch.draw(texture,xPosition, yPosition, width, height - 30);
            }
        }

        public void checkTouch(PlayerCar playerCar){
            if(Gdx.input.isTouched()){
                float x  = Gdx.input.getX();
                float y  = 1280 * (RaceGame.SCREEN_HEIGHT / RaceGame.SCREEN_WIDTH) - Gdx.input.getY();
                if(xPosition <= x && x <= xPosition + width
                        && yPosition <= y && y <= yPosition + height){
                    ispPressed = true;
                }
                else{
                    ispPressed = false;
                }
            }
            else{
                ispPressed = false;
            }
            if(ispPressed){
                playerCar.go();
            }
        }
    }
    static class TransmissionButton{
        public int type; // 0 = minus; 1 = plus;
        public int width;
        public int height;
        public int xPosition;
        public int yPosition;
        Texture texture;

        public TransmissionButton(int type, int width, int height, int xPosition, int yPosition) {
            this.type = type;
            this.width = width;
            this.height = height;
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            if(type == 0){
                texture = new Texture(Gdx.files.internal("race_game/minus.png"));
            }
            else if(type == 1){
                texture = new Texture(Gdx.files.internal("race_game/plus.png"));
            }
        }

        public void draw(Batch batch){
            batch.draw(texture,xPosition,yPosition,width,height);
        }

        public void checkTouch(float x, float y, PlayerCar playerCar){
            if(xPosition <= x && x <= xPosition  + width
                    && yPosition <= y && y <= yPosition  + height){
                playerCar.changeTransmission(type * 2 - 1 );
                playerCar.timeLock = System.currentTimeMillis() + 200;
            }
        }


    }

}

