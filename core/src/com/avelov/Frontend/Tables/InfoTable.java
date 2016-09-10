package com.avelov.Frontend.Tables;

import com.avelov.Aloa;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class InfoTable extends DynamicTable
{
    public InfoTable(String text)
    {
        Label infoLabel = new Label(text, Aloa.assets.skin);
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.topLeft);

        Table labelTable = new Table();
        labelTable.add(infoLabel).expand().fill().pad(uy(20), ux(20), uy(20), ux(20));

        ScrollPane scrollPane = new ScrollPane(labelTable, Aloa.assets.skin);
        add(scrollPane).expand().fill().padBottom(uy(20)).row();
        ImageButton backButton = Aloa.assets.makeButton("left-arrow");
        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().popTable();
            }
        });
        add(backButton).fill();
    }

    @Override
    public void refresh()
    {
        //do nothing, to change text create new InfoTable
    }

    private float ux(float millage)
    {
        return MenuScreen.ux(millage);
    }
    private float uy(float millage)
    {
        return MenuScreen.uy(millage);
    }


}
