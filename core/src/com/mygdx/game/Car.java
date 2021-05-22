package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

 public class Car {
    long timeLock;

    // Properties
    int weight;
    int transmissions;
    int[] speedChange;
    int maxSpeed;
    public float curSpeed;
    public int curTransmission;
    float cur_distance;

    //Conditions
    boolean isStarted;
    boolean isFinished;
    boolean isSwitching;

    //Sizes
    int width;
    int height;
    public int xPosition;
    public int yPosition;
    float body_rotation;

    //Graphic
    Sprite body, wheel;

    public Car(int width, int height, int xPosition, int yPosition, int transmissions, int[] speedChange, int maxSpeed, int weight, String bodyPath, String wheelPath){
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
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
        body.setPosition(xPosition,yPosition);
        body.setSize(width, height);
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
                             curSpeed += 0.3 + (maxSpeed - curSpeed) / 100 - (speedChange[3] - curSpeed) * 0.005;
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
            curSpeed -= 0.6;
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
        else if(body_rotation > - 2 && isFinished && curSpeed != 0){
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
