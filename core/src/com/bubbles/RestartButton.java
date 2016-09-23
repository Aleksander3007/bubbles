package com.bubbles;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RestartButton extends GameEntity {
	public final static AssetDescriptor<Texture> ASSET_DESCRIPTOR = new AssetDescriptor<Texture>("restart-btn.png", Texture.class);
	
	public RestartButton(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}

	public static float WIDTH = 64;
	public static float HEIGHT = 64;
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public float getWidth() {
		return RestartButton.WIDTH;
	}

	@Override
	public float getHeight() {
		return RestartButton.HEIGHT;
	}

	public boolean hit(Vector2 coords) {
		return ((coords.x >= pos_.x) && (coords.x <= (pos_.x + getWidth()))) &&
				((coords.y >= pos_.y) && (coords.y <= (pos_.y + getHeight())));
	}
}
