package com.avelov.Frontend;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by loudrainbow on 13.06.16.
 */
public class BoardController implements GestureDetector.GestureListener
{
    public static final float cameraMoveSpeed = 8f;
    public static final float cameraZoomInSpeed = 5f;
    public static final float cameraZoomOutSpeed = 5f;

    private BrushWindow brushWindow;
    private BoardRenderer view;
    private GestureDetector gestureDetector;
    boolean cameraMode;

    public BoardController(BoardRenderer view, BrushWindow brushWindow)
    {
        this.view = view;
        this.brushWindow = brushWindow;
        gestureDetector = new GestureDetector(this);
    }

    public GestureDetector getAndroidProcessor()
    {
        return gestureDetector;
    }

    public void setCameraMode(boolean cameraMode)
    {
        this.cameraMode = cameraMode;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        System.out.println("touchDown");
        if(!cameraMode)
        {
            brushWindow.startDrawing();
            return true;
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        System.out.println("tap");
        if(!cameraMode)
        {
            brushWindow.stopDrawing();
            return true;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        System.out.println("longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        System.out.println("fling");
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        if(cameraMode)
        {
            view.translate(-deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
        System.out.println("panStop");
        if(!cameraMode)
        {
            brushWindow.stopDrawing();
            return true;
        }
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        System.out.println("zoom");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        System.out.println("pinch");
        return false;
    }

    @Override
    public void pinchStop()
    {

    }
}



