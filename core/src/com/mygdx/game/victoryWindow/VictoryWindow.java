package com.mygdx.game.victoryWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.menu.GameMenu;

public class VictoryWindow implements Screen, InputProcessor {

    final Main game;

    float w, h;
    float slideStateTime;

    //Data
    Preferences prefs;

    Texture background;

    Animation<TextureRegion> text, slide;
    float textStateTime;
    long durationAnimation;

    long timeLock;


    private Viewport viewport;
    private Camera camera;

    public VictoryWindow(final Main game){
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();


        background = new Texture(Gdx.files.internal("gamePassed/background.jpg"));

        text = getAnimation(1,20,"animations/text.png", 20f);
        slide = getAnimation(5,4,"animations/slide_animation.png", 40f);
        slide.setPlayMode(Animation.PlayMode.REVERSED);

        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        timeLock = System.currentTimeMillis() + 4000;

        prefs = Gdx.app.getPreferences("data");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(background,0,0,w,h);

        if(timeLock <= System.currentTimeMillis()) {
            TextureRegion text_animation = text.getKeyFrame(textStateTime, false);
            game.batch.draw(text_animation, (int) ((w - w / 2.5) / 2), (int) (h / 6), (int) (w / 2.5), (int) (h / 20));
            if (durationAnimation <= System.currentTimeMillis()) {
                textStateTime += Gdx.graphics.getDeltaTime();
            }
            if (durationAnimation <= System.currentTimeMillis() && text.isAnimationFinished(textStateTime)) {
                durationAnimation = System.currentTimeMillis() + 700;
                textStateTime = 0;
            }
        }
        if(!Main.slide.isAnimationFinished(slideStateTime)){
            game.batch.draw(Main.slide.getKeyFrame(slideStateTime,false), 0,0,w,h);
            slideStateTime += Gdx.graphics.getDeltaTime();
        }
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
        if(timeLock <= System.currentTimeMillis()){
            prefs.clear();
            prefs.flush();
            game.setScreen(new GameMenu(game));
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

    public Animation<TextureRegion> getAnimation(int c, int r, String path, float duration){
        Texture tex_animation = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(tex_animation, tex_animation.getWidth() / c, tex_animation.getHeight() / r);
        TextureRegion[] flipFrames = new TextureRegion[c * r];
        int index = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                flipFrames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(1f/duration, flipFrames);
    }

}
