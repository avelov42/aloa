package com.avelov.Frontend.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by avelov on 9/9/16.
 */
public class CreditsScreen2 implements Screen
{
    static private CreditsScreen2 instance;
    private SpriteBatch batch;
    private BitmapFont font;

    public CreditsScreen2()
    {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    static public CreditsScreen2 getInstance()
    {
        return instance == null ? instance = new CreditsScreen2() : instance;
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
        Gdx.gl.glClearColor(1f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "It's Credits Screen 2 now!", Gdx.graphics.getWidth()*0.36f, Gdx.graphics.getHeight()/2);
        batch.end();
    }

    @Override
    public void show()
    {
        System.out.println("Credits2 show()");
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
        System.out.println("Credits 2 hide()");
    }
}
