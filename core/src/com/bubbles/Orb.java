package com.bubbles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Orb extends GameEntity {
	public final static float WIDTH = 64;
	public final static float HEIGHT = 64;
	private OrbType orbType_;
	
	public Orb(OrbType orbType, Vector2 pos, TextureRegion texture) {
		this(pos, texture);
		this.orbType_ = orbType;
	}
	
	public boolean hit(Vector2 coords) {
		return ((coords.x >= pos_.x) && (coords.x <= (pos_.x + Orb.WIDTH))) &&
				((coords.y >= pos_.y) && (coords.y <= (pos_.y + Orb.HEIGHT)));
	}
	
	@Override
	public float getWidth() {
		return Orb.WIDTH;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return Orb.HEIGHT;
	}

	@Override
	public void dispose() {
	}
	
	private Orb(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}
}
