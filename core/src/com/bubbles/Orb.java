package com.bubbles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Orb extends GameEntity {
	public static float WIDTH = 64;
	public static float HEIGHT = 64;
	private OrbType orbType_;
	private int rowNo_;
	private int colNo_;
	
	public Orb(OrbType orbType, Vector2 pos, int colNo, int rowNo, TextureRegion texture) {
		this(pos, texture);
		this.orbType_ = orbType;
		this.colNo_ = colNo;
		this.rowNo_ = rowNo;
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
		return Orb.HEIGHT;
	}
	
	public int getColNo() {
		return this.colNo_;
	}
	
	public int getRowNo() {
		return this.rowNo_;
	}

	public OrbType getType() {
		return this.orbType_;
	}
	
	@Override
	public void dispose() {
	}
	
	private Orb(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}
}
