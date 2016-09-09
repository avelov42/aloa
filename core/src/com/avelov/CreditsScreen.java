package com.avelov;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen implements Screen
{
    static private CreditsScreen instance;
    private SpriteBatch batch;
    private BitmapFont font;

    public void CreditsScreen()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    static public CreditsScreen getInstance()
    {
       return instance == null ? instance = new CreditsScreen() : instance;
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Piotr -> Mateusz <- Piotr", 200, 200);
        batch.end();
    }

    @Override
    public void show()
    {

    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }
}

