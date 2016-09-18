package com.avelov.Frontend.Screens;

import com.avelov.Aloa;
import com.avelov.Backend.Boundary.BoundaryWrap;
import com.avelov.Center.BoardHandler;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.FileManager;
import com.avelov.Frontend.GameplayScreen;
import com.avelov.Frontend.Tables.DynamicTable;
import com.avelov.Frontend.Tables.MenuTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Stack;

public class MenuScreen implements Screen
{
    private static MenuScreen instance;
    public static final float BUTTON_SIZE_X = 240;
    public static final float BUTTON_SIZE_Y = 160;
    public static final float TABLE_POSITION_X = 40, TABLE_POSITION_Y = 15;
    public static final float TABLE_SIZE_X = 900, TABLE_SIZE_Y = 800;
    public static final float TABLE_CHANGE_DURATION = 0.5f; //seconds
    public static final Interpolation TABLE_CHANGE_LOOK = Interpolation.swing;
    private Stage stage;
    private Stack<DynamicTable> tableStack;

    private DynamicTable previousTable, nextTable;
    private boolean changing;

    private MenuScreen()
    {
        tableStack = new Stack<>();
        stage = new Stage();
        tableStack.push(new MenuTable());
        tableStack.peek().setSize(ux(TABLE_SIZE_X), uy(TABLE_SIZE_Y));
        tableStack.peek().setPosition(ux(TABLE_POSITION_X), uy(TABLE_POSITION_Y));
        stage.addActor(tableStack.peek());
    }

    public void pushTable(DynamicTable table)
    {
        if(!changing)
        {
            nextTable = table;
            previousTable = tableStack.peek();
            nextTable.setSize(ux(TABLE_SIZE_X), uy(TABLE_SIZE_Y));
            nextTable.setPosition(ux(1000 + TABLE_POSITION_X) , uy(TABLE_POSITION_Y));
            tableStack.push(nextTable);
            stage.addActor(nextTable);
            previousTable.addAction(Actions.moveTo(ux(-1000 + TABLE_POSITION_X), uy(TABLE_POSITION_Y), TABLE_CHANGE_DURATION, TABLE_CHANGE_LOOK));
            nextTable.addAction(Actions.moveTo(ux(TABLE_POSITION_X), uy(TABLE_POSITION_Y), TABLE_CHANGE_DURATION, TABLE_CHANGE_LOOK));
            changing = true;
        }
    }

    public void popTable()
    {
        if(!changing)
        {
            previousTable = tableStack.pop();
            nextTable = tableStack.peek();
            previousTable.addAction(Actions.moveTo(ux(1000 + TABLE_POSITION_X), uy(TABLE_POSITION_Y), TABLE_CHANGE_DURATION, TABLE_CHANGE_LOOK));
            nextTable.addAction(Actions.moveTo(ux(TABLE_POSITION_X), uy(TABLE_POSITION_Y), TABLE_CHANGE_DURATION, TABLE_CHANGE_LOOK));
            changing = true;
        }
    }

    private boolean isChangingDone(DynamicTable table)
    {
        return table.getActions().size > 0;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.X))
        {
            AutomatonInfo toRun = FileManager.getPredefinedAutomata().get(0);
            Aloa.instance.setScreen(new GameplayScreen(new BoardHandler(toRun, toRun.getTopologies().get(0), new BoundaryWrap())));
        }

        //Action.act(float) returns true when action is done
        if(nextTable != null && isChangingDone(nextTable))
        {
            //first frame after end of changing table
            changing = false;
            nextTable = null;
            previousTable = null;
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(Aloa.assets.ignoreInput);
    }

    @Override
    public void dispose()
    {

    }

    public void showDialog(String message)
    {
        Label textLabel = new Label(message, Aloa.assets.skin);
        Dialog d = new Dialog("Error", Aloa.assets.skin, "dialog"){}.text(textLabel).button("I'll fix it", true);
        ((TextButton) d.getButtonTable().getCells().get(0).getActor()).getLabelCell().pad(uy(10), ux(10), uy(10), ux(10));
        d.getContentTable().pad(uy(10), ux(10), uy(10), ux(10));
        d.getTitleLabel().setAlignment(Align.center);
        ((Label)((TextButton) d.getButtonTable().getCells().get(0).getActor()).getLabelCell().getActor()).setFontScale(0.8f);
        System.out.println(((TextButton)(d.getButtonTable().getCells().get(0).getActor())).getStyle());//.setStyle(Aloa.assets.skin.get("button", TextButton.TextButtonStyle.class));
        d.show(stage);
    }

    public static MenuScreen getInstance()
    {
        return instance == null ? instance = new MenuScreen() : instance;
    }

    /*
    Using ux() and uy() allows to set ratio-independent distances between UI actors.
    Just express distances in promiles of width for ux and height for uy. (1% == 10%%)
     */
    static public float ux(float x)
    {
        return x / 1000f * Gdx.graphics.getWidth();
    }
    static public float uy(float y)
    {
        return y / 1000f * Gdx.graphics.getHeight();
    }
}