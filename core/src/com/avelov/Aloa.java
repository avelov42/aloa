package com.avelov;

import com.avelov.screens.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

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
    private com.avelov.screens.ScreenFade fade;

    public Aloa(OrientationManager orientationManager)
    {
        Aloa.orientationManager = orientationManager;
    }

    @Override
    public void create()
    {
        instance = this;
        assets = new Assets();
        fade = new com.avelov.screens.ScreenFade();
        setScreen(MenuScreen.getInstance());
    }

    public void setScreen(Screen screen)
    {
        if(fade.getState() != com.avelov.screens.ScreenFade.State.Idle)
            return;
        if(currentScreen != null) //typical change
        {
            nextScreen = screen;
            currentScreen.hide(); // lets tell the screen to unregister its input processor
            fade.start(com.avelov.screens.ScreenFade.State.FadeOut, com.avelov.screens.ScreenFade.FADE_OUT_DURATION); //setting fade-out
        }
        else //occurs only on start of application
        {
            currentScreen = screen;
            fade.start(com.avelov.screens.ScreenFade.State.FadeIn, com.avelov.screens.ScreenFade.FADE_OUT_DURATION); //setting fade-in
        }
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentScreen.render(Gdx.graphics.getDeltaTime());

        assert currentScreen != null: "No current screen assigned";

        fade.render();

        if(fade.getState() == com.avelov.screens.ScreenFade.State.AfterFadeIn) //new screen faded
        {
            currentScreen.show();
            fade.stop();
        }

        if(fade.getState() == com.avelov.screens.ScreenFade.State.AfterFadeOut) //old screen faded out (4)
        {
            currentScreen = nextScreen;
            nextScreen = null;
            fade.stop();
            fade.start(com.avelov.screens.ScreenFade.State.FadeIn, com.avelov.screens.ScreenFade.FADE_IN_DURATION); //lets fade in new screen
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
        //@todo dispose all screen singletons (or move it to assets)
    }
}