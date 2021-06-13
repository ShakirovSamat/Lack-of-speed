package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Button;
import com.mygdx.game.Main;
import com.mygdx.game.Unit;
import com.mygdx.game.map.Map;

public class GameMenu implements Screen, InputProcessor {

    final Main game;

    float w, h;
    float slideStateTime;

    //Data
    Preferences prefs;

    public static Music music;

    SureMenu sureMenu;

    Texture background;

    Button button_start, button_continue;

    private Viewport viewport;
    private Camera camera;

    public GameMenu(final Main game){
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
        music.setVolume(0.3f);
        music.setLooping(true);
        music.play();


        prefs = Gdx.app.getPreferences("data");

        background = new Texture(Gdx.files.internal("Menu/background.jpg"));
        button_start = new Button((int)(w / 3.2),(int)(h / 10.8),(int)(w / 20),(int)((h - (int)(h / 6.42)) / 2),
                new Texture(Gdx.files.internal("Menu/button_start.png")), new Texture(Gdx.files.internal("Menu/button_start_touched.png"))){
          @Override
          public void onClick(Main game){
              if(prefs.contains("money")){
                  if(sureMenu == null){
                      sureMenu = new SureMenu((int)(w / 2.68), (int)(h / 2.88), (int)(w - (int)(w / 2.68)) / 2 + (int)(w / 15) , (int)((h - (int)(h / 2.88)) / 2), new Texture(Gdx.files.internal("Menu/sureMenu_background.png")));
                  }
              }
              else{
                  prefs.clear();
                  prefs.flush();
                  game.setScreen(new com.mygdx.game.map.Map(game));
              }
          }
        };

        if(prefs.contains("money")){
            button_continue = new Button((int)(w / 3.2),(int)(h / 10.8),(int)(w / 20),(int)((h - (int)(h / 6.42)) / 3.2),
                    new Texture(Gdx.files.internal("Menu/button_continue.png")), new Texture(Gdx.files.internal("Menu/button_continue_touched.png"))){
                @Override
                public void onClick(Main game){
                    game.setScreen(new com.mygdx.game.map.Map(game));
                }
            };
        }

        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(background,0,0,w,h);
        button_start.draw(game.batch);
        if(prefs.contains("money")){
            button_continue.draw(game.batch);
        }
        if(sureMenu != null){
            sureMenu.draw(game.batch);
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
        float x = screenX;
        float y = Gdx.graphics.getHeight() - screenY;
        if(sureMenu != null){
            sureMenu.button_yes.isClicked(x,y,game);
            sureMenu.button_no.isClicked(x,y,game);
        }
        else{
            if(prefs.contains("money")){
                button_continue.isClicked(x, y, game);
            }
            button_start.isClicked(x, y, game);
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

    class SureMenu extends Unit {

        Button button_yes,button_no;
        Texture texture;
        public SureMenu(int width, int height, int xPosition, int yPosition, Texture texture) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;

            button_yes = new Button((int)(w / 12), (int)(w / 12),(int)(xPosition + (int)(w / 17)), yPosition + (int)(h / 21),
                    new Texture(Gdx.files.internal("Menu/button_yes.png")), new Texture(Gdx.files.internal("Menu/button_yes.png"))){
                @Override
                public void onClick(Main game){
                    prefs.clear();
                    prefs.flush();
                    game.setScreen(new Map(game));
                }
            };
            button_no = new Button((int)(w / 12), (int)(w / 12),(int)(xPosition + width - (int)(w / 12) - (int)(w / 17)), yPosition + (int)(h / 21),
                    new Texture(Gdx.files.internal("Menu/button_no.png")), new Texture(Gdx.files.internal("Menu/button_no.png"))){
                @Override
                public void onClick(Main game){
                    sureMenu = null;
                }
            };
        }

        public void draw(Batch batch){
            batch.draw(texture,xPosition,yPosition,width,height);
            button_yes.draw(batch);
            button_no.draw(batch);
        }
    }

}
