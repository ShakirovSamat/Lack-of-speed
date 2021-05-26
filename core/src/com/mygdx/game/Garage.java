package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;


public class Garage implements Screen, InputProcessor {

    Main game;

    Texture background, icon_map, icon_upgrade;

    float w, h;

    MapIcon mIcon;
    UpgradeIcon uIcon;


    //Data
    Preferences prefs;
    int money;

    OrthographicCamera camera;


    public Garage(Main game) {
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        background = new Texture(Gdx.files.internal("garage/background.png"));
        icon_map = new Texture(Gdx.files.internal("garage/icon_map.png"));
        icon_upgrade = new Texture(Gdx.files.internal("garage/icon_upgrade.png"));

        uIcon = new UpgradeIcon(100, 100, 30, 30, "Upgrade", icon_upgrade);


        mIcon = new MapIcon(80, 110, 1180, 20, "Map", icon_map);

        prefs = Gdx.app.getPreferences("data");
        money = prefs.getInteger("money",0);


        camera = new OrthographicCamera(1280, 1280 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        money = prefs.getInteger("money",0);

        game.batch.begin();
        game.batch.draw(background, 0, 0, w, h);

        mIcon.draw(game.batch);
        uIcon.draw(game.batch, game);
        game.font_trans.draw(game.batch, money + " rubles", 1050,680);


        game.batch.end();
    }


    @Override
    public void show() {

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
        screenY = 720 - screenY;
        if (mIcon.isClicked(screenX, screenY)) mIcon.onClick(game);

        if(uIcon.isClicked(screenX, screenY)){
            uIcon.onClick();
        }

        if (uIcon.upgrade_menu.opened){
            for(int i = 0; i < uIcon.upgrade_menu.buttons.length; i++){
                uIcon.upgrade_menu.buttons[i].isTouched(screenX, screenY);
            }
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

}
