package com.bubbles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Orb {
	private TextureRegion texture_;
	private Vector2 pos_; // TODO: ѕозици€ должна быть хитрой, т.к. размещаем по кругу.
	private OrbType orbType_;
	
	public Orb(OrbType orbType, Vector2 pos, TextureRegion texture) {
		this.orbType_ = orbType;
		this.pos_ = pos;
		this.texture_ = texture;
	}
	
	public TextureRegion getTexture() {
		return texture_;
	}
	
	public float getX() {
		return this.pos_.x;
	}
	
	public float getY() {
		return this.pos_.y;
	}
}
