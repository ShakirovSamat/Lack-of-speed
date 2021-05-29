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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;



public class Map implements Screen, InputProcessor {
    final Main game;
    Texture map, icon_store, icon_garage, icon_race, icon_tournament;
    OrthographicCamera camera;
    float w, h;
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

        map = new Texture(Gdx.files.internal("Map/map.png"));
        icon_store = new Texture(Gdx.files.internal("Map/icon_store.png"));
        icon_garage = new Texture(Gdx.files.internal("Map/icon_garage.png"));
        icon_race = new Texture(Gdx.files.internal("Map/icon_race.png"));
        icon_tournament = new Texture(Gdx.files.internal("Map/icon_tournament.png"));

        rIcon = new RaceIcon(50,50, 230,635, "Race", icon_race);
        gIcon = new GarageIcon(50,50, 800,600, "Garage", icon_garage);
        sIcon = new StoreIcon(50,50, 1150,400, "Store", icon_store);
        tIcon = new TournamentIcon(50,50, 150,380, "Tournament", icon_tournament);


        camera = new OrthographicCamera(1280,1280 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        camera.update();

        tournamentMenu = new AdditionalMenu(696, 564, (int) (w - 696) / 2, (int) (h - 564) / 2, new Texture(Gdx.files.internal("garage/upgrade_background.png")));
        menuOpened = false;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(map,0,0,1280,720);
        game.font_trans.draw(game.batch, money + " рублей", 1050,680);

        gIcon.draw(game.batch);
        rIcon.draw(game.batch);
        sIcon.draw(game.batch);
        tIcon.draw(game.batch, game.font_trans);

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
        float deltaWidth1280 = Gdx.graphics.getWidth() / 1280f;
        float x = screenX / deltaWidth1280;
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





