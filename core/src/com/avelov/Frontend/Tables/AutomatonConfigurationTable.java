package com.avelov.Frontend.Tables;

import com.avelov.Aloa;
import com.avelov.Center.BrushState;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.Layer;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avelov on 9/10/16.
 */
public class AutomatonConfigurationTable extends DynamicTable
{
    private List<Layer> layers;
    private int currentLayer = 0;
    private Label layerLabel;

    final SelectBox<String> boundaryChoice;
    //layer dependents

    final SelectBox<AutomatonInfo.TinterDetails> coloringChoice;
    final SelectBox<BrushState> constantValueChoice;


    private void incrementCurrentLayer()
    {
        currentLayer = (currentLayer+1) % layers.size();
        updateLayerDependents();
    }
    private void decrementCurrentLayer()
    {
        currentLayer = currentLayer-1 < 0 ? layers.size()-1 : currentLayer-1;
        updateLayerDependents();
    }

    private void updateLayerDependents()
    {
        layerLabel.setText(layers.get(currentLayer).getName());
        coloringChoice.setItems(layers.get(currentLayer).getTinters().toArray(new AutomatonInfo.TinterDetails[layers.get(currentLayer).getBrushStates().size()]));
        constantValueChoice.setItems(layers.get(currentLayer).getBrushStates().toArray(new BrushState[layers.get(currentLayer).getBrushStates().size()]));
        constantValueChoice.setSelected(layers.get(currentLayer).getDefState());
        constantValueChoice.setVisible(boundaryChoice.getSelected().equals("Constant value"));
    }


    public AutomatonConfigurationTable(AutomatonInfo selected)
    {
        Table configTable = new Table();
        Label automatonName = new Label(selected.getName(), Aloa.assets.skin.get("title", Label.LabelStyle.class));
        automatonName.setAlignment(Align.center);


        Label boundaryLabel = new Label("Boundary behaviour:", Aloa.assets.skin);
        boundaryChoice = new SelectBox<>(Aloa.assets.skin);
        boundaryChoice.setItems("Constant value", "Mirror", "Same", "Wrap");


        layers = selected.getLayers();


        ImageButton previousLayerButton = Aloa.assets.makeButton("left-arrow");
        ImageButton nextLayerButton = Aloa.assets.makeButton("right-arrow");
        layerLabel = new Label("", Aloa.assets.skin);
        previousLayerButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                decrementCurrentLayer();
            }
        });
        nextLayerButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                incrementCurrentLayer();
            }
        });


        Label coloringLabel = new Label("Layer color theme:", Aloa.assets.skin);

        coloringChoice = new SelectBox<>(Aloa.assets.skin);
        constantValueChoice = new SelectBox<>(Aloa.assets.skin);

        updateLayerDependents();

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
        //configTable.add(boundaryLabel).colspan(columns).fill().expandX().padBottom(uy(10)).row();
       // configTable.add(boundaryChoice).colspan(columns).fill().expandX().padBottom(uy(32)).row();
        configTable.add(sizeLabel).expandX().center();
        configTable.add(sizeInput).fill().expandX().pad(uy(20)).row();
        configTable.add(configLabel).expandX().center();
        configTable.add(configInput).fill().expandX().pad(uy(20)).row();

        add(configTable).expand().fill().colspan(2).padBottom(20).row();
        add(backButton).left().width(ux(MenuScreen.BUTTON_SIZE)).height(uy(MenuScreen.BUTTON_SIZE));;
        add(nextButton).right().width(ux(MenuScreen.BUTTON_SIZE)).height(uy(MenuScreen.BUTTON_SIZE));;

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
