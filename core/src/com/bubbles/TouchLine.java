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

	public TouchLine(Orb orb1, Orb orb2, TextureRegion texture) {
		super(new Vector2(0.0f, 0.0f), 0.0f, texture);
		
		this.pos_ = calculatePos(orb1, orb2);
		this.rotationDeg_ =  calculateRotationDeg(orb1, orb2);
	}
	
	@Override
	public void dispose() {
		// TODO: Auto-generated method stub
	}

	@Override
	public float getWidth() {
		return TouchLine.WIDTH; /* (Orb.WIDTH / 2) + (Orb.WIDTH / 2) */
	}

	@Override
	public float getHeight() {
		return TouchLine.HEIGHT;
	}
	
	private Vector2 calculatePos(Orb orb1, Orb orb2) {
		Orb startOrb;
		// Если выделение идёт снизу вверх.
		if (orb2.getY() != orb1.getY()) {
			// Если выделение идёт снизу вверх.
			if (orb2.getY() > orb1.getY()) {
				Gdx.app.log("World.hit", "bottom to top");
				startOrb = orb1;
			} 
			// Если выделение идёт сверху вниз.
			else {
				Gdx.app.log("World.hit", "top to bottom");
				startOrb = orb2;
			}
			
			return new Vector2(
					startOrb.getX() + (Orb.WIDTH / 2) - (TouchLine.WIDTH / 2),
					startOrb.getY() + Orb.HEIGHT  - (TouchLine.HEIGHT / 2)
					);
		} 
		else {
			// Если выделение идёт слево направо.
			if (orb2.getX() > orb1.getX()) {
				Gdx.app.log("World.hit", "left to right");
				startOrb = orb1;
			}
			// Если выделение идёт справо налево.
			else {
				Gdx.app.log("World.hit", "right to left");
				startOrb = orb2;
			}
			
			return new Vector2(
					startOrb.getX() + (Orb.WIDTH / 2),
					startOrb.getY() + (Orb.HEIGHT / 2) - (TouchLine.HEIGHT / 2)
					);
		}
	}
	
	private float calculateRotationDeg(Orb orb1, Orb orb2) {
		// Если выделение идёт снизу вверх.
		if (orb2.getY() != orb1.getY()) {
			return 90;
		}
		else {
			return 0;
		}
	}
}
