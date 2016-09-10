package com.avelov.Frontend.Tables;


import com.avelov.Aloa;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuTable extends DynamicTable
{
    public MenuTable()
    {
        TextButton newSimulationButton = new TextButton("New simulation", Aloa.assets.skin);
        newSimulationButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().pushTable(new com.avelov.Frontend.Tables.AutomatonChoiceTable(com.avelov.Frontend.Tables.AutomatonChoiceTable.Mode.New));
            }
        });
        TextButton savedSimulationsButton = new TextButton("Saved simulations", Aloa.assets.skin);
        savedSimulationsButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().pushTable(new com.avelov.Frontend.Tables.AutomatonChoiceTable(com.avelov.Frontend.Tables.AutomatonChoiceTable.Mode.Load));
            }
        });
        TextButton infoButton = new TextButton("Info", Aloa.assets.skin);
        infoButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().pushTable(new com.avelov.Frontend.Tables.InfoTable(Aloa.assets.aloaAbout));
            }
        });
        TextButton exitButton = new TextButton("Exit", Aloa.assets.skin);
        exitButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
            }
        });

        float top = uy(0), left = ux(80), bottom = uy(42), right = ux(80);
        add(newSimulationButton).expand().fill().space(top, left, bottom, right).row();
        add(savedSimulationsButton).expand().fill().space(top, left, bottom, right).row();
        add(infoButton).expand().fill().space(top, left, bottom, right).row();
        add(exitButton).expand().fill().space(top, left, bottom, right).row();
    }

    private float ux(float millage)
    {
        return MenuScreen.ux(millage);
    }
    private float uy(float millage)
    {
        return MenuScreen.uy(millage);
    }

    @Override
    public void refresh()
    {
        //nothing dynamic here
    }
}
