package com.avelov.Frontend.Screens;

import com.avelov.Aloa;
import com.avelov.Frontend.Tables.DynamicTable;
import com.avelov.Frontend.Tables.MenuTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.Stack;

public class MenuScreen implements Screen
{
    static private MenuScreen instance;
    private final float tablePositionX = 40, tablePositionY = 50;
    private final float tableSizeX = 900, tableSizeY = 750;
    private final float tableChangeDuration = 1; //seconds
    private final Interpolation tableChangeLook = Interpolation.swing;
    private Stage stage;
    private Stack<DynamicTable> tableStack;

    private DynamicTable previousTable, nextTable;
    private boolean changing;

    private MenuScreen()
    {
        tableStack = new Stack<>();
        stage = new Stage();
        tableStack.push(new MenuTable());
        tableStack.peek().setSize(ux(tableSizeX), uy(tableSizeY));
        tableStack.peek().setPosition(ux(tablePositionX), uy(tablePositionY));
        stage.addActor(tableStack.peek());
    }

    public void pushTable(DynamicTable table)
    {
        if(!changing)
        {
            nextTable = table;
            previousTable = tableStack.peek();
            nextTable.setSize(ux(tableSizeX), uy(tableSizeY));
            nextTable.setPosition(ux(1000 + tablePositionX) , uy(tablePositionY));
            tableStack.push(nextTable);
            stage.addActor(nextTable);
            previousTable.addAction(Actions.moveTo(ux(-1000 + tablePositionX), uy(tablePositionY), tableChangeDuration, tableChangeLook));
            nextTable.addAction(Actions.moveTo(ux(tablePositionX), uy(tablePositionY), tableChangeDuration, tableChangeLook));
            changing = true;
        }
    }

    public void popTable()
    {
        if(!changing)
        {
            previousTable = tableStack.pop();
            nextTable = tableStack.peek();
            previousTable.addAction(Actions.moveTo(ux(1000 + tablePositionX), uy(tablePositionY), tableChangeDuration, tableChangeLook));
            nextTable.addAction(Actions.moveTo(ux(tablePositionX), uy(tablePositionY), tableChangeDuration, tableChangeLook));
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
    static private final float viewportX = 540, viewportY = 960;
    static public float ux(float x)
    {
        return x / 1000f * viewportX;
    }
    static public float uy(float y)
    {
        return y / 1000f * viewportY;
    }
}