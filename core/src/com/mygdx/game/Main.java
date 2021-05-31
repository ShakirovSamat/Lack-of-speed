package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;



public class Main extends Game {
   public SpriteBatch batch;
   public BitmapFont font;
   public BitmapFont font_speed, font_trans;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/speedometer.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(Gdx.graphics.getHeight() / 12);
        String chars = "йцукенгшщзъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\\\/?-+=()*&.;:,{}\\\"´`'<>";
        parameter.characters = chars;
        font_speed = generator.generateFont(parameter);
        parameter.size = (int)(Gdx.graphics.getHeight() / 20.5);
        font_trans = generator.generateFont(parameter);
        this.setScreen(new Map(this));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void dispose() {

    }
}
