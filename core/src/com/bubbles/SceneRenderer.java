package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Отрисовка всего (по сути - View из MVC).
 */
public class SceneRenderer {
	private SpriteBatch spriteBatch_;
	private OrthographicCamera  camera_;
	private Viewport viewport_;
	
	public static float gameWidth = 640;
	public static float gameHeight = 480;
	
	private OrbsBox orbsBox_;
	
	public SceneRenderer(OrbsBox orbsBox) {
		
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();      
        gameHeight = screenHeight / (screenWidth / gameWidth);
        
        Gdx.app.log("screenWidth", Float.toString(screenWidth));
        Gdx.app.log("screenHeight", Float.toString(screenHeight));
        Gdx.app.log("gameHeight", Float.toString(gameHeight));
        
		camera_ = new OrthographicCamera();
        viewport_ = new ExtendViewport(/*gameWidth_, gameHeight_*/ screenWidth, screenHeight, camera_);
        viewport_.apply();
        camera_.position.set(camera_.viewportWidth / 2, camera_.viewportHeight / 2, 0);
        
        spriteBatch_ = new SpriteBatch();
        
        this.orbsBox_ = orbsBox;
	}
	
	public void render () {
		camera_.update();
		 
		// TODO: Необходимо определять размеры экрана.
		// TODO: Размеры шаров должны зависить от размера экрана, т.к. на экране всегда должно помещаться опред. число шаров.
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch_.setProjectionMatrix(camera_.combined); //camera.projection
		//spriteBatch_.setTransformMatrix(camera_.view);
		spriteBatch_.begin();

		// TODO: Нужен алгоритм определения позиций начальных и последующих.
		// TODO: Нужен алгоритм отрисовки по вх. массиву готовых orbs.
		//Vector2 center = new Vector2((gameWidth_ / 2 - 64), (gameHeight_ / 2 - 64));
		//Vector2 center = new Vector2(0, 0);
		for (Orb orb : orbsBox_.getOrbs()) {
			spriteBatch_.draw(orb.getTexture(),
					orb.getX(),
					orb.getY()
					);
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
        
		Gdx.app.log("screenWidth", Float.toString(screenWidth));
        Gdx.app.log("screenHeight", Float.toString(screenHeight));
        Gdx.app.log("gameHeight", Float.toString(gameHeight));
	}
	
	public void dispose () {
		spriteBatch_.dispose();
	}
}
