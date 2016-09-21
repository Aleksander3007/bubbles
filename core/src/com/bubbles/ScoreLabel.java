package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class ScoreLabel {
	public final static AssetDescriptor<Texture> ASSET_DESCRIPTOR = new AssetDescriptor<Texture>("numbers.png", Texture.class);
	
	private AssetManager assets_; 
	private Array<ScoreNumber> numbers_ = new Array<ScoreNumber>();
	private Array<GameEntity> gameEntities_ = new Array<GameEntity>();
	private Vector2 pos_;
	
	private int score_;
	
	public ScoreLabel(final int startScore, final Vector2 pos, final AssetManager assets) {
		this.assets_ = assets;
		this.pos_ = pos;
		score_ = startScore;
	}
	
	public void init() {
		setScore(score_);
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public float getWidth() {
		return String.valueOf(Math.abs(score_)).length() * 32;
	}

	public float getHeight() {
		return 32;
	}

	public void setScore(int addScore) {
		score_ += addScore;

		int rest = score_;
		if (rest > 1) {
			int number = rest % 10;
			rest = rest / 10;
			
			ScoreNumber scoreNumber = new ScoreNumber(
					new Vector2(this.pos_.x, this.pos_.y),
					new TextureRegion(assets_.get(ASSET_DESCRIPTOR), number * 32, 0, 32, 32));
			
			numbers_.add(scoreNumber);
			gameEntities_.add(scoreNumber);
		}
	}
	
	public Array<GameEntity> getEntities() {
		return gameEntities_;
	}
}
