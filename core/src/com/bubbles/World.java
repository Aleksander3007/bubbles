package com.bubbles;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

public class World {
	private AssetManager assets_;
	private TextureRegion  touchLineTexture_;
	private ArrayMap<OrbType, ArrayMap<OrbSpecType, TextureRegion>> orbTextures_ = 
			new ArrayMap<OrbType, ArrayMap<OrbSpecType, TextureRegion>>();
	
	private Vector2 pos_;
	private float width_;
	private float height_;
	private int nRows_;
	private int nColumns_;
	private Orb[][] orbs_;
	private Array<Orb> selectedOrbs_ = new Array<Orb>();
	private Array<TouchLine> touchLines_ = new Array<TouchLine>();
	
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
			for (int iRow = 0; iRow < nRows_; iRow++) {
				for (int iCol = 0; iCol < nColumns_; iCol++) {
					if (hitOrb(orbs_[iRow][iCol], coords))
						return true;
				}
			}
		}
		
		return (hitX && hitY);
	}

	public int getProfitByOrbs() {
		// TODO: Возможно это должно быть в классе GameRules.
		// А лучше GameRule, и GameRuleManager.
		if (selectedOrbs_.size < 2) {
			return 0;
		}
		
		int profit = 0;
		int factor = 1;
		OrbType orbType = selectedOrbs_.first().getType();
		for (Orb orb : selectedOrbs_) {
			if ((orb.getType() == orbType) || (orb.getType() == OrbType.UNIVERSAL) || (orbType == OrbType.UNIVERSAL)) {
				profit += 10;
			} 
			// Все элементы должны быть одинакового OrbType.
			else {
				return 0;
			}
			
			switch (orb.getSpecType()) {
			case DOUBLE: {
				factor *= 2;
				break;
			}
			case TRIPLE: {
				factor *= 3;
				break;
			}
			case ROWS_EATER: {
				// Элементы должны тоже считаться.
				break;
			}
			default:
				// В остальных случаях ничего не делаем.
				break;
			}
			
			orbType = orb.getType();
		}
		
		return (profit * factor);
	}
	
	public void update() {
		if (selectedOrbs_.size < 2) {
			return;
		}
		
		// Ищем спец. Orbs.
		for (Orb orb : selectedOrbs_) {
			switch (orb.getSpecType()) {
			case ROWS_EATER: {
				// Добавляем все элементы строки как выделенные. 
				for (int iCol = 0; iCol < nColumns_; iCol++) {
					// ... без повтора в массиве.
					if (!selectedOrbs_.contains(orbs_[orb.getRowNo()][iCol], true)) {
						// ... и делаем их уникальным, чтобы считался Profit и для них.
						orbs_[orb.getRowNo()][iCol].setType(OrbType.UNIVERSAL);
						selectedOrbs_.add(orbs_[orb.getRowNo()][iCol]);
					}
				}
			}
			default:
				// В остальных случаях ничего не делаем.
				break;
			}
		}
	}
	
	public void removeSelection() {
		touchLines_.clear();
		selectedOrbs_.clear();
	}
	
	/**
	 * Удалить выделенные Orbs.
	 */
	public void deleteSelectedOrbs() {
		// Определяем верхних соседей.
		for (Orb orb : selectedOrbs_) {
			for (int iRow = orb.getRowNo() + 1; iRow < nRows_; iRow++) {
				// Опускаем все элементы колонки на клетку ниже.
				createOrb(orbs_[iRow][orb.getColNo()].getType(), 
						orbs_[iRow][orb.getColNo()].getSpecType(),
						iRow - 1, orb.getColNo());
			}
			
			// На пустую верхную часть генерируем новые.
			createOrb(nRows_ - 1, orb.getColNo());
		}
	}
	
	public Array<GameEntity> getEntities() {
		Array<GameEntity> gameEntities = new Array<GameEntity>();
		for (int iRow = 0; iRow < nRows_; iRow++) {
			for (int iCol = 0; iCol < nColumns_; iCol++) {
				gameEntities.add(orbs_[iRow][iCol]);
			}
		}
		gameEntities.addAll(touchLines_);
		return gameEntities;
	}
	
	public void dispose () {
		clearOrbs();
		
		for (TouchLine touchLine : touchLines_) {
			touchLine.dispose();
		}
		touchLines_.clear();
	}
	
	public void createLevel() {
		clearOrbs();

		orbs_ = new Orb[nRows_][nColumns_];
		for (int iRow = 0; iRow < nRows_; iRow++) {
			for (int iCol = 0; iCol < nColumns_; iCol++) {
				createOrb(iRow, iCol);
			}
		}
		
		Gdx.app.log("World", "Level is created.");
	}
	
	private OrbType generateOrbType() {
		// TODO: Подумать где дожна находится карта вероятностей выпадения. (GameRuler?)
		ArrayMap<OrbType, Float> orbTypeProbabilities = new ArrayMap<OrbType, Float>();
		orbTypeProbabilities.put(OrbType.YELLOW, 32f);
		orbTypeProbabilities.put(OrbType.RED, 32f);
		orbTypeProbabilities.put(OrbType.GREEN, 32f);
		orbTypeProbabilities.put(OrbType.UNIVERSAL, 4f);
		
		return GameMath.generateValue(orbTypeProbabilities);
	}
	
	private OrbSpecType generateOrbSpecType() {
		// TODO: Подумать где дожна находится карта вероятностей выпадения. (GameRuler?)
		ArrayMap<OrbSpecType, Float> orbTypeProbabilities = new ArrayMap<OrbSpecType, Float>();
		orbTypeProbabilities.put(OrbSpecType.NONE, 90f);
		orbTypeProbabilities.put(OrbSpecType.DOUBLE, 0f);
		orbTypeProbabilities.put(OrbSpecType.TRIPLE, 0f);
		orbTypeProbabilities.put(OrbSpecType.AROUND_EATER, 0f);
		orbTypeProbabilities.put(OrbSpecType.ROWS_EATER, 10f);
		orbTypeProbabilities.put(OrbSpecType.COLUMNS_EATER, 0f);
		
		return GameMath.generateValue(orbTypeProbabilities);
	}
	
	private void clearOrbs() {
		if (orbs_ != null)
		{
			for (int iRow = 0; iRow < nRows_; iRow++) {
				for (int iCol = 0; iCol < nColumns_; iCol++) {
					orbs_[iRow][iCol].dispose();
					orbs_[iRow][iCol] = null;
				}
			}
		}
	}
	
	private boolean hitOrb(Orb orb, Vector2 coords) {
		if (orb.hit(coords)) {
			if (!isOrbSelected(orb)) {
				// Если выбран до этого как минимум еще один.
				if (selectedOrbs_.size > 0) {
					Orb prevOrb = selectedOrbs_.peek();

					// Если соседний, то выделяем (защита от нажатий несколькими пальцами в разных местах).
					if ((Math.abs((orb.getColNo() - prevOrb.getColNo())) == 1) ||
							(Math.abs((orb.getRowNo() - prevOrb.getRowNo())) == 1)) {
						selectedOrbs_.add(orb);
						TouchLine touchLine = new TouchLine(prevOrb, orb, touchLineTexture_);
						touchLines_.add(touchLine);
					}
				} 
				else {
					selectedOrbs_.add(orb);
				}
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isOrbSelected(Orb orb) {
		for (Orb selectedOrb : selectedOrbs_) {
			if (orb == selectedOrb) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Создать Orb.
	 * @param rowNo Номер строки.
	 * @param colNo Номер столбца.
	 */
	private void createOrb(final int rowNo, final int colNo) {
		OrbType orbType = generateOrbType();
		OrbSpecType orbSpecType = generateOrbSpecType();
		createOrb(orbType, orbSpecType, rowNo, colNo);
	}

	/**
	 * Создать Orb.
	 * @param orbType Тип.
	 * @param orbSpecType Специальный тип.
	 * @param rowNo Номер строки.
	 * @param colNo Номер столбца.
	 */
	private void createOrb(final OrbType orbType, final OrbSpecType orbSpecType, final int rowNo, final int colNo) {
		TextureRegion orbTexture = orbTextures_.get(orbType).get(orbSpecType);
		Vector2 orbPos = new Vector2(
				(float) Orb.WIDTH * colNo,
				(float) Orb.HEIGHT * rowNo
				);

		orbs_[rowNo][colNo] = new Orb(orbType, orbSpecType, orbPos, rowNo, colNo, orbTexture);
	}

	private void initTextures() {
		try {
			// TODO: В отдельный класс?
			XmlReader xmlReader = new XmlReader();
		    XmlReader.Element root = xmlReader.parse(Gdx.files.internal("orbsTextures.xml"));
		    Gdx.app.log("World.initTextures()", "root.childCount = " + Integer.toString(root.getChildCount()));
		    for (int iOrbs = 0; iOrbs < root.getChildCount(); iOrbs++) {
		    	XmlReader.Element xmlOrb = root.getChild(iOrbs);
		    	Gdx.app.log("World.initTextures()", "root.child.orbType = " + xmlOrb.getAttribute("orbType"));
		    	Gdx.app.log("World.initTextures()", "root.child.orbSpecType = " + xmlOrb.getAttribute("orbSpecType"));
		    	
		    	OrbType orbType = OrbType.valueOf(xmlOrb.getAttribute("orbType"));
		    	OrbSpecType orbSpecType = OrbSpecType.valueOf(xmlOrb.getAttribute("orbSpecType"));
		    	XmlReader.Element xmlOrbTexture = xmlOrb.getChildByName("texture");
		    	
		    	int orbTextureX = Integer.parseInt(xmlOrbTexture.getAttribute("x"));
		    	int orbTextureY = Integer.parseInt(xmlOrbTexture.getAttribute("y"));
		    	int orbTextureWidth = Integer.parseInt(xmlOrbTexture.getAttribute("width"));
		    	int orbTextureHeight = Integer.parseInt(xmlOrbTexture.getAttribute("height"));
		    	
		    	TextureRegion orbTexture = new TextureRegion(
		    			assets_.get(xmlOrbTexture.getAttribute("path"), Texture.class), 
		    			orbTextureX, orbTextureY, 
		    			orbTextureWidth, orbTextureHeight);
		    	//orbSpecTypeTextures = new ArrayMap<OrbSpecType, TextureRegion>();
		    	//orbTextures_.put(orbType, );
		    	if (orbTextures_.get(orbType) != null) {
		    		orbTextures_.get(orbType).put(orbSpecType, orbTexture);
		    	}
		    	else {
		    		ArrayMap<OrbSpecType, TextureRegion> orbSpecTypeTextures = new ArrayMap<OrbSpecType, TextureRegion>();
		    		orbSpecTypeTextures.put(orbSpecType, orbTexture);
		    		orbTextures_.put(orbType, orbSpecTypeTextures);
		    	}
		    }
		    
		    touchLineTexture_ = new TextureRegion(assets_.get(TouchLine.ASSET_DESCRIPTOR), 0, 0, 256, 64);
		    Gdx.app.log("World", "File with textures of orbs is loaded.");
		}
		catch (IOException e) {
			 Gdx.app.error("World", "File with textures of orbs is NOT loaded.");
			e.printStackTrace();
		}
	}
}
