package com.avelov.Frontend;

import com.avelov.Aloa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by adamek on 4/25/16.
 */
public class SpeedWindow extends Window
{

    private Slider slider;
    private Label label;
    private SpeedMeter speedMeter;

    public SpeedWindow(SpeedMeter speedMeter)
    {
        super("Hayoish speed slider!", Aloa.assets.skin);
        getTitleLabel().setAlignment(Align.center);
        getTitleTable().pad(12); //@todo use u()
        this.speedMeter = speedMeter;

        slider = new Slider(0, 100, 1, false, Aloa.assets.skin);
        slider.setValue(speedMeter.getSpeed());
        label = new Label(Integer.toString(Math.round(slider.getValue())) + "%", Aloa.assets.skin);
        slider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                label.setText(Integer.toString(Math.round(slider.getValue())) + "%");
                updateMeterFromSlider();
            }
        });

        add(slider).pad(12).width(Gdx.graphics.getWidth() * 0.3f).height(Gdx.graphics.getHeight()*0.02f);
        add(label).pad(12);
        pack();
        setPosition(Gdx.graphics.getWidth() * 0.5f - getWidth() / 2, Gdx.graphics.getHeight() * 0.98f - getHeight());

    }


    private void updateMeterFromSlider()
    {
        speedMeter.setSpeed((int) slider.getValue());
    }

    private void updateSliderFromMeter()
    {
        slider.setValue(speedMeter.getSpeed());
        label.setText(Integer.toString(Math.round(slider.getValue())) + "%");
    }

    public void resume()
    {
        speedMeter.resume();
        updateSliderFromMeter();
    }

    public void pause()
    {
        speedMeter.pause();
        updateSliderFromMeter();
    }

    public boolean isPaused()
    {
        return speedMeter.isPaused();
    }

    public boolean shouldMakeStep()
    {
        return speedMeter.shouldMakeStep();
    }
}
