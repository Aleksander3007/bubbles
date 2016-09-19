package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TouchLine extends GameEntity{

	public final static AssetDescriptor<Texture> ASSET_DESCRIPTOR = new AssetDescriptor<Texture>("touch-line.png", Texture.class);
	public final static float WIDTH = Orb.WIDTH;
	public final static float HEIGHT = (Orb.WIDTH / 4);
	
	public TouchLine(Vector2 pos, TextureRegion texture) {
		super(pos, texture);
	}
	
	public TouchLine(Vector2 pos, float rotationDeg, TextureRegion texture) {
		super(pos, rotationDeg, texture);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getWidth() {
		return TouchLine.WIDTH; /* (Orb.WIDTH / 2) + (Orb.WIDTH / 2) */
	}

	@Override
	public float getHeight() {
		return TouchLine.HEIGHT;
	}
	
}
