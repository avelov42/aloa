package com.avelov.Frontend.Screens;
import com.avelov.Aloa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class that is rendering a alpha black rectangle.
 * It is both capable of rendering fade in and fade out.
 *
 * After construction, ScreenFade is in state Idle.
 * It is the only state that allows you to start fader.
 * While starting two states can be used as an argument:
 * State.FadeIn and State.FadeOut.
 *
 * When transition is done, ScreenFade will be in state
 * State.AfterFadeIn or State.AfterFadeOut, accordingly
 * to start state.
 *
 * You should call stop() before calling start(), because
 * it re-establishes State.Idle state.
 */
public class ScreenFade
{
    public static final long FADE_OUT_DURATION = 404;
    public static final long FADE_IN_DURATION = 404;

    public enum State
    {
        FadeIn,
        FadeOut,
        AfterFadeIn,
        AfterFadeOut,
        Idle
    }
    private State state;
    private long startTimestamp;
    private long duration;
    private ShapeRenderer shape;
    private Viewport viewport;

    public ScreenFade()
    {
        shape = Aloa.assets.shape;
        viewport = new ScreenViewport();
        state = State.Idle;
    }

    public void start(State state, long duration)
    {
        if(this.state == State.Idle && (state == State.FadeIn || state == State.FadeOut))
        {
            this.startTimestamp = TimeUtils.millis();
            this.state = state;
            this.duration = duration;
        }
        else if(state != State.FadeIn && state != State.FadeOut)
            throw new IllegalStateException("Cannot set ScreenFade to state " + state.toString());
        else
            throw new IllegalStateException("Cannot start working ScreenFade");
    }

    public void stop()
    {
        this.state = State.Idle;
    }

    public State getState()
    {
        return state;
    }

    public void render()
    {
        if(state != State.Idle)
        {
            float alpha = 0.42f; //calming down android studio
            float advance = (float) (TimeUtils.millis() - startTimestamp) / duration;
            if(advance >= 1) advance = 1f;
            if(state == State.FadeOut) alpha = advance;
            if(state == State.FadeIn) alpha = 1 - advance;
            if(advance == 1f) //previously set to 1f
            {
                if(state == State.FadeIn) state = State.AfterFadeIn;
                if(state == State.FadeOut) state = State.AfterFadeOut;
            }
            //draw
            Gdx.gl20.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape.begin(ShapeRenderer.ShapeType.Filled); //called in aloa
            viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            viewport.apply(true);
            shape.setProjectionMatrix(viewport.getCamera().combined);
            shape.setColor(0f, 0f, 0f, alpha);
            shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shape.end();
        }
    }
}