package com.mygdx.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Button;
import com.mygdx.game.Main;
import com.mygdx.game.map.Map;
import com.mygdx.game.Menu;
import com.mygdx.game.Unit;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FactoryGame implements Screen, InputProcessor {

    Main game;

    float w, h;
    float slideStateTime;

    FactoryButton metal_button, paper_button, plastic_button, glass_button;

    Texture background_back, background_middle, background_front;
    Manhole metal_manhole, glass_manhole, plastic_manhole, paper_manhole;

    ArrayList<Garbage> garbage = new ArrayList<>();

    //Data
    Preferences prefs;

    //Menu
    Menu menu;

    long duration;
    long timeLock;

    int score;
    float time;

    boolean isOver;


    private Viewport viewport;
    private Camera camera;


    public FactoryGame(Main game) {
        this.game = game;

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        metal_button = new FactoryButton((int)(w / 15), (int)(w /15),(int)(w / 19.2), (int)(h / 24.6),
                new Texture(Gdx.files.internal("Factory/buttons/metal_off.png")), new Texture(Gdx.files.internal("Factory/buttons/metal_on.png")));



        glass_button = new FactoryButton((int)(w / 15), (int)(w /15),(int)(w / 5.85), (int)(h / 24.6),
                new Texture(Gdx.files.internal("Factory/buttons/glass_off.png")), new Texture(Gdx.files.internal("Factory/buttons/glass_on.png")));



        plastic_button = new FactoryButton((int)(w / 15), (int)(w /15),(int)(w / 1.311), (int)(h / 24.6),
                new Texture(Gdx.files.internal("Factory/buttons/plastic_off.png")), new Texture(Gdx.files.internal("Factory/buttons/plastic_on.png")));



        paper_button = new FactoryButton((int)(w / 15), (int)(w /15),(int)(w / 1.134), (int)(h / 24.6),
                new Texture(Gdx.files.internal("Factory/buttons/paper_off.png")), new Texture(Gdx.files.internal("Factory/buttons/paper_on.png")));





        background_back = new Texture(Gdx.files.internal("Factory/background_back.png"));
        background_middle = new Texture(Gdx.files.internal("Factory/background_middle.png"));
        background_front = new Texture(Gdx.files.internal("Factory/background_front.png"));


        metal_manhole = new Manhole((int)(w / 7.5294),(int)(h / 8.1203),(int)(w / 2.807), (int)(h / 2.666), new Texture(Gdx.files.internal("Factory/manhole.png")));
        glass_manhole = new Manhole((int)(w / 7.5294),(int)(h / 8.1203),(int)(w / 1.91), (int)(h / 2.666), new Texture(Gdx.files.internal("Factory/manhole.png")));
        plastic_manhole = new Manhole((int)(w / 7.5294),(int)(h / 8.1203),(int)(w / 1.4589), (int)(h / 2.666), new Texture(Gdx.files.internal("Factory/manhole.png")));
        paper_manhole = new Manhole((int)(w / 7.5294),(int)(h / 8.1203),(int)(w / 1.1743), (int)(h / 2.666), new Texture(Gdx.files.internal("Factory/manhole.png")));


        duration = 3;
        timeLock = 0;

        camera = new PerspectiveCamera();
        viewport = new ScreenViewport(camera);

        isOver = false;

        prefs = Gdx.app.getPreferences("data");

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        if(time >= 10 && !isOver){
            int money = 0;
            if(score > 0){
                money = score * (50 + (int)(Math.random() * 100));
            }

            prefs.putInteger("money",prefs.getInteger("money",0) + money);
            prefs.flush();

            String[] results = new String[2];
            results[0] = "Набрано " + score + " очков";
            results[1] = "Заработано " + money + " рублей";
            menu = new Menu((int)(w / 1.6),(int)(h / 1.71), results);



            isOver = true;
        }


        if(timeLock <= System.currentTimeMillis()){
            garbage.add(new Garbage());
            timeLock = (System.currentTimeMillis() + duration * 1000);
        }
        game.batch.begin();
        game.batch.draw(background_back,0,0,w,h);


        if(metal_button.isOpened){
            metal_manhole.draw(game.batch);
            if (metal_button.timeLock <= System.currentTimeMillis()){
                metal_button.isOpened = false;
            }
        }
        if(paper_button.isOpened){
            paper_manhole.draw(game.batch);
            if (paper_button.timeLock <= System.currentTimeMillis()){
                paper_button.isOpened = false;
            }
        }
        if(plastic_button.isOpened){
            plastic_manhole.draw(game.batch);


            if (plastic_button.timeLock <= System.currentTimeMillis()){
                plastic_button.isOpened = false;
            }
        }
        if(glass_button.isOpened){
            glass_manhole.draw(game.batch);
            if (glass_button.timeLock <= System.currentTimeMillis()){
                glass_button.isOpened = false;
            }
        }
        for(int i = 0; i < garbage.size(); i++){
            int goal = garbage.get(i).xPosition + (int)(garbage.get(i).width / 3);
            if(metal_button.isOpened && metal_manhole.xPosition <= goal && goal <= metal_manhole.xPosition + metal_manhole.width / 1.5){
                if(!garbage.get(i).isFallen) {
                    if (garbage.get(i).type.equals("metal")) {
                        score += 2;
                    } else {
                        score -= 1;
                    }
                }
                garbage.get(i).isFallen = true;

            }
            if(glass_button.isOpened && glass_manhole.xPosition <= goal && goal <=glass_manhole.xPosition + glass_manhole.width / 1.5){
                if(!garbage.get(i).isFallen) {
                    if (garbage.get(i).type.equals("glass")) {
                        score += 2;
                    } else {
                        score -= 1;
                    }
                }
                garbage.get(i).isFallen = true;
            }
            if(plastic_button.isOpened && plastic_manhole.xPosition <= goal && goal <= plastic_manhole.xPosition + plastic_manhole.width / 1.5){
                if(!garbage.get(i).isFallen) {
                    if (garbage.get(i).type.equals("plastic")) {
                        score += 2;
                    } else {
                        score -= 1;
                    }
                }
                garbage.get(i).isFallen = true;
            }
            if(paper_button.isOpened && paper_manhole.xPosition <= goal && goal <= paper_manhole.xPosition + paper_manhole.width / 1.5){
                if(!garbage.get(i).isFallen) {
                    if (garbage.get(i).type.equals("paper")) {
                        score += 2;
                    } else {
                        score -= 1;
                    }
                }
                garbage.get(i).isFallen = true;
            }
        }

        for (int i = 0; i < garbage.size(); i++) {
            if(!isOver){
                int step = 3;
                if (score >= 0 && score / 4 <= 4) {
                    step += score / 4;
                } else if (score / 4 == 4) {
                    step += 4;
                }
                garbage.get(i).xPosition += (int) (w / (1920 / step));
            }
            garbage.get(i).draw(game.batch);
            if (garbage.get(i).isFallen) {
                garbage.get(i).falling();
            }
        }

        game.batch.draw(background_middle,0,0,w,h);
        game.batch.draw(background_front,0,0,w,h);



        metal_button.draw(game.batch);
        paper_button.draw(game.batch);
        plastic_button.draw(game.batch);
        glass_button.draw(game.batch);


        game.font_trans.draw(game.batch,"Очки: " + score,(int)(w / 25.6),(int)(h / 1.1));
        String str = new DecimalFormat("#0.0").format(time);
        game.font_trans.draw(game.batch,"Время: " + str,(int)(w / 25.6),(int)(h / 1.25));

        if(menu != null){
            menu.draw(game.batch, game.font_speed, game.font_trans);
        }

        if(!Main.slide.isAnimationFinished(slideStateTime)){
            game.batch.draw(Main.slide.getKeyFrame(slideStateTime,false), 0,0,w,h);
            slideStateTime += Gdx.graphics.getDeltaTime();
        }

        game.batch.end();

        for(int i = 0; i < garbage.size(); i++){
            if(garbage.get(i).xPosition > w){
                score -=5;
                garbage.remove(i);
            }
            else if(garbage.get(i).yPosition <= (int)(h / 11)){
                garbage.remove(i);
            }
        }

        if(!isOver){
            time += Gdx.graphics.getDeltaTime();
        }

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
        int x = screenX;
        int y = (int)h - screenY;
        if(!isOver) {
            metal_button.isClicked(x, y, game);
            glass_button.isClicked(x, y, game);
            plastic_button.isClicked(x, y, game);
            paper_button.isClicked(x, y, game);
        }
        if(menu != null){
            if(menu.ok.isTouched(x,y)){
                dispose();
                game.setScreen(new Map(game));
            }
            if(menu.restart.isTouched(x,y)){
                dispose();
                game.setScreen(new FactoryGame(game));
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

    class FactoryButton extends Button{
        long timeLock;
        boolean isOpened;
        public FactoryButton(int width, int height, int xPosition, int yPosition, Texture texture, Texture touched_texture) {
            super(width, height, xPosition, yPosition, texture, touched_texture);
            timeLock = 0;
        }
        @Override
        public void onClick(Main game){
            isOpened = true;
            timeLock = System.currentTimeMillis() + 800;
        }

        public void isClicked(float x, float y, Main game){
            if(timeLock <= System.currentTimeMillis()){
                super.isClicked(x,y, game);
            }
        }

    }
    class Manhole extends Unit{
        Texture texture;
        public Manhole(int width, int height, int xPosition, int yPosition, Texture texture) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
        }

        public void draw(Batch batch){
            batch.draw(texture,xPosition,yPosition,width,height);
        }
    }

}

