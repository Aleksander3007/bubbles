package com.bubbles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
	private SceneRenderer renderer_;
	
	private ArrayMap<OrbType, TextureRegion> orbTextures_ = new ArrayMap<OrbType, TextureRegion>();
	private OrbsBox orbsBox_;
	
	private final int TEXTURE_SIZE_ = 64;
	
	@Override
	public void create () {
		try {
			// TODO: Добавить Assets и из него получать тектуры.
			Texture orbsTextures = new Texture("circles.png");
			orbTextures_.put(OrbType.YELLOW, new TextureRegion(orbsTextures, 0, 0, TEXTURE_SIZE_, TEXTURE_SIZE_));
			orbTextures_.put(OrbType.RED, new TextureRegion(orbsTextures, TEXTURE_SIZE_, 0, TEXTURE_SIZE_, TEXTURE_SIZE_));
			Gdx.app.log("Info", "Textures are loaded.");
			
			int nRows = (int) (Gdx.graphics.getHeight() / TEXTURE_SIZE_);
			int nColumns = (int) (Gdx.graphics.getWidth() / TEXTURE_SIZE_);
			orbsBox_ = new OrbsBox(nRows, nColumns, orbTextures_);
			renderer_ = new SceneRenderer(orbsBox_);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			Gdx.app.exit();
		}
	}

	@Override
	public void render () {
		try {
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
	}
}
