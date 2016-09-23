package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen {
	private World world_;
	private ScoreLabel scoreLabel_;
	private RestartButton restartBtn_;
	private AssetManager assets_;
	//private SkillsPanel skillsPanel; //"Перемешать"("Перетасовка"), "Обратить три случайных в соседний цвет"("Хороший сосед"), "Убрать два нижних ряда"("Пропасть")
	
	public GameScreen(AssetManager assets) {
		this.assets_ = assets;
		world_ = new World(new Vector2(0.0f , 0.0f), assets_);
		//scoreLabel_ = new ScoreLabel(0, new Vector2(0.0f, SceneRenderer.gameHeight - 80f), assets_);
	}
	
	public void init() {
		world_.init(SceneRenderer.gameWidth, SceneRenderer.gameHeight - 80f); // TODO: 80 - Magic number!
		scoreLabel_ = new ScoreLabel(0, new Vector2(0.0f, SceneRenderer.gameHeight - 80f), assets_); // TODO: 80 - Magic number!
		scoreLabel_.init();
		restartBtn_ = new RestartButton(new Vector2(
				SceneRenderer.gameWidth - RestartButton.WIDTH,
				SceneRenderer.gameHeight - RestartButton.HEIGHT),
				new TextureRegion(assets_.get(RestartButton.ASSET_DESCRIPTOR), 0, 0, 256, 256) // TODO: 256 - Magic number!
				);
		
	}
	
	public void dispose() {
		world_.dispose();
	}
	
	public Array<GameEntity> getEntities() {
		Array<GameEntity> entities = new Array<GameEntity>();
		entities.addAll(world_.getEntities());
		entities.addAll(scoreLabel_.getEntities());
		entities.add(restartBtn_);
		return entities;
	}
	
	public void resize() {
		// TODO: Необходимо создать класс Dimension2D = (width, height).
		world_.resize(SceneRenderer.gameWidth, SceneRenderer.gameHeight - 80f); // TODO: 80 - Magic number!
	}
	
	public void update() {
		// TODO: Пустой GameScreen.update().
	}
	
	public boolean hit(Vector2 worldCoords) {
		if (world_.hit(worldCoords)) {
    		return true;
    	}
    	
    	return false;
	}
	public World getWorld() {
		return world_;
	}
	
	public void touchUp(Vector2 worldCoords) {
		if (restartBtn_.hit(worldCoords)) {
			Gdx.app.log("GameScreen", "hit restartBtn_");
			world_.createLevel();
			scoreLabel_.setScore(0);
		} else {
			world_.update();
			int profit = world_.getProfitByOrbs();
			if (profit > 0) {
				scoreLabel_.addScore(profit);
				world_.deleteSelectedOrbs();
				world_.removeSelection();
			} 
			else {
				world_.removeSelection();
			}
		}
	}
}
