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
	public OrthographicCamera  camera_; // TODO: Должен быть закрытым.
	private Viewport viewport_;
	
	public static float gameWidth = 640;
	public static float gameHeight = 480;

	private World world_;
	
	public SceneRenderer(World world) {
		
		this.world_ = world;
		
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
		for (GameEntity gameEntity : world_.getEntities()) {
			if (gameEntity != null) {
				spriteBatch_.draw(gameEntity.getTexture(),
						gameEntity.getX(),
						gameEntity.getY(),
						gameEntity.getWidth() / 2, // относительно x-координаты; Вращение вокруг точки  = (x + originX);
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
        
		Gdx.app.log("screenWidth", Float.toString(screenWidth));
        Gdx.app.log("screenHeight", Float.toString(screenHeight));
        Gdx.app.log("gameHeight", Float.toString(gameHeight));
	}
	
	public void dispose () {
		spriteBatch_.dispose();
	}
}
