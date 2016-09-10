package com.avelov.Frontend;

import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.avelov.Aloa;
import com.avelov.Center.BoardHandler;
import com.avelov.Center.Files.FileManager;

/**
 * Created by loudrainbow on 24.04.16.
 */
public class DebugInputProcessor implements InputProcessor
{
    private GameplayScreen gameplay;
    private BoardHandler handler;
    private BoardRenderer view;
    private BrushWindow brushWindow;
    private Brush brush;
    private boolean scrollingSize;
    private boolean disabled;

    public DebugInputProcessor(GameplayScreen gameplay, BoardHandler handler, BoardRenderer view, BrushWindow brushWindow)
    {
        this.gameplay = gameplay;
        this.handler = handler;
        this.view = view;
        this.brushWindow = brushWindow;
        brush = brushWindow.getBrush();
        scrollingSize = true;
        disabled = false;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }

    @Override
    public String toString()
    {
        return "Klawiszologia obecnie: \n" +
                "WSAD - ruch kamery\n" +
                "QE - zoom kamery\n" +
                "X - zmiana zachowania scrolla (boardSize/density)\n" +
                "C - zmiana aktualnego stanu brusha na następny\n" +
                "Z - ukrycie/pokazanie brusha\n" +
                "P - jeden krok\n" +
                "F5 - zapisz\n" +
                "SPACJA - pauza/wznów";
    }

    void handleContinousInput()
    {
        if(disabled)
            return;
        if(Gdx.input.isKeyPressed(Input.Keys.Q))
            view.zoom(-BoardController.cameraZoomInSpeed);
        if(Gdx.input.isKeyPressed(Input.Keys.E))
            view.zoom(BoardController.cameraZoomOutSpeed);
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            view.translate(0, BoardController.cameraMoveSpeed);
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            view.translate(0, -BoardController.cameraMoveSpeed);
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            view.translate(-BoardController.cameraMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            view.translate(BoardController.cameraMoveSpeed, 0);
    }


    @Override
    public boolean keyDown(int keycode)
    {
        if(disabled)
            return false;
        if(keycode == Input.Keys.SPACE)
        {
            gameplay.pauseOrResume();

        }
        if(keycode == Input.Keys.ESCAPE)
            Aloa.instance.setScreen(MenuScreen.getInstance());
        if(keycode == Input.Keys.P)
        {
            handler.makeStep();
            System.out.println("Generation: " + handler.getStepsCount());
        }
        if (keycode == Input.Keys.F5) {
            FileManager.SaveAutomaton(handler, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            System.out.printf("Automaton not saved");
        }

        if(keycode == Input.Keys.C)
        {
            brush.nextState();
            System.out.println("Brush state is now: \"" + brush + "\"");
        }

        if(keycode == Input.Keys.X)
        {
            scrollingSize = !scrollingSize;
            if(scrollingSize) System.out.println("You are now scrolling boardSize");
            else System.out.println("You are now scrolling density");
        }

        if(keycode == Input.Keys.Z)
        {
            brush.setVisible(!brush.isVisible());
        }
        brushWindow.update(false);
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        if(scrollingSize)
            brush.setSize(brush.getSize() - amount);
        else
            brush.setDensity(brush.getDensity() - amount / 100f);
        brushWindow.update(false);
        return true;
    }
}
