package com.avelov.Frontend;

import com.avelov.Aloa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class BrushWindow extends Window
{
    private Brush brush;

    Label densityTitle;
    Label densityValue;
    Slider densitySlider;

    Label sizeTitle;
    Label sizeValue;
    Slider sizeSlider;

    Label stateTitle;
    Label stateValue;
    Slider stateSlider;

    public void update(boolean forceSliderValues)
    {
        if(forceSliderValues)
        {
            brush.setDensity(densitySlider.getValue());
            brush.setSize((int) sizeSlider.getValue());
            brush.setStateID((int) stateSlider.getValue());
        }


        densityValue.setText(Float.toString(Math.round(brush.getDensity() * 100) / (float) 100));
        densitySlider.setValue(brush.getDensity());

        sizeValue.setText(Integer.toString(brush.getSize()));
        sizeSlider.setValue(brush.getSize());
        sizeSlider.setRange(sizeSlider.getMinValue(), brush.getMaxSize());

        stateValue.setText(brush.getCurrentState().getDescription());
        stateSlider.setValue(brush.getCurrentStateID());

    }

    private void setMaxWidth()
    {
        for(int i = 0; i < brush.getStatesCount(); i++)
        {
            stateValue.setText(brush.getState(i).getDescription());
            if(getPrefWidth() > getWidth())
                pack();
        }
    }

    public BrushWindow(Brush brush)
    {
        super("Awesome brush!", Aloa.assets.skin);
        getTitleLabel().setAlignment(Align.center);
        getTitleTable().pad(12); //@todo use u()
        this.brush = brush;

        //value labels will be updated in update()
        densityTitle = new Label("Density", Aloa.assets.skin);
        densityValue = new Label("", Aloa.assets.skin); //@todo .2413?
        densitySlider = new Slider(0, 1, 0.01f, false, Aloa.assets.skin);
        densitySlider.setValue(brush.getDensity()); //start value

        sizeTitle = new Label("Size", Aloa.assets.skin);
        sizeValue = new Label("", Aloa.assets.skin);
        sizeSlider = new Slider(0, brush.getMaxSize(), 1, false, Aloa.assets.skin);
        sizeSlider.setValue(brush.getSize());

        stateTitle = new Label("State", Aloa.assets.skin);
        stateValue = new Label("", Aloa.assets.skin);
        stateSlider = new Slider(0, brush.getStatesCount() - 1, 1, false, Aloa.assets.skin);
        stateSlider.setValue(brush.getCurrentStateID());

        setUp();
        setMaxWidth();

        update(true);

        addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                update(true);
            }
        });

        addListener(new ClickListener()
        {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                startModifying();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
            {
                stopModifying();
            }
        });
        //debug();
        setPosition(Gdx.graphics.getWidth() * 0.02f, Gdx.graphics.getHeight() * 0.98f - getHeight());
    }

    private void startModifying()
    {
        brush.startModifying();
    }

    private void stopModifying()
    {
        brush.stopModifying();
    }

    public void startDrawing() { brush.startDrawing();}

    public void stopDrawing() { brush.stopDrawing();}

    public Brush getBrush()
    {
        return brush;
    }

    private void setUp()
    {
        add(sizeTitle);
        add(sizeSlider).padLeft(10);
        add(sizeValue).expandX().center().padLeft(20).padRight(10);

        row().padTop(10);

        add(stateTitle);
        add(stateSlider).padLeft(10);
        add(stateValue).expandX().center().padLeft(20).padRight(10);

        row().padTop(10);

        add(densityTitle);
        add(densitySlider).padLeft(10);
        add(densityValue).expandX().center().padLeft(20).padRight(10);

        //top().left();
        pack();
    }
}
