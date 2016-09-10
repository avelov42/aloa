package com.avelov;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable
{
    public SpriteBatch batch;
    public ShapeRenderer shape;
    public EmptyInputProcessor ignoreInput;
    public Skin skin;
    public String aloaAbout = getAloaAbout();

    @Override
    public void dispose()
    {
        batch.dispose();
        shape.dispose();
        skin.dispose();
    }


    public ImageButton makeButton(String name)
    {
        Drawable tinted = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("skin/icons/" + name + ".png")))).tint(new Color(0.2f, 1f, 0.2f, 1f));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("skin/icons/" + name + ".png")))), tinted);
    }


    public Assets()
    {
        /** SKIN & FONT **/
        skin = new Skin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        float size = (float) Math.sqrt(Gdx.graphics.getWidth() * Gdx.graphics.getHeight()) * 0.04f;
        if(Gdx.app.getType() == Application.ApplicationType.Android) size *= 0.75f;
        parameter.spaceX = 2;

        parameter.size = Math.round(size);
        BitmapFont buttonFont = generator.generateFont(parameter);

        parameter.size = Math.round(size * 0.8f);
        BitmapFont titleFont = generator.generateFont(parameter);

        parameter.size = Math.round(size * 0.75f);
        BitmapFont labelFont = generator.generateFont(parameter);

        skin.add("font-button", buttonFont);
        skin.add("font-label", labelFont);
        skin.add("font-title", titleFont);
        generator.dispose();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
        skin.load(Gdx.files.internal("skin/uiskin.json"));

        /** Utils **/

        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        ignoreInput = new EmptyInputProcessor();
    }

    private String getAloaAbout()
    {
        return "Welcome in a Lot of Automata!\n\n" +
                "Cellular automata are very interesting topic in mathematics and science.\n\n" +
                "There is a lot of automata - from astounding game of life, through its modifications to undoubtedly " +
                "practical heat transfer automaton.\n\n" +
                "All of them and many more can be found in this sandbox application.\n\n" +
                "Explore them, discover their behaviours, create new structures inside them.\n\n" +
                "Don't forget to show this awesome app to your friends! Have fun!\n\n";
    }
}

