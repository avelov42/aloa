package com.avelov.Frontend.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen implements Screen
{
    static private CreditsScreen instance;
    private SpriteBatch batch;
    private BitmapFont font;

    public CreditsScreen()
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
        Gdx.gl.glClearColor(0.2f, 0.2f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Piotr -> Mateusz <- Piotr", Gdx.graphics.getWidth()*0.36f, Gdx.graphics.getHeight()/2);
        batch.end();
    }

    @Override
    public void show()
    {
        System.out.println("Credits 1 show()");
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
        System.out.println("Credits 1 hide()");
    }
}

