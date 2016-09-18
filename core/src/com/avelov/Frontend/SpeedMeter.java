package com.avelov.Frontend;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by loudrainbow on 11.06.16.
 */
public class SpeedMeter
{
    public static long MAX_WAIT_TIME = 42 * 10;
    public static int START_SPEED_PERCENT = 50;

    private long maxWaitTime; //time between steps when speed == 1%
    private long currentWaitTime; //time between steps at current speed
    private long sinceLastUpdate; //time already elapsed since last tick
    private long lastTickTime; //timestamp from last tick to compute elapsed time
    private int savedSpeed;

    public SpeedMeter(int startValue, long maxWaitTime)
    {
        this.maxWaitTime = maxWaitTime;
        lastTickTime = TimeUtils.millis();
        setSpeed(startValue);
        savedSpeed = -1;
    }

    public void setSpeed(int percent)
    {
        if(currentWaitTime == Long.MAX_VALUE && percent > 0) //returning to non-zero
        {
            lastTickTime = TimeUtils.millis();
            sinceLastUpdate = 0;
        }

        if(percent == 0) //entering to zero
            currentWaitTime = Long.MAX_VALUE;
        else
            currentWaitTime = (maxWaitTime * (100 - percent)) / 100;
        //System.out.println("SET: " + currentWaitTime);
    }

    public int getSpeed()
    {
        //System.out.println("GET: " + (currentWaitTime == Long.MAX_VALUE ? 0 : (int) (100 - (100 * currentWaitTime)/maxWaitTime)));
        return currentWaitTime == Long.MAX_VALUE ? 0 : (int) (100 - (100 * currentWaitTime)/maxWaitTime);
    }

    public boolean isPaused()
    {
        return getSpeed() == 0;
    }

    public void resume()
    {
        if(savedSpeed > 0)
        {
            setSpeed(savedSpeed);
            savedSpeed = -1;
        }
    }

    public void pause()
    {
        if(savedSpeed == -1)
        {
            savedSpeed = getSpeed();
            setSpeed(0);
        }
    }

    public boolean shouldMakeStep()
    {
        sinceLastUpdate += TimeUtils.millis() - lastTickTime;
        lastTickTime = TimeUtils.millis();
        if(sinceLastUpdate < currentWaitTime)
            return false;
        else
        {
            sinceLastUpdate = sinceLastUpdate - currentWaitTime;
            return true;
        }
    }
}