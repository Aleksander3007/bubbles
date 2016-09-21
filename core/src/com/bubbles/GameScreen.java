package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen {
	private World world_;
	private ScoreLabel scoreLabel_;
	private Texture restartBtn_;
	//private SkillsPanel skillsPanel; //"Перемешать"("Перетасовка"), "Обратить три случайных в соседний цвет"("Хороший сосед"), "Убрать два нижних ряда"("Пропасть")
	
	public GameScreen(AssetManager assets) {
		world_ = new World(new Vector2(0.0f , 0.0f), assets);
		scoreLabel_ = new ScoreLabel(0, new Vector2(0.0f , 0.0f), assets);
	}
	
	public void dispose() {
		world_.dispose();
	}
	
	public Array<GameEntity> getEntities() {
		Array<GameEntity> entities = new Array<GameEntity>();
		entities.addAll(world_.getEntities());
		entities.addAll(scoreLabel_.getEntities());
		return entities;
	}
	
	public void resize() {
		// TODO: Необходимо создать класс Dimension2D = (width, height).
		world_.resize(SceneRenderer.gameWidth, SceneRenderer.gameHeight - 80f);
	}
	
	public void update() {
		world_.update();
	}
	
	public boolean hit(Vector2 worldCoords) {
		if (world_.hit(worldCoords)) {
    		// TODO: Записываем, что нажатие было на orbsBox.
    		return true;
    	};
    	
    	return false;
	}
	public World getWorld() {
		return world_;
	}
	
	public void touchUp() {
		int profit = world_.profitByOrbs();
		scoreLabel_.setScore(profit);
    	world_.clearSelectedOrbs();
	}

	public void init() {
		world_.init(SceneRenderer.gameWidth, SceneRenderer.gameHeight - 80f);
		scoreLabel_.init();
	}
}
