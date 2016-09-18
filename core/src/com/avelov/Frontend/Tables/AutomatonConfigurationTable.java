package com.avelov.Frontend.Tables;

import com.avelov.Aloa;
import com.avelov.Center.BrushState;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.Layer;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

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
    Label constantBoundaryValueLabel;


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
//        coloringChoice.setItems(new AutomatonInfo.TinterDetails(null, "/jakisfejkowy/path/mateusznapraw.coloring", false));
        coloringChoice.setItems(layers.get(currentLayer).getTinters().toArray(new AutomatonInfo.TinterDetails[layers.get(currentLayer).getTinters().size()]));
        constantValueChoice.setItems(layers.get(currentLayer).getBrushStates().toArray(new BrushState[layers.get(currentLayer).getBrushStates().size()]));
        constantValueChoice.setSelected(layers.get(currentLayer).getDefState());
//        constantValueChoice.setItems(new BrushState(new float[3], "ASD"), new BrushState(new float[3], "RPiS"));
        constantValueChoice.setVisible(boundaryChoice.getSelected().equals("Constant value"));
        constantBoundaryValueLabel.setVisible(boundaryChoice.getSelected().equals("Constant value"));

    }


    @Override
    public void act(float delta)
    {
        super.act(delta);
        updateLayerDependents(); //@todo please solve it in a better way
    }

    public AutomatonConfigurationTable(AutomatonInfo selected)
    {
        Table automatonConfig = new Table(), layerConfig = new Table();
        Label automatonName = new Label(selected.getName(), Aloa.assets.skin.get("title", Label.LabelStyle.class));
        automatonName.setAlignment(Align.center);


        Label boundaryLabel = new Label("Boundary behaviour:", Aloa.assets.skin);
        boundaryChoice = new SelectBox<>(Aloa.assets.skin);
        boundaryChoice.setItems("Constant value", "Mirror", "Same", "Wrap");


        layers = selected.getLayers();


        ImageButton previousLayerButton = Aloa.assets.makeButton("left-arrow");
        ImageButton nextLayerButton = Aloa.assets.makeButton("right-arrow");
        layerLabel = new Label("KOTEK", Aloa.assets.skin.get("title", Label.LabelStyle.class));
        layerLabel.setAlignment(Align.center);
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
        constantBoundaryValueLabel = new Label("Constant boundary value: ", Aloa.assets.skin);

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



        final int automatonColumns = 2;

//        automatonConfig.debug();
        automatonConfig.top();
        automatonConfig.add(automatonName).colspan(automatonColumns).fill().spaceBottom(uy(32)).row();

        automatonConfig.add(boundaryLabel).colspan(automatonColumns).fill().expandX().spaceBottom(uy(10)).row();
        automatonConfig.add(boundaryChoice).colspan(automatonColumns).fill().expandX().spaceBottom(uy(16)).row();

        automatonConfig.add(sizeLabel).expandX().center().space(uy(20));
        automatonConfig.add(sizeInput).fill().expandX().space(uy(20)).row();
        automatonConfig.add(configLabel).expandX().center().space(uy(20));
        automatonConfig.add(configInput).fill().expandX().space(uy(20)).row();


        final int layerColumns = 3;

//        layerConfig.debug();
        //ayerConfig.top();
        layerConfig.add(previousLayerButton).fill().width(MenuScreen.BUTTON_SIZE_X/6).height(MenuScreen.BUTTON_SIZE_Y/6).padBottom(uy(24));
        layerConfig.add(layerLabel).fill().expand().center().padBottom(uy(24)).padLeft(ux(12)).padRight(ux(12));
        layerConfig.add(nextLayerButton).fill().width(MenuScreen.BUTTON_SIZE_X/6).height(MenuScreen.BUTTON_SIZE_Y/6).padBottom(uy(24)).row();
        layerConfig.add(coloringLabel).colspan(layerColumns).fill().expandX().padBottom(uy(10)).row();
        layerConfig.add(coloringChoice).colspan(layerColumns).fill().expandX().padBottom(uy(32)).row();
        layerConfig.add(constantBoundaryValueLabel).colspan(layerColumns).fill().expandX().padBottom(uy(10)).row();
        layerConfig.add(constantValueChoice).colspan(layerColumns).fill().expandX().row();

        add(automatonConfig).expand().fill().colspan(2).padBottom(uy(32)).row(); //todo wtf, why 100 is needed?
        add(layerConfig).expand().fill().colspan(2).padBottom(uy(20)).row();

//        debug();

        add(backButton).fill().left().width(ux(MenuScreen.BUTTON_SIZE_X)).height(uy(MenuScreen.BUTTON_SIZE_Y));
        add(nextButton).fill().right().width(ux(MenuScreen.BUTTON_SIZE_X)).height(uy(MenuScreen.BUTTON_SIZE_Y));
//        debug();
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
