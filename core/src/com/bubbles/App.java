package com.bubbles;

/**
 * 1. Сделать ачивки - pattern Observer (Наблюдатель).
 * 2. Контент
 * 3. Взаимодействие пользователя с игрой
 * 4.  3d - прозрачные orbs (надо ли?). - избыточно получается!
 * 5. Отрисовка линии при движении.
 * 6. Кнопка рестарта.
 * 7. Возможно нужен конечный автомат, как минимум два состояни - загрузка и игра.
 * 		Хотя если загрузку вынести (т.к. по сути один экран и весь контент загружается сразу).
 * 		То и состояний не будет.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

/*
 * Resolution[] resolutions = {
                new Resolution(240, 320, "240x320"),
                new Resolution(320, 480, "320x480"),
                new Resolution(480, 800, "480x800") 
        };
        ResolutionFileResolver resolver = 
                new ResolutionFileResolver(new InternalFileHandleResolver(), 
                                           resolutions);
        mAssets = new AssetManager();
        mAssets.setLoader(Skin.class, new SkinLoader(resolver));
        mAssets.load("data/skins/SimpleSkin", Skin.class);
 */
public class App extends ApplicationAdapter {
	private World world_;
	private Game game_;
	private SceneRenderer renderer_;
	private AssetManager assets_;
	
	@Override
	public void create () {
		try {
			assets_ = new AssetManager();
			
			world_ = new World(new Vector2(0.0f , 0.0f), assets_);
			game_ = new Game(world_);
			renderer_ = new SceneRenderer(world_);
			game_.camera_ = renderer_.camera_;
			
			Gdx.input.setInputProcessor(game_);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	@Override
	public void render () {
		try {
			game_.update();
			world_.update();
			renderer_.render();
			//Gdx.app.log("GameScreen FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	public void resize (int width, int height) {
		renderer_.resize(width, height);
	}

    public void pause () {
    	// TODO: App.pause() Не реализовано.
    }

    public void resume () {
    	// TODO: App.resume() Не реализовано.
    }
    
	@Override
	public void dispose () {
		renderer_.dispose();
		game_.dispose();
	}
}
