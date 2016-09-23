package com.bubbles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ScoreNumber extends GameEntity {
	public static float WIDTH = 32;
	public static float HEIGHT = 32;
	
	public ScoreNumber(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public float getWidth() {
		return WIDTH;
	}

	@Override
	public float getHeight() {
		return HEIGHT;
	}

	public void setPos(final float x, final float y) {
		this.pos_.x = x;
		this.pos_.y = y;
	}
}
