package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * ќписывает всю игровую логику (по сути -  controller из MVC).
 */
public class Game extends InputAdapter/* TODO: ¬озможно необходимо вынести в отделный клас и по pattern Subcribe */{
	private World world_;
	
	
	// TODO: Ётого тут не должно быть.
	public OrthographicCamera  camera_;
	
	public Game(World world) {	
		this.world_ = world;
	}
	
	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
		// ѕолучаем координаты нажати€ в мировой системе координат.
		Vector3 worldCoords = camera_.unproject(new Vector3(screenX, screenY, 0.0f));
		
		if (world_.hit(new Vector2(worldCoords.x, worldCoords.y))) {
    		// TODO: «аписываем, что нажатие было на orbsBox.
    		return true;
    	};
    	
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
    	// ѕолучаем координаты нажати€ в мировой системе координат.
    	Vector3 worldCoords = camera_.unproject(new Vector3(screenX, screenY, 0.0f));
    	
    	if (world_.hit(new Vector2(worldCoords.x, worldCoords.y))) {
    		// TODO: «аписываем, что нажатие было на orbsBox.
    		return true;
    	};
    			
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
    	Gdx.app.log("Input Event", "touchUp");
    	world_.clearSelectedOrbs();
        return true;
    }

    @Override
    public boolean scrolled(int amount)
    {
    	Gdx.app.log("Input Event", "scrolled");
        return false;
    }
    
    public void dispose () {
    	world_.dispose();
    }
    
    public void update() {
    	// TODO: ѕока здесь ничего не обновл€етс€, т.к. всЄ ожидает действий от пользовател€.
    }
}
