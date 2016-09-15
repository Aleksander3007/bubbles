package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class OrbsBox {
	private Array<Orb> orbs_ = new Array<Orb>();
	
	public OrbsBox(final int nRows, final int nColumns, ArrayMap<OrbType, TextureRegion> orbTextures) {
		OrbType orbType;
		TextureRegion orbTexture;
		for (int iRow = 0; iRow < nRows; iRow++) {
			for (int iCol = 0; iCol < nColumns; iCol++) {
				
				orbType = (iRow % 2 == 0) ? OrbType.YELLOW : OrbType.RED;
				orbTexture = orbTextures.get(orbType);
				Vector2 orbPos = new Vector2(
						(float) orbTexture.getRegionWidth() * iCol,
						(float) orbTexture.getRegionHeight() * iRow
						);
				
				orbs_.add(new Orb(orbType, orbPos, orbTexture));
			}
		}
	}
	
	public Array<Orb> getOrbs() {
		return orbs_;
	}
}
