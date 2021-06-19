package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.defeatWindow.DefeatWindow;
import com.mygdx.game.menu.GameMenu;
import com.mygdx.game.victoryWindow.VictoryWindow;


public class Main extends Game {
   public SpriteBatch batch;
   public BitmapFont font;
   public BitmapFont font_speed, font_trans;

   public static Sound buttonPressed;

    static int w,h;
    public static Animation<TextureRegion> slide;

    @Override
    public void create() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        slide = getAnimation(5,4,"animations/slide_animation.png", 20f);

        buttonPressed = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonPressed.mp3"));
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
        this.setScreen(new GameMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void dispose() {

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
