package com.avelov;

public interface OrientationManager
{
    enum Orientation
    {
        PORTRAIT,
        LANDSCAPE
    }
    Orientation getOrientation();
    void setOrientation(Orientation o);
}