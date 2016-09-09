package com.avelov.desktop;

import com.avelov.OrientationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.avelov.Aloa;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		final int userHeight = 960; //modify if your screen is too small :P

		config.width = (int) (540f/960f * userHeight);
		config.height = userHeight;
		config.fullscreen = false;
		config.resizable = false;
		OrientationManager orientationManager = new OrientationManager()
		{
			OrientationManager.Orientation orientation;
			@Override
			public Orientation getOrientation()
			{
				return orientation;
			}

			@Override
			public void setOrientation(Orientation newOrientation)
			{
				int width = Gdx.graphics.getWidth();
				int height = Gdx.graphics.getHeight();
				switch(newOrientation)
				{
					case PORTRAIT:
						Gdx.graphics.setWindowedMode(Math.min(width, height), Math.max(width, height));
						break;
					case LANDSCAPE:
						Gdx.graphics.setWindowedMode(Math.max(width, height), Math.min(width, height));
						break;
				}
			}
		};
		new LwjglApplication(new Aloa(orientationManager), config);
	}
}
