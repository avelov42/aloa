package com.avelov;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class that is rendering a alpha black rectangle.
 * It is both capable of rendering fade in and fade out.
 */
public class ScreenFade
{
    public static final long FADE_OUT_DURATION = 400;
    public static final long FADE_IN_DURATION = 400;

    public enum Mode
    {
        FadeIn,
        FadeOut,
        Done
    }
    private Mode mode;
    private Mode prevMode;
    private long startTimestamp;
    private long duration;
    private ShapeRenderer shape;
    private Viewport viewport;

    public ScreenFade()
    {
        mode = Mode.Done;
        shape = Aloa.assets.shape;
        viewport = new ScreenViewport();
    }

    public void start(Mode mode, long duration)
    {
        startTimestamp = TimeUtils.millis();
        prevMode = this.mode;
        this.mode = mode;
        this.duration = duration;
    }

    public boolean done()
    {
        return mode == Mode.Done;
    }

    public Mode getJustFinishedState()
    {
        Mode rv = prevMode;
        prevMode = Mode.Done;
        return rv;
    }

    public void render()
    {
        if(mode != Mode.Done)
        {
            //initializing alpha to random value cuz AS says it may be uninitialized, somehow.
            float alpha = 0.42f; //calming down android studio
            float advance = (float) (TimeUtils.millis() - startTimestamp) / duration;
            if(mode == Mode.FadeOut) alpha = advance;
            if(mode == Mode.FadeIn) alpha = 1 - advance;
            if(advance >= 1)
            {
                prevMode = mode;
                mode = Mode.Done;
            }

            //draw
            Gdx.gl20.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            //shape.begin(ShapeRenderer.ShapeType.Filled); //called in aloa
            viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            viewport.apply(true);
            shape.setProjectionMatrix(viewport.getCamera().combined);
            shape.setColor(0f, 0f, 0f, alpha);
            shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //shape.end();
        }
    }
}