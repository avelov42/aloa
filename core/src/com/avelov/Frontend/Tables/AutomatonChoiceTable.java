package com.avelov.Frontend.Tables;


import com.avelov.Aloa;
import com.avelov.Backend.Boundary.BoundaryWrap;
import com.avelov.Center.AutomatonLoader.AutomatonLoaderException;
import com.avelov.Center.BoardHandler;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.FileManager;
import com.avelov.Frontend.GameplayScreen;
import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

public class AutomatonChoiceTable extends DynamicTable
{
    public enum Mode
    {
        New,
        Load
    }
    final List<AutomatonInfo> graphicalAutomataList = new List<>(Aloa.assets.skin);
    final Mode mode;

    @Override
    public void refresh()
    {
        ArrayList<AutomatonInfo> fetchedList;
        switch(mode)
        {
            case New:
                fetchedList = FileManager.getPredefinedAutomata();
                break;
            case Load:
                fetchedList = FileManager.getSavedAutomata();
                break;
            default:
                fetchedList = new ArrayList<>(); //just calming down the AS
                break;
        }
        graphicalAutomataList.clearItems();
        //@todo Write nice handling of case when list is empty (non-clickable "The list is empty..")
        graphicalAutomataList.setItems(fetchedList.toArray(new AutomatonInfo[fetchedList.size()]));
    }

    public AutomatonChoiceTable(final Mode mode)
    {
        this.mode = mode;
        refresh();
        ScrollPane scrollPane = new ScrollPane(graphicalAutomataList, Aloa.assets.skin);

        ImageButton backButton = Aloa.assets.makeButton("left-arrow");
        backButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().popTable();
            }
        });
        ImageButton infoButton = Aloa.assets.makeButton("info");
        infoButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                MenuScreen.getInstance().pushTable(new InfoTable(graphicalAutomataList.getSelected().getDescription()));
                //@todo Consider modal window here.
            }
        });
        ImageButton nextButton = Aloa.assets.makeButton("right-arrow");
        nextButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                switch(mode)
                {
                    case New:
                        try
                        {
                            FileManager.loadPredefinedAutomaton(graphicalAutomataList.getSelected());
                            MenuScreen.getInstance().pushTable(new AutomatonConfigurationTable(graphicalAutomataList.getSelected()));
                        }
                        catch(AutomatonLoaderException e)
                        {
                            e.printStackTrace();
                            MenuScreen.getInstance().showDialog("Cannot load this automaton.\nPlease check syntax in file.");
                        }

                        MenuScreen.getInstance().pushTable(new AutomatonConfigurationTable(graphicalAutomataList.getSelected()));
                        break;
                    case Load:
                        break;

                }
            }
        });



       add(scrollPane).expand().fill().colspan(3).padBottom(uy(20)).row();
       add(backButton).left().width(ux(MenuScreen.BUTTON_SIZE_X)).height(uy(MenuScreen.BUTTON_SIZE_Y));
       add(infoButton).width(ux(MenuScreen.BUTTON_SIZE_X)).height(uy(MenuScreen.BUTTON_SIZE_Y));
       add(nextButton).right().width(ux(MenuScreen.BUTTON_SIZE_X)).height(uy(MenuScreen.BUTTON_SIZE_Y));
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
