package com.bubbles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Orb extends GameEntity {
	public static float WIDTH = 64;
	public static float HEIGHT = 64;
	private OrbType type_;
	private OrbSpecType specType_;
	private int rowNo_;
	private int colNo_;
	
	public Orb(OrbType orbType, OrbSpecType orbSpecType, Vector2 pos, int rowNo, int colNo, TextureRegion texture) {
		this(pos, texture);
		this.type_ = orbType;
		this.specType_ = orbSpecType;
		this.rowNo_ = rowNo;
		this.colNo_ = colNo;
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
		return this.type_;
	}
	
	public void setType(OrbType orbType) {
		this.type_ = orbType;
	}
	
	public OrbSpecType getSpecType() {
		return this.specType_;
	}
	
	@Override
	public void dispose() {
	}
	
	private Orb(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}
}
