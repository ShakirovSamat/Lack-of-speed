package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.text.DecimalFormat;

public class RaceGame implements Screen, InputProcessor {
    Main game;

    // Landscape
    Landscape landscape;

    //UI
    UI ui;

    //Cars
    public PlayerCar playerCar;
    public EnemyCar enemyCar;

    //Menu
    Menu menu;
    //Camera
    OrthographicCamera camera;

    //Results
    float time;

    //Animations
    Animation<TextureRegion> cube_appearance, cube_first, cube_second, cube_third;
    float stateTime;
    int step = 1;
    long timeLock = System.currentTimeMillis() + 3000;
    long timeLock2 = 0;

    // Parameters
    final static float SCREEN_WIDTH = Gdx.graphics.getWidth();
    final static float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    float distance; // in meters



    public RaceGame(Main game, int distance, PlayerCar playerCar, EnemyCar enemyCar){
        this.game = game;
        this.distance = distance;

        this.playerCar = playerCar;
        this.enemyCar = enemyCar;
        landscape = new Landscape();
        ui = new UI();

        game.font_trans.setColor(Color.WHITE);

        cube_appearance = getAnimation(4,2, "race_game/animations/cube_appearance.png", 16f);
        cube_first = getAnimation(4,4,"race_game/animations/cube_first.png", 30f);
        cube_second = getAnimation(4,4, "race_game/animations/cube_second.png", 30f);
        cube_third = getAnimation(4,4, "race_game/animations/cube_third.png", 30f);

        camera = new OrthographicCamera(1280,1280 * (SCREEN_HEIGHT / SCREEN_WIDTH));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        camera.update();
        Gdx.input.setInputProcessor(this);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        playerCar.changeDistance();
        enemyCar.go();
        enemyCar.rest();
        enemyCar.changeDistance();



        ui.checkTouchPedal(playerCar);
        playerCar.rest();


        game.batch.begin();

        landscape.draw(game.batch,playerCar);
        enemyCar.draw(game.batch, playerCar);
        playerCar.draw(game.batch);
        ui.draw(game.batch, game.font_speed, game.font_trans, playerCar);


        if(!landscape.isFinishing && playerCar.cur_distance >= distance){
            landscape.finish(playerCar);
        }
        else if(playerCar.isStarted){
            time += Gdx.graphics.getDeltaTime();
        }
        if(playerCar.isFinished && menu == null){
            String[] results = new String[2];
            String format_time = new DecimalFormat("#0.00").format(time);
            results[0] = "time: " + format_time + "c";
            results[1] = "distance: " + distance + "m";
            menu = new Menu(600,400, results);
        }
        if(menu != null){
            menu.draw(game.batch, game.font_speed, game.font_trans);
        }
        if(step < 5 && timeLock < System.currentTimeMillis()){
            showStartAnimation(game.batch);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float deltaWidth1280 = Gdx.graphics.getWidth() / 1280f;
        float x = screenX / deltaWidth1280;
        float y = Gdx.graphics.getHeight() - screenY;
        ui.checkTouchTransmissions(x, y,playerCar);
        if(menu != null){
            menu.ok.isTouched(game, x, y, playerCar, enemyCar);
            menu.restart.isTouched(game, x, y, playerCar, enemyCar);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Animation<TextureRegion> getAnimation(int c, int r, String path, float speed){
        Texture tex_animation = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(tex_animation, tex_animation.getWidth() / c, tex_animation.getHeight() / r);
        TextureRegion[] flipFrames = new TextureRegion[c * r];
        int index = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                flipFrames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(1f/speed, flipFrames);
    }

    public void showStartAnimation(Batch batch){
        TextureRegion curFrame = null;
        if(timeLock2 < System.currentTimeMillis()){

        }
        switch (step){
            case 1:
                curFrame = cube_appearance.getKeyFrame(stateTime, false);
                if(cube_appearance.isAnimationFinished(stateTime) && timeLock2 == 0){
                    timeLock2 = System.currentTimeMillis() + 1000;
                }
                else if(cube_appearance.isAnimationFinished(stateTime) && timeLock2 < System.currentTimeMillis()){
                    step++;
                    stateTime = 0;
                    timeLock2 = 0;
                }
                break;
            case 2:
                curFrame = cube_first.getKeyFrame(stateTime, false);
                if(cube_first.isAnimationFinished(stateTime) && timeLock2 == 0){
                    timeLock2 = System.currentTimeMillis() + 700;
                }
                else if(cube_first.isAnimationFinished(stateTime) && timeLock2 < System.currentTimeMillis()){
                    step++;
                    stateTime = 0;
                    timeLock2 = 0;
                }
                break;
            case 3:
                curFrame = cube_second.getKeyFrame(stateTime, false);
                if(cube_second.isAnimationFinished(stateTime) && timeLock2 == 0){
                    timeLock2 = System.currentTimeMillis() + 700;
                }
                else if(cube_second.isAnimationFinished(stateTime) && timeLock2 < System.currentTimeMillis()){
                    step++;
                    stateTime = 0;
                    timeLock2 = 0;
                }
                break;
            case 4:
                curFrame = cube_third.getKeyFrame(stateTime, false);
                if(cube_third.isAnimationFinished(stateTime) && timeLock2 == 0){
                    timeLock2 = System.currentTimeMillis() + 500;
                    playerCar.isStarted = true;
                    enemyCar.isStarted = true;
                }
                else if(cube_third.isAnimationFinished(stateTime) && timeLock2 < System.currentTimeMillis()){
                    step++;
                    stateTime = 0;
                    timeLock2 = 0;
                }
                break;
        }
        batch.draw(curFrame,(int)(SCREEN_WIDTH / 2 - 96), (int)(SCREEN_HEIGHT / 2 - 96), 192,192);
        stateTime += Gdx.graphics.getDeltaTime();
    }
}


