package org.csiVesit.csiVesitExperience;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		CheckBox checkBoxDispSplashScreen = (CheckBox)findViewById(R.id.checkBoxDispSplashScreen);
		checkBoxDispSplashScreen.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				SplashActivity.showSplashScreen = ((CheckBox)v).isChecked()?true:false;
			}
		});
	}
	
}
