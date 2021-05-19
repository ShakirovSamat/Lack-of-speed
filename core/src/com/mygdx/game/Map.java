package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;



public class Map implements Screen, InputProcessor {
    Main game;
    Texture map, icon_store, icon_garage, icon_race;
    OrthographicCamera camera;
    float w, h;
    int money;
    Preferences prefs;

    Icon store, garage, race;

    public Map(final Main game) {
        this.game = game;
        game.font_trans.setColor(Color.BLACK);

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        map = new Texture(Gdx.files.internal("Map/map.png"));
        icon_store = new Texture(Gdx.files.internal("Map/icon_store.png"));
        icon_garage = new Texture(Gdx.files.internal("Map/icon_garage.png"));
        icon_race = new Texture(Gdx.files.internal("Map/icon_race.png"));
        store = new Icon(50,50, 1150,400, "Store", icon_store);
        garage = new Icon(50,50, 800,600, "Garage", icon_garage);
        race = new Icon(50,50, 150,380, "Race", icon_race);

        camera = new OrthographicCamera(1280,1280 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        camera.update();
        Gdx.input.setInputProcessor(this);

        prefs = Gdx.app.getPreferences("data");
        money = prefs.getInteger("money",0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(map,0,0,w,h);
        store.draw(game.batch);
        garage.draw(game.batch);
        race.draw(game.batch);
        game.font_trans.draw(game.batch, money + " rubles", 1050,690);
        game.batch.end();
        if(Gdx.input.isTouched()){
            store.onClick(game);
            race.onClick(game);
            garage.onClick(game);
        }
    }

    @Override
    public void dispose() {
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
    public void show() {

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

    public static Car loadCar(String dataPath, int type){
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
        if(type == 0){
            return new PlayerCar(487,126,300,20, transmissions, speedChange, maxSpeed, speeds, weight, bodyPath, wheelPath);
        }
        else{
            return new EnemyCar(487,126,300,120, transmissions, speedChange, maxSpeed, speeds, weight, bodyPath, wheelPath);
        }
    }


}





