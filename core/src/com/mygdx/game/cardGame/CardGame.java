package com.mygdx.game.cardGame;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.map.Map;
import com.mygdx.game.Menu;

import java.text.DecimalFormat;


public class CardGame implements InputProcessor, Screen {
	Main game;


	// Graphic
	private Viewport viewport;
	private Camera camera;
	SpriteBatch batch;
	Texture ordinary_card, hammer_card, nail_card,  brick_card, yellow_screwdriver_card , green_screwdriver_card, axe_card;
	Animation<TextureRegion> hammer_flip, nail_flip, brick_flip, yellow_screwdriver_flip, green_screwdriver_flip, axe_flip;

	// Cards logic
	com.mygdx.game.cardGame.Deck deck;

	//Data
	Preferences prefs;

	//Menu
	Menu menu;

	// Other
	final float SCREEN_WIDTH;
	final float SCREEN_HEIGHT;
	float slideStateTime;
	long timeLock;
	int score;
	float time;
	boolean isOver;


	public Animation<TextureRegion> getAnimation(int c, int r, String path){
		Texture tex_animation = new Texture(Gdx.files.internal(path));
		TextureRegion[][] tmp = TextureRegion.split(tex_animation, tex_animation.getWidth() / c, tex_animation.getHeight() / r);
		TextureRegion[] flipFrames = new TextureRegion[c * r];
		int index = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				flipFrames[index++] = tmp[i][j];
			}
		}
		return new Animation<TextureRegion>(1f/70f, flipFrames);
	}


	public CardGame(Main game) {
		this.game = game;
		game.font_trans.setColor(Color.BLACK);

		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();

		time = 0;
		boolean isOver = false;


		hammer_flip = getAnimation(5,4,"card_game/animations/hammer_flip.png");
		brick_flip = getAnimation(5,4,"card_game/animations/brick_flip.png");
		nail_flip = getAnimation(5,4,"card_game/animations/nail_flip.png");
		yellow_screwdriver_flip = getAnimation(5,4,"card_game/animations/yellow_screwdriver_flip.png");
		green_screwdriver_flip = getAnimation(5,4,"card_game/animations/green_screwdriver_flip.png");
		axe_flip = getAnimation(5,4,"card_game/animations/axe_flip.png");


		batch = new SpriteBatch();
		ordinary_card = new Texture(Gdx.files.internal("card_game/main.png"));
		hammer_card = new Texture(Gdx.files.internal("card_game/hammer.png"));
		nail_card = new Texture(Gdx.files.internal("card_game/nail.png"));
		brick_card = new Texture(Gdx.files.internal("card_game/brick.png"));
		yellow_screwdriver_card = new Texture(Gdx.files.internal("card_game/yellow_screwdriver.png"));
		green_screwdriver_card = new Texture(Gdx.files.internal("card_game/green_screwdriver.png"));
		axe_card = new Texture(Gdx.files.internal("card_game/axe.png"));

		deck = new com.mygdx.game.cardGame.Deck((int)(SCREEN_WIDTH / 9.84),(int)(SCREEN_HEIGHT / 3.46));
		deck.sortCards();

		camera = new PerspectiveCamera();
		viewport = new ScreenViewport(camera);

		prefs = Gdx.app.getPreferences("data");

		Gdx.input.setInputProcessor(this);

	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor( 249f, 236f, 177f, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

		if(time >= 60 && !isOver){
			int money = score * (50 + (int)(Math.random() * 100));

			prefs.putInteger("money",prefs.getInteger("money",0) + money);
			prefs.flush();

			String[] results = new String[2];
			results[0] = "Набрано " + score + " очков";
			results[1] = "Заработано " + money + " рублей";
			menu = new Menu((int)(SCREEN_WIDTH / 1.6),(int)(SCREEN_HEIGHT / 1.71), results);

			for(int i = 0; i < deck.getAmountOfCards(); i++){
				deck.getCard(i).setPressed(true);
			}
			isOver = true;

		}

		if(deck.getAmountOfCards() == deck.completed){
			deck.sortCards();
		}


		game.batch.begin();

		for(int i = 0; i < deck.getAmountOfCards(); i++){
			Texture texture = ordinary_card;
			com.mygdx.game.cardGame.Deck.Card card = deck.getCard(i);

			if(deck.checkIsSame(timeLock)){
				score++;
			}


			if(card.isAnimating()){
				Animation.PlayMode playMode;

				if(card.isReverse){
					playMode = Animation.PlayMode.REVERSED;
				}
				else{
					playMode = Animation.PlayMode.NORMAL;
				}

				TextureRegion currentFrame = null;
				Animation<TextureRegion> animation = null;

				switch (card.getType()){
					case 0:
						hammer_flip.setPlayMode(playMode);
						animation = hammer_flip;
						currentFrame = hammer_flip.getKeyFrame(card.stateTime,false);
						break;
					case 1:
						nail_flip.setPlayMode(playMode);
						animation = nail_flip;
						currentFrame = nail_flip.getKeyFrame(card.stateTime,false);
						break;
					case 2:
						brick_flip.setPlayMode(playMode);
						animation = brick_flip;
						currentFrame = brick_flip.getKeyFrame(card.stateTime,false);
						break;
					case 3:
						yellow_screwdriver_flip.setPlayMode(playMode);
						animation = yellow_screwdriver_flip;
						currentFrame = yellow_screwdriver_flip.getKeyFrame(card.stateTime,false);
						break;
					case 4:
						green_screwdriver_flip.setPlayMode(playMode);
						animation = green_screwdriver_flip;
						currentFrame = green_screwdriver_flip.getKeyFrame(card.stateTime, false);
						break;
					case 5:
						axe_flip.setPlayMode(playMode);
						animation = axe_flip;
						currentFrame = axe_flip.getKeyFrame(card.stateTime, false);
						break;
				}

				game.batch.draw(currentFrame, card.getX(), card.getY(), deck.getCards_width(), deck.getCards_height());

				if(animation.isAnimationFinished(card.stateTime) && card.isReverse){
					card.setPressed(false);
					card.setAnimating(false);
					card.isReverse = false;
					card.stateTime = 0;
				}
				else if(animation.isAnimationFinished(card.stateTime)){
					card.setAnimating(false);
					card.stateTime = 0;
				}
				card.stateTime += Gdx.graphics.getDeltaTime();
			}

			if(!deck.ended && card.isPressed() || card.isComplete && !card.isAnimating()){
				switch (card.getType()){
					case 0:
						texture = hammer_card;
						break;
					case 1:
						texture = nail_card;
						break;
					case 2:
						texture = brick_card;
						break;
					case 3:
						texture = yellow_screwdriver_card;
						break;
					case 4:
						texture = green_screwdriver_card;
						break;
					case 5:
						texture = axe_card;
						break;
					default:
						texture = ordinary_card;
						break;
				}
			}

			if(!card.isAnimating()){
				game.batch.draw(texture,card.getX(), card.getY(), deck.getCards_width(), deck.getCards_height());
			}
			if(menu != null){
				menu.draw(game.batch, game.font_speed, game.font_trans);
			}

		}
		game.font_trans.draw(game.batch,"Очки: " + score,(int)(SCREEN_WIDTH / 25.6),(int)(SCREEN_HEIGHT / 1.1));
		String str = new DecimalFormat("#0.0").format(time);
		game.font_trans.draw(game.batch,"Время: " + str,(int)(SCREEN_WIDTH / 25.6),(int)(SCREEN_HEIGHT / 1.25));

		if(!Main.slide.isAnimationFinished(slideStateTime)){
			game.batch.draw(Main.slide.getKeyFrame(slideStateTime,false), 0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
			slideStateTime += Gdx.graphics.getDeltaTime();
		}

		game.batch.end();
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
	public void resume() {
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {
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
		if(deck.opened < 2) {
			float x = screenX;
			float y = Gdx.graphics.getHeight() - screenY;

			if (menu != null) {
				if (menu.ok.isTouched(x, y)) {
					dispose();
					game.setScreen(new Map(game));
				}
				if (menu.restart.isTouched(x, y)) {
					dispose();
					game.setScreen(new CardGame(game));
				}
			}
			if (!isOver) {
				for (int i = 0; i < deck.getAmountOfCards(); i++) {
					Deck.Card card = deck.getCard(i);
					if (!card.isPressed() && !card.isComplete && card.getX() - 1 < x && x < 1 + card.getX() + deck.getCards_width()
							&& card.getY() - 1 < y && y < 1 + card.getY() + deck.getCards_height()) {
						card.setPressed(true);
						card.setAnimating(true);
						deck.opened++;

						if (deck.opened == 2) {
							timeLock = System.currentTimeMillis() + 700;
							card.isLast = true;
						}
					}

				}
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
