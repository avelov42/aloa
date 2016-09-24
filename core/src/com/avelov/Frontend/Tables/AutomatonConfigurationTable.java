package com.avelov.Frontend.Tables;

import com.avelov.Aloa;
import com.avelov.Backend.Boundary.BoundaryConstant;
import com.avelov.Backend.Boundary.BoundaryMirror;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Boundary.BoundarySame;
import com.avelov.Backend.Boundary.BoundaryWrap;
import com.avelov.Center.BoardHandler;
import com.avelov.Center.BrushState;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.Layer;
import com.avelov.Center.Files.TopologyScript;
import com.avelov.Frontend.GameplayScreen;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Select;

import java.util.List;

/**
 * Created by avelov on 9/10/16.
 */
public class AutomatonConfigurationTable extends DynamicTable
{
    private enum BoundaryMode
    {
        ConstantValue("Constant value"),
        Same("Same"),
        Mirror("Mirror"),
        Wrap("Wrap");

        private final String name;
        BoundaryMode(final String name)
        {
            this.name = name;
        }
        @Override
        public String toString()
        {
            return name;
        }
        public static BoundaryMode[] getAll()
        {
            return new BoundaryMode[]{ConstantValue, Same, Mirror, Wrap};
        }
    }

    private List<Layer> layers;
    private int currentLayer = 0;
    private Label layerLabel;

    final SelectBox<BoundaryMode> boundaryChoice;
    //layer dependents

    final SelectBox<AutomatonInfo.TinterDetails> coloringChoice;
    final SelectBox<BrushState> constantValueLayerChoice;
    float[] constantValues;
    Label constantBoundaryValueLabel;


    private void incrementCurrentLayer()
    {
        currentLayer = (currentLayer+1) % layers.size();
        update();
    }
    private void decrementCurrentLayer()
    {
        currentLayer = currentLayer-1 < 0 ? layers.size()-1 : currentLayer-1;
        update();
    }

    private void update()
    {
        layerLabel.setText(layers.get(currentLayer).getName());
        coloringChoice.setItems(layers.get(currentLayer).getTinters().toArray(new AutomatonInfo.TinterDetails[layers.get(currentLayer).getTinters().size()]));
        constantValueLayerChoice.setItems(layers.get(currentLayer).getBrushStates().toArray(new BrushState[layers.get(currentLayer).getBrushStates().size()]));
        constantValueLayerChoice.setSelected(layers.get(currentLayer).getDefState());
        constantValueLayerChoice.setVisible(boundaryChoice.getSelected() == BoundaryMode.ConstantValue);
        constantBoundaryValueLabel.setVisible(boundaryChoice.getSelected() == BoundaryMode.ConstantValue);
        constantValues[currentLayer] = constantValueLayerChoice.getSelected().getValue();
    }


    public AutomatonConfigurationTable(final AutomatonInfo selected)
    {
        Table automatonConfig = new Table(), layerConfig = new Table();
        Label automatonName = new Label(selected.getName(), Aloa.assets.skin.get("title", Label.LabelStyle.class));
        automatonName.setAlignment(Align.center);

        final Label topologyLabel = new Label("Automaton topology:", Aloa.assets.skin);
        final SelectBox<TopologyScript> topologyChoice = new SelectBox<>(Aloa.assets.skin);
        topologyChoice.setItems(selected.getTopologies().toArray(new TopologyScript[selected.getTopologies().size()]));

        final Label boundaryLabel = new Label("Boundary behaviour:", Aloa.assets.skin);
        boundaryChoice = new SelectBox<>(Aloa.assets.skin);
        boundaryChoice.setItems(BoundaryMode.getAll());
        boundaryChoice.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                update();
            }
        });


        layers = selected.getLayers();
        constantValues = new float[layers.size()];
        for(int i = 0; i < layers.size(); i++)
            constantValues[i] = layers.get(i).getDefault();


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
        constantValueLayerChoice = new SelectBox<>(Aloa.assets.skin);

        update();

        Label sizeLabel = new Label("Size:", Aloa.assets.skin);
        Label configLabel = new Label("Config string:", Aloa.assets.skin);
        final TextField sizeInput = new TextField("", Aloa.assets.skin);
        final TextField configInput = new TextField("", Aloa.assets.skin);
        sizeInput.setText("42");

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
                BoundaryPolicy boundaryPolicy = makeBoundaryPolicy();
                int boardSize = 42;
                try
                {
                    boardSize = Integer.parseInt(sizeInput.getText());
                    if(boardSize < 1) throw new NumberFormatException();
                }
                catch(NumberFormatException e)
                {
                    MenuScreen.getInstance().showDialog("Size must be a positive number");
                    return;
                }
                selected.setBoardSize(boardSize);
                selected.setCellSize(2); //@todo temporary hax, remove after main issue is fixed
                Aloa.instance.setScreen(new GameplayScreen(new BoardHandler(selected, topologyChoice.getSelected(), boundaryPolicy)));
            }
        });



        final int automatonColumns = 2;

//        automatonConfig.debug();
        automatonConfig.top();
        automatonConfig.add(automatonName).colspan(automatonColumns).fill().spaceBottom(uy(32)).row();

        automatonConfig.add(topologyLabel).colspan(automatonColumns).fill().expandX().spaceBottom(uy(10)).row();
        automatonConfig.add(topologyChoice).colspan(automatonColumns).fill().expandX().spaceBottom(uy(16)).row();

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
        layerConfig.add(constantValueLayerChoice).colspan(layerColumns).fill().expandX().row();

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

    private BoundaryPolicy makeBoundaryPolicy()
    {
        switch(boundaryChoice.getSelected())
        {
            default:
            case ConstantValue:
                return new BoundaryConstant(constantValues);
            case Same:
                return new BoundarySame();
            case Mirror:
                return new BoundaryMirror();
            case Wrap:
                return new BoundaryWrap();
        }
    }

    @Override
    public void refresh()
    {
        //nothing to refresh
    }
}
