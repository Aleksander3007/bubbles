package com.bubbles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GameEntity {
	protected TextureRegion texture_;
	/**
	 * Координаты левого нижнего угла.
	 */
	protected Vector2 pos_;
	/**
	 * По умолчанию - без масштабирования.
	 */
	protected Vector2 scale_ = new Vector2(1, 1);
	/**
	 * Вращение вокруг центра объекта. По умолчанию - без вращения.
	 */
	protected float rotationDeg_ = 0;
	
	public GameEntity(Vector2 pos, TextureRegion texture) {
		this.texture_ = texture;
		this.pos_ = pos;
	}
	
	public GameEntity(Vector2 pos, float rotationDeg, TextureRegion texture) {
		this.texture_ = texture;
		this.pos_ = pos;
		this.rotationDeg_ = rotationDeg;
	}
	
	public TextureRegion getTexture() {
		return texture_;
	}
	
	public abstract void dispose();
	
	public float getX() {
		return this.pos_.x;
	}
	
	public float getY() {
		return this.pos_.y;
	}
	
	public float getScaleX() {
		return this.scale_.x;
	}
	
	public float getScaleY() {
		return this.scale_.y;
	}
	
	public float getRotationDeg() {
		return this.rotationDeg_;
	}
	
	public abstract float getWidth();
	public abstract float getHeight();
}
