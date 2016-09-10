package com.avelov.Frontend;

import com.avelov.Aloa;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

/**
 * Created by loudrainbow on 10.06.16.
 */
public class Nyan
{
    private Texture nyanTexture;
    private Sprite nyanSprite;
    private long startTime;
    private int height;
    private long nextTime;
    public Nyan()
    {
        nyanTexture = new Texture(Gdx.files.internal("nyan.png"));
        nyanSprite = new Sprite(nyanTexture);
    }

    public void start()
    {
        height = new Random().nextInt((int) (Gdx.graphics.getHeight() * 0.6 + Gdx.graphics.getHeight() * 0.2));
        startTime = TimeUtils.millis() + 2 * 1000;
    }

    public void render()
    {
        nyanSprite.setPosition((TimeUtils.millis() - startTime)/(float)1000 * Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 1, height);
        if(new Random().nextBoolean())
            nyanSprite.translate(0, new Random().nextFloat()*10 -5);
        //Aloa.assets.batch.setProjectionMatrix(new Matrix4());
        //System.out.println(nyanSprite.getX());
        nyanSprite.draw(Aloa.assets.batch);
        if (TimeUtils.timeSinceMillis(startTime) > 59 * 1000)
            start();

    }

}
