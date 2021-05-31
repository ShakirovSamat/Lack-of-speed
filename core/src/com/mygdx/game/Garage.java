package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Garage implements Screen, InputProcessor {

    Main game;

    Texture background, icon_map, icon_upgrade;

    float w, h;

    MapIcon mIcon;
    UpgradeIcon uIcon;


    //Data
    Preferences prefs;
    int money;

    private Viewport viewport;
    private Camera camera;


    public Garage(Main game) {
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        background = new Texture(Gdx.files.internal("garage/background.png"));
        icon_map = new Texture(Gdx.files.internal("garage/icon_map.png"));
        icon_upgrade = new Texture(Gdx.files.internal("garage/icon_upgrade.png"));

        uIcon = new UpgradeIcon((int)(w / 12.8), (int)(w / 12.8), (int)(w / 42.6), (int)(h / 24), "Upgrade", icon_upgrade);


        mIcon = new MapIcon((int)(w / 16), (int)(h / 6.54), (int)(w / 1.08), (int)(h / 36), "Map", icon_map);

        prefs = Gdx.app.getPreferences("data");
        money = prefs.getInteger("money",0);


        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        money = prefs.getInteger("money",0);

        game.batch.begin();
        game.batch.draw(background, 0, 0, w, h);

        mIcon.draw(game.batch);
        uIcon.draw(game.batch, game);
        game.font_trans.draw(game.batch, money + " рублей", (int)(w / 1.22),(int)(h / 1.05));


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
        float x = screenX;
        float y = Gdx.graphics.getHeight() - screenY;
        if (mIcon.isClicked(x, y)) mIcon.onClick(game);

        if(uIcon.isClicked(x, y)){
            uIcon.onClick();
        }

        if (uIcon.upgrade_menu.opened){
            for(int i = 0; i < uIcon.upgrade_menu.buttons.length; i++){
                uIcon.upgrade_menu.buttons[i].isTouched(x, y);
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
