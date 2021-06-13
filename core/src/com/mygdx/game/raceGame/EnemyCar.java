package com.mygdx.game.raceGame;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

public class EnemyCar extends Car {
    int nextChange;
    Random random;
    long waitLock;
    int type;
    int[] wheelXPosition, wheelYPosition;
    public EnemyCar(int width, int height, int xPosition, int yPosition, int transmissions, int[] speedChange, int maxSpeed, int weight, String bodyPath, String wheelPath, int[] wheelXPosition, int[] wheelYPosition, float size, int type) {
        super(width, height, xPosition, yPosition, transmissions, speedChange, maxSpeed, weight, bodyPath, wheelPath);
        this.wheelXPosition = wheelXPosition;
        this.wheelYPosition = wheelYPosition;
        this.type = type;
        this.speedChange = speedChange;

        random = new Random();
        waitLock = 0;
        nextChange = speedChange[0] - (10 + random.nextInt(15));
        wheel.setSize((int)(w / (1280 / size)), (int)(w / (1280 / size)));
        wheel.setOrigin((int)(w / (1280f / size)) / 2f, (int)(w / (1280f / size)) / 2f);
    }


    public void draw(Batch batch, PlayerCar playerCar) {
        body.setRotation(body_rotation);
        if(type == 0){
            batch.draw(body, xPosition + (cur_distance - playerCar.cur_distance) * 75, yPosition, width, height);
        }
        wheel.setPosition(xPosition + wheelXPosition[0] + (cur_distance - playerCar.cur_distance) * 75,yPosition + wheelYPosition[0]);
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
        wheel.setPosition(xPosition + wheelXPosition[1] + (cur_distance - playerCar.cur_distance) * 75,yPosition + wheelYPosition[1]);
        if(type == 1){
            batch.draw(body, xPosition + (cur_distance - playerCar.cur_distance) * 75, yPosition, width, height);
        }
        wheel.draw(batch);
    }

    @Override
    public void go() {
        if(waitLock != 0 && waitLock < System.currentTimeMillis()){
            timeLock = System.currentTimeMillis() + 500;
            waitLock = 0;
        }
        else if (waitLock < System.currentTimeMillis()) {
            super.go();
            if (curSpeed >= nextChange) {
                changeTransmission(1);
                timeLock = System.currentTimeMillis() + 200;
            }
            switch (curTransmission) {
                case 1:
                    if (curSpeed >= speedChange[0] - 1 && nextChange > speedChange[0]) {
                        changeTransmission(1);
                        waitLock = System.currentTimeMillis() + (nextChange - speedChange[0]) * 20;
                    }
                    break;
                case 2:
                    if (curSpeed >= speedChange[1] - 1&& nextChange > speedChange[1]) {
                        changeTransmission(1);
                        waitLock = System.currentTimeMillis() + (nextChange - speedChange[1]) * 20;
                    }
                    break;
                case 3:
                    if (curSpeed >= speedChange[2] - 1 && nextChange > speedChange[2]) {
                        changeTransmission(1);
                        waitLock = System.currentTimeMillis() + (nextChange - speedChange[2]) * 20;
                    }
                    break;
                case 4:
                    if (curSpeed >= speedChange[3] - 1 && nextChange > speedChange[3]) {
                        changeTransmission(1);
                        waitLock = System.currentTimeMillis() + (nextChange - speedChange[3]) * 20;
                    }
                    break;
            }
        }


    }

    @Override
    public void changeTransmission(int step) {
        switch (curTransmission){
            case 1:
                nextChange = speedChange[1] - 10 + random.nextInt(15);
                break;
            case 2:
                nextChange = speedChange[2] + random.nextInt(15);
                break;
            case 3:
                nextChange = speedChange[3] + random.nextInt(15);
                break;
            case 4:
                nextChange = maxSpeed + random.nextInt(15);
                break;

        }
        super.changeTransmission(step);
    }

    @Override
    public void rest() {
        if(waitLock != 0 && waitLock < System.currentTimeMillis()){
            timeLock = System.currentTimeMillis() + 200;
            waitLock = 0;
        }
        else if(waitLock < System.currentTimeMillis()){
            super.rest();
        }

    }
}
