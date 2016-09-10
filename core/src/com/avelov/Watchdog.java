package com.avelov;

import com.avelov.Frontend.Screens.MenuScreen;
import com.badlogic.gdx.Gdx;

/**
 * Created by loudrainbow on 13.06.16.
 */
public class Watchdog implements Runnable
{
    private float fpsLimit;
    private int checkIntervalMs;
    private int allowableDrops;

    public Watchdog(float fpsLimit, int checkIntervalMs, int allowableDrops)
    {
        this.fpsLimit = fpsLimit;
        this.checkIntervalMs = checkIntervalMs;
        this.allowableDrops = allowableDrops;
    }
    @Override
    public void run()
    {
        int counter = 0;
        //System.out.println("Watchdog - waiting for fps data");
        while(Gdx.graphics.getFramesPerSecond() == 0)
        {
            try
            {
                Thread.sleep(20);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        //System.out.println("Watchdog - fps estabilished");
        while(true)
        {
            try
            {
                Thread.sleep(checkIntervalMs);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            if(Gdx.graphics.getFramesPerSecond() < fpsLimit)
                counter++;
            else
                counter = 0;

            if(counter > allowableDrops)
                Aloa.instance.setScreen(MenuScreen.getInstance());
            else;
                //System.out.println("Watchdog OK, counter: " + counter + " fps: " + Gdx.graphics.getFramesPerSecond());

        }
    }
}
