package com.avelov;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		OrientationManager orientationManager = new OrientationManager()
		{
			Orientation currentOrientation = Orientation.PORTRAIT;
			@Override
			public Orientation getOrientation()
			{
				return currentOrientation;
			}

			@Override
			public void setOrientation(Orientation newOrientation)
			{
				switch(newOrientation)
				{
					case PORTRAIT:
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
						currentOrientation = Orientation.PORTRAIT;
						break;
					case LANDSCAPE:
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
						currentOrientation = Orientation.LANDSCAPE;
						break;
				}

			}
		};
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initialize(new Aloa(orientationManager), config);
	}
}
