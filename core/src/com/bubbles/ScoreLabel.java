package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
		addScore(score_);
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public float getWidth() {
		return String.valueOf(Math.abs(score_)).length() * ScoreNumber.WIDTH;
	}

	public float getHeight() {
		return ScoreNumber.HEIGHT;
	}

	public void setScore(int score) {
		score_ = score;
		
		numbers_.clear();
		gameEntities_.clear();
		
		if (score_ == 0) {
			Gdx.app.log("ScoreLabel.setScore()", "score = 0");
			ScoreNumber scoreNumber = new ScoreNumber(
					new Vector2(this.pos_.x, this.pos_.y),
					new TextureRegion(assets_.get(ASSET_DESCRIPTOR), 0, 0, 32, 32));
			numbers_.add(scoreNumber);
			gameEntities_.add((GameEntity)scoreNumber);
		}
		
		// Выделяем цифры из числа (и записываем в массив).
		int rest = score_;
		while (rest >= 1) {
			int number = rest % 10;
			rest = rest / 10;
			
			ScoreNumber scoreNumber = new ScoreNumber(
					new Vector2(this.pos_.x, this.pos_.y), // Правильная позиция устанавливается далее.
					new TextureRegion(assets_.get(ASSET_DESCRIPTOR), number * 32, 0, 32, 32));
			
			numbers_.add(scoreNumber);
			gameEntities_.add((GameEntity)scoreNumber);
		}
		
		for (int iNumber = 0; iNumber < numbers_.size; iNumber++) {
			numbers_.get(numbers_.size - iNumber - 1).setPos(
					this.pos_.x + iNumber * ScoreNumber.WIDTH,
					this.pos_.y
					);
		}
	}

	public void addScore(int addScore) {
		setScore(score_ + addScore);
	}
	
	public Array<GameEntity> getEntities() {
		return gameEntities_;
	}
}
