package com.bubbles;

/**
 * 1. Сделать ачивки - pattern Observer (Наблюдатель).
 * 1.1. Открывать новое за очки (например, какие-нибудь новые  фоны, новые Orbs и т.д.)
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
import com.badlogic.gdx.graphics.Texture;
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
	private GameScreen screen_;
	private Game game_;
	private SceneRenderer renderer_;
	private AssetManager assets_;
	
	// Надо вводить конечный автомат. Все должно зависит от состояния игрового.
	private boolean isLoading_;
	private boolean isInitialized_;
	
	@Override
	public void create () {
		try {
			isLoading_ = false;
			isInitialized_ = false;
			
			assets_ = new AssetManager();
			loadContentAsync();
			
			screen_ = new GameScreen(assets_);
			game_ = new Game(screen_);
			renderer_ = new SceneRenderer(screen_);
			game_.camera_ = renderer_.camera_;

			Gdx.input.setInputProcessor(game_);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.error("App.create", ex.getMessage());
			Gdx.app.exit();
		}
	}
	
	@Override
	public void render () {
		try {
			doneLoading();
			
			if (!isLoading_) {
				
				if (!isInitialized_) {
					screen_.init();
					isInitialized_ = true;
				}
				
				game_.update();
				screen_.update();
				renderer_.render();
			}
			//Gdx.app.log("GameScreen FPS", Integer.toString(Gdx.graphics.getFramesPerSecond()));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.error("App.render", ex.getMessage());
			Gdx.app.exit();
		}
	}
	
	public void resize (int width, int height) {
		renderer_.resize(width, height);
		screen_.resize();
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
		screen_.dispose();
	}
	
	private void doneLoading()
	{
		if (isLoading_) {
			isLoading_ = assets_.update() ? false : true;
		}
	}
	
	private void loadContentAsync() {
		isLoading_ = true;
		// TODO: Вообще надо вынести все, т.к. у нас сразу все текстуры прогружаются (т.к. по сути один экран).
		assets_.load("circles-2.png", Texture.class);
		assets_.load(TouchLine.ASSET_DESCRIPTOR);
		assets_.load("green-bubble-3.png", Texture.class);
		assets_.load(ScoreLabel.ASSET_DESCRIPTOR);
	}
}
