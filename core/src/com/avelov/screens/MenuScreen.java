package com.avelov.screens;

import com.avelov.Aloa;
import com.avelov.tables.DynamicTable;
import com.avelov.tables.MenuTable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.Stack;

public class MenuScreen implements Screen
{
    static private MenuScreen instance;

    private Stack<DynamicTable> tableStack;
    private Stage stage;

    private MenuScreen()
    {
        tableStack = new Stack<>();
        stage = new Stage();
        pushTable(new MenuTable());
    }

    public void pushTable(DynamicTable table)
    {
        tableStack.push(table);
        stage.clear();
        stage.addActor(tableStack.peek());
        //table.setFillParent(true);
        table.setSize(ux(900), uy(750));
        table.setPosition(ux(50), (uy(40)));
    }

    public void popTable()
    {
        tableStack.pop();
        stage.clear();
        stage.addActor(tableStack.peek());
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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