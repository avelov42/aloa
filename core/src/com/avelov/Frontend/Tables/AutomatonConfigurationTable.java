package com.avelov.Frontend.Tables;

import com.avelov.Aloa;
import com.avelov.Center.Files.AutomatonDescription;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by avelov on 9/10/16.
 */
public class AutomatonConfigurationTable extends DynamicTable
{
    public AutomatonConfigurationTable(AutomatonDescription selected)
    {
        Table configTable = new Table();
        Label automatonName = new Label(selected.toString(), Aloa.assets.skin.get("title", Label.LabelStyle.class));
        automatonName.setAlignment(Align.center);

        Label coloringLabel = new Label("Color theme:", Aloa.assets.skin);
        final SelectBox<String> coloringChoice = new SelectBox<>(Aloa.assets.skin);
        coloringChoice.setItems("Nice", "Colorful", "Autumn", "Midnight"); //@todo write real fetch

        Label boundaryLabel = new Label("Boundary behaviour:", Aloa.assets.skin);
        final SelectBox<String> boundaryChoice = new SelectBox<>(Aloa.assets.skin);
        boundaryChoice.setItems("Wrap", "Mirror", "Constant value"); //@todo write real fetch

        Label sizeLabel = new Label("Size:", Aloa.assets.skin);
        Label configLabel = new Label("Config string:", Aloa.assets.skin);
        final TextField sizeInput = new TextField("", Aloa.assets.skin);
        final TextField configInput = new TextField("", Aloa.assets.skin);

        ImageButton nextButton = Aloa.assets.makeButton("right-arrow");
        ImageButton backButton = Aloa.assets.makeButton("left-arrow");
        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().popTable();
            }
        });
        nextButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                //todo write logic of invoking gamescreen
            }
        });


        final int columns = 2;

        configTable.top();
        configTable.add(automatonName).colspan(2).fill().padBottom(uy(40)).row();
        configTable.add(coloringLabel).colspan(columns).fill().expandX().padBottom(uy(10)).row();
        configTable.add(coloringChoice).colspan(columns).fill().expandX().padBottom(uy(32)).row();
        configTable.add(boundaryLabel).colspan(columns).fill().expandX().padBottom(uy(10)).row();
        configTable.add(boundaryChoice).colspan(columns).fill().expandX().padBottom(uy(32)).row();
        configTable.add(sizeLabel).expandX().center();
        configTable.add(sizeInput).fill().expandX().pad(uy(20)).row();
        configTable.add(configLabel).expandX().center();
        configTable.add(configInput).fill().expandX().pad(uy(20)).row();

        add(configTable).expand().fill().colspan(2).padBottom(20).row();
        add(backButton).left();
        add(nextButton).right();

        //outerTable.debug();

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
        //nothing to refresh
    }
}
