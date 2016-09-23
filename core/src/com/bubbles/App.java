package com.bubbles;

/**
 * ����� � ��������.
 * 1. ������� ������ - pattern Observer (�����������).
 * 1.1. ��������� ����� �� ���� (��������, �����-������ �����  ����, ����� Orbs � �.�.)
 * 2. �������
 * 3.  3d - ���������� orbs (���� ��?). - ��������� ����������!
 * 4. ������ ��������.
 * 5. �������� ����� �������� �������, ��� ������� ��� �������� - �������� � ����.
 * 		���� ���� �������� ������� (�.�. �� ���� ���� ����� � ���� ������� ����������� �����).
 * 		�� � ��������� �� �����.
 * 6. implements interface RenderingObject ��� class DisplayableObject.
 * 7. ��������� �� ���������?.
 * 8. ������ ��������?
 */

// TODO: ������ SkillsPanel.
// TODO: ������� �� ������ Android SDK.
// TODO: ������.
// TODO: �������� (�� ������� ������, �� ��������� �������).
// TODO: �������� ������������� ��������.
// TODO: �������� � ������� ��� ���� �������� (������ ����� ������ ���� ������ ����� ��� ������� ����� - ��������, ������� - �����, ������� - �������� � �.�.).
// TODO: ������� ��������� ����. orb.
// TODO: ������� ������.
// TODO: ������ exit.


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
	
	// ���� ������� �������� �������. ��� ������ ������� �� ��������� ��������.
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
    	// TODO: App.pause() �� �����������.
    }

    public void resume () {
    	// TODO: App.resume() �� �����������.
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
		// TODO: ������ �������� �� �����.
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
