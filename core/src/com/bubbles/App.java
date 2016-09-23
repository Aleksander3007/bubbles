package com.bubbles;

/**
 * Мысли и развитие.
 * 1. Сделать ачивки - pattern Observer (Наблюдатель).
 * 1.1. Открывать новое за очки (например, какие-нибудь новые  фоны, новые Orbs и т.д.)
 * 2. Контент
 * 3.  3d - прозрачные orbs (надо ли?). - избыточно получается!
 * 4. Кнопка рестарта.
 * 5. Возможно нужен конечный автомат, как минимум два состояни - загрузка и игра.
 * 		Хотя если загрузку вынести (т.к. по сути один экран и весь контент загружается сразу).
 * 		То и состояний не будет.
 * 6. implements interface RenderingObject или class DisplayableObject.
 * 7. Выделение по диагонали?.
 * 8. Отмена действий?
 */

// TODO: Панель SkillsPanel.
// TODO: Переход на чистый Android SDK.
// TODO: Ачивки.
// TODO: Анимация (на нажатие кнопки, на выделение объекта).
// TODO: Звуковое сопровождение действий.
// TODO: Обдумать и создать все типы реальные (скорее всего должны быть разные формы для разного цвета - например, красный - сфера, зеленый - квандрат и т.д.).
// TODO: Описать остальные спец. orb.
// TODO: Игровой баланс.
// TODO: Кнопка exit.


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

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
		// TODO: Должен читаться из файла.
		assets_.load("circles-2.png", Texture.class);
		assets_.load("green-bubble-3.png", Texture.class);
		assets_.load("universal.png", Texture.class);
		assets_.load("red-double-orb.png", Texture.class);
		assets_.load("red-rowsEater-orb.png", Texture.class);
		assets_.load(TouchLine.ASSET_DESCRIPTOR);
		assets_.load(ScoreLabel.ASSET_DESCRIPTOR);
		assets_.load(RestartButton.ASSET_DESCRIPTOR);
	}
}
