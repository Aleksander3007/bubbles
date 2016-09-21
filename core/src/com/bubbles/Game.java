package com.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Описывает всю игровую логику (по сути -  тонкий controller из MVC).
 */
public class Game extends InputAdapter/* TODO: Возможно необходимо вынести в отделный клас и по pattern Subcribe */{
	private GameScreen screen_;
	
	
	// TODO: Этого тут не должно быть.
	public OrthographicCamera  camera_;
	
	public Game(GameScreen screen) {	
		this.screen_ = screen;
	}
	
	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
    	// Получаем координаты нажатия в мировой системе координат.
    	Vector3 worldCoords = camera_.unproject(new Vector3(screenX, screenY, 0.0f));
    	
    	// TODO: По идеи везде hit и необходимо передавать Action.
    	screen_.hit(new Vector2(worldCoords.x, worldCoords.y));
    			
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
    	// TODO: По идеи везде hit и необходимо передавать Action.
    	screen_.touchUp();
        return true;
    }

    @Override
    public boolean scrolled(int amount)
    {
    	Gdx.app.log("Input Event", "scrolled");
        return false;
    }
    
    public void dispose () {
    	
    }
    
    public void update() {
    	// TODO: Пока здесь ничего не обновляется, т.к. всё ожидает действий от пользователя.
    }
}
