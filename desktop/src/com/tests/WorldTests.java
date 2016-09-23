package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bubbles.Orb;
import com.bubbles.TouchLine;
import com.bubbles.World;

@RunWith(GdxTestRunner.class)
public class WorldTests {

	AssetManager assets_ = new AssetManager();
	
	@Before
	public void load() {
		assets_.load("circles-2.png", Texture.class);
		assets_.load("green-bubble-3.png", Texture.class);
		assets_.load("universal.png", Texture.class);
		assets_.load("red-double-orb.png", Texture.class);
		assets_.load("red-rowsEater-orb.png", Texture.class);
		assets_.load(TouchLine.ASSET_DESCRIPTOR);
		
		while (!assets_.update()) {}
	}
	
	@After
    public void dispose() {
		assets_.dispose();
    }
	
	@Test
	public void profitZero() {
		World world = new World(new Vector2(0, 0), assets_);
		world.init(3 * Orb.WIDTH, 3 * Orb.HEIGHT);
		int profit = world.getProfitByOrbs();
		assertEquals(0, profit);
	}
}
