package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * ќтрисовка всего (по сути - View из MVC).
 */
public class SceneRenderer {
	private SpriteBatch spriteBatch_;
	public OrthographicCamera  camera_; // TODO: ƒолжен быть закрытым.
	private Viewport viewport_;
	
	public static float gameWidth = 480;
	public static float gameHeight = 640;

	private GameScreen screen_;
	
	public SceneRenderer(GameScreen screen) {
		
		this.screen_ = screen;
		
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        
        if (screenHeight < screenWidth)
        	gameWidth = screenWidth / (screenHeight / gameHeight);
        else
        	gameHeight = screenHeight / (screenWidth / gameWidth);
        
        Gdx.app.log("screenWidth", Float.toString(screenWidth));
        Gdx.app.log("screenHeight", Float.toString(screenHeight));
        Gdx.app.log("gameHeight", Float.toString(gameHeight));
        
		camera_ = new OrthographicCamera();
        viewport_ = new ExtendViewport(/*gameWidth_, gameHeight_*/ gameWidth, gameHeight, camera_);
        viewport_.apply(true);
        
        spriteBatch_ = new SpriteBatch();
	}
	
	public void render () {
		camera_.update();

		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch_.setProjectionMatrix(camera_.combined);
		spriteBatch_.begin();

		
		for (GameEntity gameEntity : screen_.getEntities()) {
			if (gameEntity != null) {
				spriteBatch_.draw(gameEntity.getTexture(),
						gameEntity.getX(),
						gameEntity.getY(),
						gameEntity.getWidth() / 2, // относительно x-координаты; ¬ращение вокруг точки  = (x + originX);
						gameEntity.getHeight() / 2, // относительно y-координаты;
						gameEntity.getWidth(),
						gameEntity.getHeight(),
						gameEntity.getScaleX(),
						gameEntity.getScaleY(),
						gameEntity.getRotationDeg()
						);
			}
		}
		spriteBatch_.end();
	}
	
	public void resize (int width, int height) {
		Gdx.app.log("Info", "resize");

		viewport_.update(width, height);
	    camera_.position.set(camera_.viewportWidth / 2, camera_.viewportHeight / 2, 0);

	    float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();      
        //gameHeight_ = screenHeight / (screenWidth / gameWidth_);
        
		Gdx.app.log("screenWidthPix", Float.toString(screenWidth));
        Gdx.app.log("screenHeightPix", Float.toString(screenHeight));
        Gdx.app.log("screenWidthInch", Float.toString(screenWidth / Gdx.graphics.getPpiX()));
        Gdx.app.log("screenHeightInch", Float.toString(screenHeight  / Gdx.graphics.getPpiY()));
        Gdx.app.log("gameHeight", Float.toString(gameHeight));
	}
	
	public void dispose () {
		spriteBatch_.dispose();
	}
}
