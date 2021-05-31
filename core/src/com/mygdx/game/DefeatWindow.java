package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DefeatWindow implements Screen, InputProcessor {

    final Main game;

    float w, h;

    Texture background;
    public Button button_exit;

    private Viewport viewport;
    private Camera camera;

    public DefeatWindow(final Main game){
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        background = new Texture(Gdx.files.internal("gameOver/game_over_background.png"));
        button_exit = new Button((int)(w / 4.26),(int)(h / 6.42),(int)(w /1.33),(int)(h / 18), new Texture(Gdx.files.internal("gameOver/button.png")));

        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(background,0,0,w,h);
        button_exit.draw(game.batch);
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

        button_exit.onClick(x,y);
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
    class Button extends Unit {
        Texture texture;
        public Button(int width, int height, int xPosition, int yPosition, Texture texture) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
        }
        public void draw(Batch batch){
            batch.draw(texture,xPosition,yPosition,width,height);
        }
        public void onClick(float x, float y){
            if(xPosition <= x && x <= xPosition + width
                    && yPosition <= y && y <= yPosition + height) {
                game.setScreen(new Map(game));
            }
        }
    }

}
