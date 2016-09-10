package com.avelov.Frontend.Tables;


import com.avelov.Aloa;
import com.avelov.Center.Files.AutomatonDescription;
import com.avelov.Center.Files.FileManager;
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
    final List<AutomatonDescription> graphicalAutomataList = new List<>(Aloa.assets.skin);
    final Mode mode;

    @Override
    public void refresh()
    {
        ArrayList<AutomatonDescription> fetchedList;
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
        graphicalAutomataList.setItems(fetchedList.toArray(new AutomatonDescription[fetchedList.size()]));
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
                MenuScreen.getInstance().pushTable(new InfoTable("Believe me or not, but this automaton is epic!"));
                //todo it's good idea to use modal window here
                //todo add real fetching
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
                        MenuScreen.getInstance().pushTable(new AutomatonConfigurationTable(graphicalAutomataList.getSelected()));
                        break;
                    case Load:
                        //todo add code which starts GameScreen
                        break;

                }
            }
        });

       add(scrollPane).expand().fill().colspan(3).padBottom(uy(20)).row();
       add(backButton).left();
       add(infoButton);
       add(nextButton).right();
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
