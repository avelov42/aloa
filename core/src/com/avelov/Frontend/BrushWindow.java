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
    Label densityName;
    Slider densitySlider;

    Label sizeTitle;
    Label sizeName;
    Slider sizeSlider;

    Label stateTitle;
    Label stateName;
    Slider stateSlider;

    Label layerTitle;
    Label layerName;
    Slider layerSlider;


    public BrushWindow(final Brush brush)
    {
        super("Awesome brush!", Aloa.assets.skin);
        getTitleLabel().setAlignment(Align.center);
        getTitleTable().pad(12); //@todo use u()
        this.brush = brush;

        //value labels will be updated in update()
        densityTitle = new Label("Density", Aloa.assets.skin);
        densityName = new Label(Float.toString(Math.round(brush.getDensity() * 10000) / 100f) + " %", Aloa.assets.skin);
        densitySlider = new Slider(0, 1, 0.01f, false, Aloa.assets.skin);
        densitySlider.setValue(brush.getDensity()); //start value

        sizeTitle = new Label("Size", Aloa.assets.skin);
        sizeName = new Label(Integer.toString(brush.getSize()), Aloa.assets.skin);
        sizeSlider = new Slider(0, brush.getMaxSize(), 1, false, Aloa.assets.skin);
        sizeSlider.setValue(brush.getSize());


        stateTitle = new Label("State", Aloa.assets.skin);
        stateName = new Label(brush.getCurrentBrushStateName(), Aloa.assets.skin);
        stateSlider = new Slider(0, brush.getLayerBrushStateCount(0) - 1, 1, false, Aloa.assets.skin);
        stateSlider.setValue(brush.getCurrentBrushStateIndex());

        layerTitle = new Label("Layer", Aloa.assets.skin);
        layerName = new Label(brush.getCurrentLayerName(), Aloa.assets.skin);
        layerSlider = new Slider(0, brush.getLayerCount() - 1, 1, false, Aloa.assets.skin);
        layerSlider.setValue(brush.getCurrentLayerIndex());


        setupFrontend();

        layerSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                brush.setLayerByIndex((int) layerSlider.getValue());
                layerName.setText(brush.getCurrentLayerName());

                stateSlider.setRange(0, brush.getLayerBrushStateCount(brush.getCurrentLayerIndex())-1);
                stateSlider.setValue(brush.getCurrentBrushStateIndex());
                stateName.setText(brush.getCurrentBrushStateName());
            }
        });

        stateSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                brush.setBrushStateByIndex((int) stateSlider.getValue());
                stateName.setText(brush.getCurrentBrushStateName());
            }
        });

        sizeSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                brush.setSize((int) sizeSlider.getValue());
                sizeName.setText(Integer.toString(brush.getSize()));
            }
        });

        densitySlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                brush.setDensity(sizeSlider.getValue());
                densityName.setText(Float.toString(Math.round(brush.getDensity() * 10000) / 100f) + " %");
            }
        });


        addListener(new ClickListener()
        {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                brush.startShowcase();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
            {
                brush.stopShowcase();
            }
        });
        //debug();
        setPosition(Gdx.graphics.getWidth() * 0.02f, Gdx.graphics.getHeight() * 0.98f - getHeight());
    }

    public void startDrawing() { brush.startDrawing();}

    public void stopDrawing() { brush.stopDrawing();}

    public Brush getBrush()
    {
        return brush;
    }

    private void setupFrontend()
    {
        //add ux & uy

        add(layerTitle);
        add(layerSlider).padLeft(10);
        add(layerName).expandX().center().padLeft(20).padRight(10);

        row().padTop(10);

        add(stateTitle);
        add(stateSlider).padLeft(10);
        add(stateName).expandX().center().padLeft(20).padRight(10);

        row().padTop(10);

        add(sizeTitle);
        add(sizeSlider).padLeft(10);
        add(sizeName).expandX().center().padLeft(20).padRight(10);

        row().padTop(10);

        add(densityTitle);
        add(densitySlider).padLeft(10);
        add(densityName).expandX().center().padLeft(20).padRight(10);

        pack();

        //find minimum size of the window
        for(int l = 0; l < brush.getLayerCount(); l++)
        {
            brush.setLayerByIndex(l);
            layerName.setText(brush.getCurrentLayerName());
            if(getPrefWidth() > getWidth()) pack();
            for(int b = 0; b < brush.getLayerBrushStateCount(l); b++)
            {
                brush.setBrushStateByIndex(b);
                stateName.setText(brush.getCurrentBrushStateName());
                if(getPrefWidth() > getWidth()) pack();
            }
        }
    }
}
