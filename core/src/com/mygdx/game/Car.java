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
    float[] speeds;
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

    public Car(int width, int height, int xPosition, int yPosition, int transmissions, int[] speedChange, int maxSpeed, float[] speeds, int weight, String bodyPath, String wheelPath){
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.transmissions = transmissions;
        this.speedChange = speedChange;
        this.speeds = speeds;
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
                             curSpeed += speeds[0];
                         } else {
                             curSpeed += weight * 0.00008;
                         }
                         break;
                     case 2:
                         if (curSpeed <= speedChange[1]) {
                             curSpeed += speeds[1] - (2 - curTransmission) * 0.2;
                         } else {
                             curSpeed += weight * 0.00008;
                         }
                         break;
                     case 3:
                         if (curSpeed <= speedChange[2]) {
                             curSpeed += speeds[2] - (3 - curTransmission) * 0.1;
                         } else {
                             curSpeed += weight * 0.00008;
                         }
                         break;
                     case 4:
                         if (curSpeed <= speedChange[3]) {
                             curSpeed +=speeds[3] - (4 - curTransmission) * 0.07;
                         } else {
                             curSpeed += weight * 0.00008;
                         }
                         break;
                     case 5:
                         if(curSpeed <= maxSpeed) {
                             curSpeed += speeds[4] - (5 - curTransmission) * 0.07;
                         } else {
                             curSpeed += weight * 0.00008;
                         }
                         break;
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
