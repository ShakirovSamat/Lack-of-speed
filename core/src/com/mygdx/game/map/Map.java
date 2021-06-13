package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.menu.GameMenu;
import com.mygdx.game.Main;


public class Map implements Screen, InputProcessor {
    final Main game;
    Texture map, icon_store, icon_garage, icon_race, icon_tournament, icon_factory;
    float w, h;
    float slideStateTime;
    int money;
    boolean menuOpened;
    String status;

    //Data
    Preferences prefs;

    //Menu
    AdditionalMenu tournamentMenu;

    //Icons
    RaceIcon rIcon;
    GarageIcon gIcon;
    StoreIcon sIcon;
    TournamentIcon tIcon;
    FactoryIcon fIcon;

    private Viewport viewport;
    private Camera camera;


    public Map(final Main game) {
        this.game = game;
        game.font_trans.setColor(Color.BLACK);

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        prefs = Gdx.app.getPreferences("data");
        status = prefs.getString("status","null");
        if(status.equals("defeat")){
            prefs.clear();
            prefs.flush();
        }
        money = prefs.getInteger("money",0);
        prefs.putInteger("money",money);
        prefs.flush();

        map = new Texture(Gdx.files.internal("Map/map.png"));
        icon_store = new Texture(Gdx.files.internal("Map/icon_store.png"));
        icon_garage = new Texture(Gdx.files.internal("Map/icon_garage.png"));
        icon_race = new Texture(Gdx.files.internal("Map/icon_race.png"));
        icon_tournament = new Texture(Gdx.files.internal("Map/icon_tournament.png"));
        icon_factory = new Texture(Gdx.files.internal("Map/icon_factory.png"));

        int iconW_H = (int)w / 25;
        rIcon = new RaceIcon(iconW_H,iconW_H, (int)(w / 5.5),(int)(h / 1.14), "Race", icon_race);
        gIcon = new GarageIcon(iconW_H,iconW_H, (int)(w / 1.6),(int)(h / 1.2), "Garage", icon_garage);
        sIcon = new StoreIcon(iconW_H,iconW_H, (int)(w / 1.1),(int)(h / 1.8), "Store", icon_store);
        tIcon = new TournamentIcon(iconW_H,iconW_H, (int)(w / 8.53),(int)(h / 1.9), "Tournament", icon_tournament);
        fIcon = new FactoryIcon(iconW_H, iconW_H, (int)(w / 1.95),(int)(h / 3), "Factory", icon_factory);



        tournamentMenu = new AdditionalMenu(696, 564, (int) (w - 696) / 2, (int) (h - 564) / 2, new Texture(Gdx.files.internal("garage/upgrade_background.png")));
        menuOpened = false;

        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        if(!GameMenu.music.isPlaying()){
            GameMenu.music.play();
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(map,0,0,w,h);
        game.font_trans.draw(game.batch, money + " рублей", (int)(w / 1.22),(int)(h / 1.05));

        gIcon.draw(game.batch);
        fIcon.draw(game.batch);
        fIcon.draw(game.batch);
        rIcon.draw(game.batch);
        sIcon.draw(game.batch);
        tIcon.draw(game.batch, game.font_trans);

        if(!Main.slide.isAnimationFinished(slideStateTime)){
            game.batch.draw(Main.slide.getKeyFrame(slideStateTime,false), 0,0,w,h);
            slideStateTime += Gdx.graphics.getDeltaTime();
        }
        game.batch.end();
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
        float x = screenX;
        float y = Gdx.graphics.getHeight() - screenY;
        if(rIcon.additionalMenu.opened){
            rIcon.additionalMenu.start.isTouched(game);
            rIcon.additionalMenu.close();
        }
        if(sIcon.additionalMenu.opened){
            sIcon.additionalMenu.start.isTouched(game);
            sIcon.additionalMenu.close();
        }
        if(tIcon.additionalMenu.opened){
            tIcon.additionalMenu.start.isTouched(game);
            tIcon.additionalMenu.close();
        }
        if(fIcon.additionalMenu.opened){
            fIcon.additionalMenu.start.isTouched(game);
            fIcon.additionalMenu.close();
        }

        if(rIcon.isClicked(x, y)){
            rIcon.onClick();
        }
        if(gIcon.isClicked(x, y)){
            gIcon.onClick(game);
        }
        if(sIcon.isClicked(x, y)){
            sIcon.onClick();
        }
        if(tIcon.isClicked(x, y)){
            tIcon.onClick();
        }
        if(fIcon.isClicked(x, y)){
            fIcon.onClick();
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





