package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


import static com.mygdx.game.Icon.loadEnemyCar;
import static com.mygdx.game.Icon.loadPlayerCar;

public class  AdditionalMenu extends Unit{

    Texture texture;
    Button start;
    boolean opened;

    public AdditionalMenu(int width, int height, int xPosition, int yPosition, Texture texture) {
        super(width, height, xPosition, yPosition);
        this.texture = texture;
        opened = false;
        start = new Button((int)(Gdx.graphics.getWidth() / 6.4),(int)(Gdx.graphics.getHeight() / 9.6),xPosition + width - (int)(Gdx.graphics.getWidth() / 5.12), yPosition + Gdx.graphics.getHeight() / 24,new Texture(Gdx.files.internal("Map/buttonMenu.png")));
    }

    public  void draw(Batch batch){
        batch.draw(texture, xPosition, yPosition, width, height);
        start.draw(batch);
    }

    public void buttonDo(Main game){
        //something
    }


    public void close(){
        float x  = Gdx.input.getX();
        float y  = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(opened && (x < xPosition || xPosition + width < x)
                || (y < yPosition || yPosition + height < y)) {
            opened = false;
        }

    }

    class Button extends Unit{

        Texture texture;

        public Button(int width, int height, int xPosition, int yPosition, Texture texture) {
            super(width, height, xPosition, yPosition);
            this.texture = texture;
        }

        public void draw(Batch batch) {
            batch.draw(texture,xPosition,yPosition,width,height);
        }

        public void isTouched(Main game){
            float x  = Gdx.input.getX();
            float y  = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(opened && xPosition <= x && x <= xPosition + width
                    && yPosition <= y && y <= yPosition + height) {
                buttonDo(game);
            }
        }

    }
}
