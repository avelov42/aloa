package com.avelov.Frontend;

import com.avelov.Aloa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import com.avelov.Center.BoardHandler;
import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Frontend.CellDrawers.CellDrawer;


/**
 * Class representing game play screen.
 * Contains board view, windows with brush, pastes, etc.
 * @todo Please decouple board rendering from brush rendering, split rendering in celldrawer
 * @todo Use stage plz
 * @todo Fix matrix chaos
 * @todo Use assets manager
 */

public class GameplayScreen implements Screen
{

    private BoardHandler handler;
    private AutomatonInfo blueprint;
    private Stage stage;

    private ShapeRenderer shapeRenderer;
    private InputMultiplexer inputMultiplexer;
    private DebugInputProcessor debugInputProcessor;

    private BoardRenderer boardView;
    private BoardController boardController;

    private BrushWindow brushWindow;
    private Brush brush;

    private SpeedWindow speedWindow;
    //private SpeedMeter speedMeter;

    private ToolsWindow toolsWindow;
    private boolean isModalMode; //is modal window on top?
    private boolean isCameraMode;


    public GameplayScreen(BoardHandler handler)
    {
        //@todo Move this cell drawer somewhere else
        CellDrawer drawer = handler.getTopology().getFrontendTopology().getCellDrawer();
        //drawer.setTinter(handler.getColoring().get(0));

        this.shapeRenderer = Aloa.assets.shape;
        this.stage = new Stage();

        this.handler = handler;

        this.boardView = new BoardRenderer(handler, drawer);
        //this.brush = new Brush(handler, boardView, handler.getBrushStates());
        this.brushWindow = new BrushWindow(brush);

        this.speedWindow = new SpeedWindow(new SpeedMeter(SpeedMeter.START_SPEED_PERCENT, SpeedMeter.MAX_WAIT_TIME));

        this.boardController = new BoardController(boardView, brushWindow);
        this.debugInputProcessor = new DebugInputProcessor(this, handler, boardView, brushWindow);
        this.inputMultiplexer = new InputMultiplexer();

        this.toolsWindow = new ToolsWindow(handler, speedWindow, this, stage);

        pause();
        setUpStage();
    }

    private void setUpStage()
    {
        stage.addActor(brushWindow);
        stage.addActor(speedWindow);
        stage.addActor(toolsWindow);
    }

    public void setCameraMode(boolean cameraMode)
    {
        isCameraMode = cameraMode;
        boardController.setCameraMode(cameraMode);
        brush.setVisible(!cameraMode);
    }
    public boolean isCameraMode()
    {
        return isCameraMode;
    }

    @Override
    public void resize(int width, int height)
    {
        boardView.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show()
    {
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(debugInputProcessor);
        inputMultiplexer.addProcessor(boardController.getAndroidProcessor());
        setCameraMode(false);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(Aloa.assets.ignoreInput);
    }

    public void modalInputMode(boolean modal)
    {
        isModalMode = modal;
        if(modal)
        {
            inputMultiplexer.removeProcessor(debugInputProcessor);
            inputMultiplexer.removeProcessor(boardController.getAndroidProcessor());
        }
        if(!modal)
        {
            inputMultiplexer.addProcessor(debugInputProcessor);
            inputMultiplexer.addProcessor(boardController.getAndroidProcessor());
        }
    }

    long nextMillis = TimeUtils.millis() + 1000;
    int calls = 0;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //@todo sync speedmeter and speed window
        if (!isModalMode)
            debugInputProcessor.handleContinousInput();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        boardView.render();
        brush.render();
        shapeRenderer.end();

        stage.act();
        stage.draw();
        if (speedWindow.shouldMakeStep())
        {
            handler.makeStep();
            calls++;
        }

        if (TimeUtils.millis() > nextMillis)
        {
            System.out.println(calls);
            calls = 0;
            nextMillis = TimeUtils.millis() + 1000;
        }
    }



    @Override
    public void dispose()
    {

    }

    public void pauseOrResume()
    {
        if(speedWindow.isPaused())
            speedWindow.pause();
        else
            speedWindow.resume();

    }
    @Override
    public void pause() { speedWindow.pause();}
    @Override
    public void resume() { speedWindow.resume(); }


}

