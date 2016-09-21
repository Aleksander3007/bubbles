package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class World {
	private AssetManager assets_;
	private TextureRegion  touchLineTexture_;
	private ArrayMap<OrbType, TextureRegion> orbTextures_ = new ArrayMap<OrbType, TextureRegion>();
	
	private Vector2 pos_;
	private float width_;
	private float height_;
	private int nRows_;
	private int nColumns_;
	private Array<Orb> orbs_ = new Array<Orb>();
	private Array<Orb> selectedOrbs_ = new Array<Orb>();
	private Array<TouchLine> touchLines_ = new Array<TouchLine>();
	
	/**
	 * Массив всех элементов мира.
	 */
	private Array<GameEntity> gameEntities_ = new Array<GameEntity>();
	
	public World(final Vector2 pos, AssetManager assets) {
		// Тут определяются параметры которые не зависят от внешнего мира.
		this.assets_ = assets;
		this.pos_ = pos;
	}
	
	public void init(final float worldWidth, final float worldHeight) {
		resize(worldWidth, worldHeight);
		initTextures();
		createLevel();
	}
	
	public void resize(final float worldWidth, final float worldHeight) {
		this.nColumns_ = (int) Math.floor(worldWidth / Orb.WIDTH);
		this.nRows_ = (int) Math.floor(worldHeight  / Orb.HEIGHT);
		this.height_ = Orb.WIDTH * this.nRows_;
		this.width_ = Orb.HEIGHT * this.nColumns_;
	}
	
	public boolean hit(Vector2 coords) {
		boolean hitX = (coords.x >= pos_.x) && (coords.x <= pos_.x + width_);
		boolean hitY = (coords.y >= pos_.y) && (coords.y <= pos_.y + height_);
		
		if (hitX && hitY) {
			// Определяем был ли нажат на элемент.
			for (Orb orb : orbs_) {
				if (orb.hit(coords)) {
					if (!isOrbSelected(orb)) {
						
						// TODO:  Уже в Game после отжатия проверяем World.ProfitByOrbs() - здесь проверяется выделены ли объекты одного типа и считается очки за это.
						
						// Если выбран до этого как минимум еще один.
						if (selectedOrbs_.size > 0) {
							Orb prevOrb = selectedOrbs_.peek();

							// Если соседний, то выделяем (защита от нажатий несколькими пальцами в разных местах).
							if ((Math.abs((orb.getColNo() - prevOrb.getColNo())) == 1) ||
									(Math.abs((orb.getRowNo() - prevOrb.getRowNo())) == 1)) {
								selectedOrbs_.add(orb);
								
								Vector2 touchLinePos = getTouchLinePos(prevOrb, orb);
								float touchLineRotationDeg = getTouchLineRotationDeg(prevOrb, orb);
								
								TouchLine touchLine = new TouchLine(touchLinePos, touchLineRotationDeg, touchLineTexture_);
								
								touchLines_.add(touchLine);
								gameEntities_.add((GameEntity)touchLine);
							}
						} 
						else {
							selectedOrbs_.add(orb);
						}
					}
					
					break;
				}
			}
		}
		
		return (hitX && hitY);
	}
	
	public int profitByOrbs() {
		// TODO: Возможно это должно быть в классе GameRules.
		// А лучше GameRule, и GameRuleManager.
		if (selectedOrbs_.size >= 2) {
			OrbType orbType = selectedOrbs_.first().getType();
			for (Orb orb : selectedOrbs_) {
				if (orb.getType() != orbType) {
					return 0;
				}
			}
			// TODO: Конечно тут должен быть более сложный расчет. (Чем больше выделено тем выше ставка).
			return (selectedOrbs_.size * 20); // TODO: Magic number!
		}
		else {
			return 0;
		}
	}
	
	public void update() {
		//....
	}
	
	public void clearSelectedOrbs() {	
		for (TouchLine touchLine : touchLines_) {
			gameEntities_.removeValue((GameEntity)touchLine, true);
		}
		touchLines_.clear();
		selectedOrbs_.clear();
	}
	
	public Array<GameEntity> getEntities() {
		return gameEntities_;
	}
	
	public void dispose () {
		for (Orb orb : orbs_) {
			orb.dispose();
		}
		orbs_.clear();
		
		for (TouchLine touchLine : touchLines_) {
			touchLine.dispose();
		}
		touchLines_.clear();
	}
	
	private boolean isOrbSelected(Orb orb) {
		for (Orb selectedOrb : selectedOrbs_) {
			if (orb == selectedOrb) {
				return true;
			}
		}
		return false;
	}

	private float getTouchLineRotationDeg(Orb prevOrb, Orb orb) {
		// Если выделение идёт снизу вверх.
		if (orb.getY() != prevOrb.getY()) {
			return 90;
		}
		else {
			return 0;
		}
	}

	private Vector2 getTouchLinePos(Orb prevOrb, Orb curOrb) {
		Orb startOrb;
		// Если выделение идёт снизу вверх.
		if (curOrb.getY() != prevOrb.getY()) {
			// Если выделение идёт снизу вверх.
			if (curOrb.getY() > prevOrb.getY()) {
				Gdx.app.log("World.hit", "bottom to top");
				startOrb = prevOrb;
			} 
			// Если выделение идёт сверху вниз.
			else {
				Gdx.app.log("World.hit", "top to bottom");
				startOrb = curOrb;
			}
			
			return new Vector2(
					startOrb.getX() + (Orb.WIDTH / 2) - (TouchLine.WIDTH / 2),
					startOrb.getY() + Orb.HEIGHT  - (TouchLine.HEIGHT / 2)
					);
		} 
		else {
			// Если выделение идёт слево направо.
			if (curOrb.getX() > prevOrb.getX()) {
				Gdx.app.log("World.hit", "left to right");
				startOrb = prevOrb;
			}
			// Если выделение идёт справо налево.
			else {
				Gdx.app.log("World.hit", "right to left");
				startOrb = curOrb;
			}
			
			return new Vector2(
					startOrb.getX() + (Orb.WIDTH / 2),
					startOrb.getY() + (Orb.HEIGHT / 2) - (TouchLine.HEIGHT / 2)
					);
		}
	}

	private void createLevel() {
		OrbType orbType;
		TextureRegion orbTexture;
		for (int iRow = 0; iRow < nRows_; iRow++) {
			for (int iCol = 0; iCol < nColumns_; iCol++) {
				
				orbType = (iRow % 2 == 0) ? OrbType.GREEN : OrbType.YELLOW;
				orbTexture = orbTextures_.get(orbType);
				Vector2 orbPos = new Vector2(
						(float) Orb.WIDTH * iCol,
						(float) Orb.HEIGHT * iRow
						);
				
				Orb orb = new Orb(orbType, orbPos, iCol, iRow, orbTexture);
				orbs_.add(orb);
				gameEntities_.add((GameEntity)orb);
			}
		}
	}

	private void initTextures() {
		/* TODO: Тут можно просто хранить AssetDescriptor для каждого цвета.*/
		orbTextures_.put(OrbType.YELLOW, new TextureRegion(assets_.get("circles-2.png", Texture.class), 0, 0, 256, 256));
		orbTextures_.put(OrbType.RED, new TextureRegion(assets_.get("circles-2.png", Texture.class), 256, 0, 256, 256));
		
		Texture greenTexture = assets_.get("green-bubble-3.png", Texture.class);
		orbTextures_.put(OrbType.GREEN, new TextureRegion(greenTexture, 0, 0, 256, 256));
		
		// TODO: TouchLine.ASSET_DECRIPTOR - вообще всё будет в одном файле. ПОТОМ TouchLine.ASSET_DESCRIPTOR - удалить, добавить общий WORLD_DESCRITOR.
		touchLineTexture_ = new TextureRegion(assets_.get(TouchLine.ASSET_DESCRIPTOR), 0, 0, 256, 64);
	}
}
