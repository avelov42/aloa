package com.avelov;

import com.avelov.Frontend.Screens.MenuScreen;
import com.avelov.Frontend.Screens.ScreenFade;
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
    private OrientationManager.Orientation nextScreenOrientation;
    private ScreenFade fade;

    public Aloa(OrientationManager orientationManager)
    {
        Aloa.orientationManager = orientationManager;
    }

    public void setOrientation(OrientationManager.Orientation orientation)
    {
        orientationManager.setOrientation(orientation);
    }

    @Override
    public void create()
    {
        instance = this;
        assets = new Assets();
        fade = new ScreenFade();
        setScreen(MenuScreen.getInstance(), OrientationManager.Orientation.PORTRAIT);
    }

    public void setScreen(Screen screen, OrientationManager.Orientation orientation)
    {
        if(fade.getState() != ScreenFade.State.Idle)
            return;
        if(currentScreen != null) //typical change
        {
            nextScreen = screen;
            nextScreenOrientation = orientation;
            currentScreen.hide(); // lets tell the screen to unregister its input processor
            fade.start(ScreenFade.State.FadeOut, ScreenFade.FADE_OUT_DURATION); //setting fade-out
        }
        else //occurs only on start of application
        {
            currentScreen = screen;
            orientationManager.setOrientation(OrientationManager.Orientation.PORTRAIT);
            fade.start(ScreenFade.State.FadeIn, ScreenFade.FADE_OUT_DURATION); //setting fade-in
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

        if(fade.getState() == ScreenFade.State.AfterFadeIn) //new screen faded
        {
            currentScreen.show();
            fade.stop();
        }

        if(fade.getState() == ScreenFade.State.AfterFadeOut) //old screen faded out (4)
        {
            currentScreen = nextScreen;
            nextScreen = null;
            fade.stop();
            orientationManager.setOrientation(nextScreenOrientation);
            System.out.println("Call orientation");
            fade.start(ScreenFade.State.FadeIn, ScreenFade.FADE_IN_DURATION); //lets fade in new screen
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