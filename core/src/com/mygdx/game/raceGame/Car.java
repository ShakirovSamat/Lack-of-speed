package com.mygdx.game.raceGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

 public class Car {
    long timeLock;

    // Properties
    public int weight;
    public int transmissions;
    public int[] speedChange;
    public int maxSpeed;
    public float curSpeed;
    public int curTransmission;
    public float cur_distance;

    //Conditions
    public boolean isStarted;
    public boolean isFinished;
    public boolean isSwitching;

    //Sizes
    public int width;
    public int height;
    public int xPosition;
    public int yPosition;
    public float body_rotation;

    //Graphic
    public Sprite body, wheel;
    int w,h;

    public Car(float width, float height, float xPosition, float yPosition, int transmissions, int[] speedChange, int maxSpeed, int weight, String bodyPath, String wheelPath){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        this.width = (int)(w / (1280 / width));
        this.height = (int)(h /(720 / height));
        this.xPosition = (int)(w / (1280 / xPosition));
        this.yPosition = (int)(h / (720 / yPosition));
        this.transmissions = transmissions;
        this.speedChange = speedChange;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        curSpeed = 0;
        curTransmission = 1;
        cur_distance = 0;
        isStarted = false;
        isFinished = false;
        isSwitching = false;
        timeLock = 0;
        body = new Sprite(new Texture(Gdx.files.internal(bodyPath)));
        body.setPosition(this.xPosition,this.yPosition);
        body.setSize(this.width, this.height);
        wheel = new Sprite(new Texture(Gdx.files.internal(wheelPath)));
    }

     public void go(){
         float speed = curSpeed;
         if(!isFinished && isStarted) {
             if (timeLock <= System.currentTimeMillis()) {
                 isSwitching = false;
                 switch (curTransmission) {
                     case 1:
                         if (curSpeed <= speedChange[0]) {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (speedChange[0] - curSpeed) * 0.005;
                         } else {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (curSpeed - speedChange[0]) * 0.06;
                         }
                         break;
                     case 2:
                         if (curSpeed <= speedChange[1]) {
                             curSpeed += 0.1 + (maxSpeed - curSpeed) / 100 - (speedChange[1] - curSpeed) * 0.005;
                         } else {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (curSpeed - speedChange[1]) * 0.06;
                         }
                         break;
                     case 3:
                         if (curSpeed <= speedChange[2]) {
                             curSpeed += 0.2 + (maxSpeed - curSpeed) / 100 - (speedChange[2] - curSpeed) * 0.005;
                         } else {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (curSpeed - speedChange[2]) * 0.06;
                         }
                         if(speedChange[curTransmission - 2] - curSpeed < 0){
                             curSpeed -= (speedChange[curTransmission - 2] - curSpeed) * 0.01;
                         }
                         break;
                     case 4:
                         if (curSpeed <= speedChange[3]) {
                             curSpeed += 0.3 + (maxSpeed - curSpeed) / 100 - (speedChange[3]  - curSpeed) * 0.005;
                         } else {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (curSpeed - speedChange[3]) * 0.06;
                         }

                         break;
                     case 5:
                         if(curSpeed <= maxSpeed) {
                             curSpeed += 0.4 + (maxSpeed - curSpeed) / 100 - (maxSpeed - curSpeed) * 0.005;
                         } else {
                             curSpeed += (maxSpeed - curSpeed) / 100 - (curSpeed - maxSpeed) * 0.06;
                         }

                         break;
                 }
                 if(curTransmission != 1 && speedChange[curTransmission - 2] - curSpeed > 0){
                     curSpeed -= (speedChange[curTransmission - 2] - curSpeed) * 0.008;
                 }
                 timeLock = System.currentTimeMillis() + 40;
             }
             if (!isSwitching) {
                 body_rotation += (curSpeed - speed) / 8 + 0.08;
                 if (body_rotation > 2) {
                     body_rotation = 2;
                 }
             }
         }

     }

    public void rest(){
        if(isFinished){
            curSpeed -= 0.6 * (maxSpeed / 120f);
        }
        if(curSpeed != 0){
            curSpeed -= 0.005;
            curSpeed -= weight * 0.0001;
        }
        if(curSpeed < 0){
            curSpeed = 0;
        }
        if(body_rotation > 0){
            body_rotation -= 0.08;
        }
        else if(body_rotation > -1 && isFinished && curSpeed != 0){
            body_rotation -= 0.08;
        }
        else if(isFinished && curSpeed == 0 && body_rotation < 0){
            body_rotation += 0.08;
        }

    }
    public void changeTransmission(int step){
        curTransmission += step;
        if(curTransmission < 0){
            curTransmission = 0;
        }
        else if(curTransmission >transmissions){
            curTransmission = transmissions;
        }
        isSwitching = true;
    }

    public void changeDistance(){
        cur_distance += curSpeed * Gdx.graphics.getDeltaTime() / 3600 * 1000;
    }
}
