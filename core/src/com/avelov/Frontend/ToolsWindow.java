package com.avelov.Frontend;

import com.avelov.Frontend.Screens.MenuScreen;
import com.avelov.OrientationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import com.avelov.Aloa;
import com.avelov.Center.BoardHandler;
import com.avelov.Center.Files.FileManager;

public class ToolsWindow extends Window
{
    private static final int PLAY_PAUSE_POSITION = 0;
    private static final int CAMERA_EDIT_POSITION = 1;
    private GameplayScreen gameplayScreen;
    private BoardHandler handler;
    private SpeedWindow speedWindow;
    private Stage gameplayStage;

    private ImageButton save;
    private ImageButton menu;
    private ImageButton camera, edit;
    private ImageButton play, pause;

    private ImageButton makeButton(String normal)
    {
        Drawable tinted = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(normal)))).tint(new Color(0.3f + 94f/255, 0.3f + 52f/255, 0.3f + 137f/255, 1f));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(normal)))), tinted);
    }

    public ToolsWindow(BoardHandler handler, SpeedWindow speedWindow, GameplayScreen gameplayScreen,
                       Stage gameplayStage)
    {
        super("Yay buttons!", Aloa.assets.skin);
        getTitleLabel().setAlignment(Align.center);
        getTitleTable().pad(12); //@todo use u()

        this.gameplayScreen = gameplayScreen;
        this.handler = handler;
        this.speedWindow = speedWindow;
        this.gameplayStage = gameplayStage;

        save = makeButton("icons/png/save.png");
        camera = makeButton("icons/png/camera.png");
        edit = makeButton("icons/png/edit.png");
        menu = makeButton("icons/png/menu.png");
        play = makeButton("icons/png/play.png");
        pause = makeButton("icons/png/pause.png");

        setUpListeners();

        if(speedWindow.isPaused()) add(play).size(50, 50).pad(12);
        else add(pause).size(50, 50).pad(12);
        add(camera).size(50, 50).pad(12);
        add(save).size(50, 50).pad(12);
        add(menu).size(50, 50).pad(12);


        pack();

        setPosition(Gdx.graphics.getWidth() * 0.98f - getWidth(), Gdx.graphics.getHeight() * 0.98f - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
        if(speedWindow.isPaused()) getCells().get(PLAY_PAUSE_POSITION).setActor(play);
        else getCells().get(PLAY_PAUSE_POSITION).setActor(pause);

        if(gameplayScreen.isCameraMode()) getCells().get(CAMERA_EDIT_POSITION).setActor(edit);
        else getCells().get(CAMERA_EDIT_POSITION).setActor(camera);
    }

    private void setUpListeners()
    {
        camera.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                gameplayScreen.setCameraMode(true);
            }
        });

        edit.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                gameplayScreen.setCameraMode(false);
            }
        });

        play.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                speedWindow.resume();
            }
        });

        pause.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                speedWindow.pause();
            }
        });

        menu.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Aloa.orientationManager.setOrientation(OrientationManager.Orientation.PORTRAIT);
                Gdx.graphics.setWindowedMode(540, 960);
                Aloa.instance.setScreen(MenuScreen.getInstance(), OrientationManager.Orientation.PORTRAIT);
            }
        });

        save.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                speedWindow.pause();
                final TextField textField = new TextField("", Aloa.assets.skin);
                Dialog d = new Dialog("Error", Aloa.assets.skin, "dialog") {

                    {
                        text("Enter save name: ");
                        button("Save it", true);
                        button("Cancel", false);
                    }
                    @Override
                    protected void result(final Object object)
                    {
                        if(object.equals(true))
                        {
                            System.out.println("Saving");
                            String name = textField.getText();
                            if(name.isEmpty())
                            {
                                cancel();
                                textField.setText("unnamed");
                            }
                            FileManager.SaveAutomaton(handler, textField.getText());
                        }
                        speedWindow.resume();
                        gameplayScreen.modalInputMode(false); //sets up input processors again
                    }

                };
                d.getTitleTable().pad(12);
                d.getContentTable().pad(12).add(textField);
                gameplayScreen.modalInputMode(true);
                d.show(gameplayStage);

            }
        });
    }
}
