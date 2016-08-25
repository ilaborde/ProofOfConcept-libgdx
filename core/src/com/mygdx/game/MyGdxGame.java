package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private Texture img;
	private TiledMap tiledMap;
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;


	//character
	private static final int        FRAME_COLS = 3;         // #1
	private static final int        FRAME_ROWS = 4;         // #2

	Animation walkAnimation;          // #3
	Texture                         walkSheet;              // #4
	TextureRegion[]                 walkFrames;             // #5
	SpriteBatch                     spriteBatch;            // #6
	TextureRegion                   currentFrame;           // #7
	Vector2 position;
	float stateTime;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("firstMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		walkSheet = new Texture(Gdx.files.internal("DwarfSprites.png")); // #9
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/12, walkSheet.getHeight()/8);              // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(1,walkFrames);      // #11
		spriteBatch = new SpriteBatch();                // #12
		stateTime = 0f;
		position = new Vector2(50,50);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		HandleInput();
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
		stateTime += Gdx.graphics.getDeltaTime();           // #15
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position.x, position.y);             // #17
		spriteBatch.end();
	}

	private void HandleInput(){
		//Mover personaje

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			position.x = position.x - 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			position.x = position.x + 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			position.y = position.y + 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			position.y = position.y - 1;
		}
	}

	@Override
	public void dispose () {
		img.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if(keycode == Input.Keys.LEFT){
			camera.translate(-32,0);
		}
		if(keycode == Input.Keys.RIGHT){
			camera.translate(32,0);
		}
		if(keycode == Input.Keys.UP){
			camera.translate(0,-32);
		}

		if(keycode == Input.Keys.DOWN){
			camera.translate(0,32);
		}
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
	public boolean scrolled(int amount) {
		return false;
	}
}
