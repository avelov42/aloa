package com.avelov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/* General list
@todo Choose production font and scale it properly (let it replace loaded font in skin)
@todo Try to use other skins than shade
 */

/**
 * Aloa main class performs few basic tasks:
 *
 * 1) Initializes objects like assets, watchdog, etc. (varying in successive builds)
 * 2) Handles changing screens and transitions between them
 * 3) Delegates render, pause, resume, dispose to existing screens (current and next screen)
 */
public class Aloa extends ApplicationAdapter
{
    static public Aloa instance;
    static public Assets assets;
    static public OrientationManager orientationManager;
    private Screen currentScreen;
    private Screen nextScreen;
    private ScreenFade fade;

    public Aloa(OrientationManager orientationManager)
    {
        Aloa.orientationManager = orientationManager;
    }

    @Override
    public void create()
    {
        instance = this;
        assets = new Assets();
        fade = new ScreenFade();
        setScreen(CreditsScreen.getInstance());
    }

    public void setScreen(Screen screen)
    {
        if(nextScreen != null)
            return; //prevent from interfering screen change
        if(currentScreen != null) //typical change
        {
            nextScreen = screen;
            currentScreen.hide(); // lets tell the screen to unregister its input processor
            fade.start(ScreenFade.Mode.FadeOut, ScreenFade.FADE_OUT_DURATION); //setting fade-out
        }
        else //occurs only on start of application
        {
            currentScreen = screen;
            fade.start(ScreenFade.Mode.FadeIn, ScreenFade.FADE_OUT_DURATION); //setting fade-in
        }
    }

    public Screen getScreen()
    {
        return currentScreen;
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentScreen.render(Gdx.graphics.getDeltaTime());

        if(Gdx.input.justTouched())
        {
            if(getScreen() == CreditsScreen.getInstance())
                setScreen(CreditsScreen2.getInstance());
            if(getScreen() == CreditsScreen2.getInstance())
                setScreen(CreditsScreen.getInstance());
        }


        assets.shape.begin(ShapeRenderer.ShapeType.Filled);
        handleTransition();
        assets.shape.end();
    }

    private void handleTransition()
    {
        assert currentScreen != null;
        fade.render();
        ScreenFade.Mode justFinishedState = fade.getJustFinishedState();
        if(fade.done() && justFinishedState == ScreenFade.Mode.FadeOut) //old screen faded out (4)
        {
            currentScreen.dispose();
            currentScreen = nextScreen;
            nextScreen = null;
            fade.start(ScreenFade.Mode.FadeIn, ScreenFade.FADE_IN_DURATION); //lets fade in new screen
        }
        if(fade.done() && justFinishedState == ScreenFade.Mode.FadeIn) //new screen faded
        {
            currentScreen.show();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        if(currentScreen != null) currentScreen.resize(width, height);
        if(nextScreen != null) nextScreen.resize(width, height);
    }

    @Override
    public void pause()
    {
        if(currentScreen != null) currentScreen.pause();
        if(nextScreen != null) nextScreen.pause();
    }

    @Override
    public void resume()
    {
        if(currentScreen != null) currentScreen.resume();
        if(nextScreen != null) nextScreen.resume();
    }

    @Override
    public void dispose()
    {
        assets.dispose();
        if(currentScreen != null) currentScreen.dispose();
        if(nextScreen != null) nextScreen.dispose();
    }
}