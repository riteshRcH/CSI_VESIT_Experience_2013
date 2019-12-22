package org.csiVesit.csiVesitExperience;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AboutCSIActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_about_csi);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setBackgroundDrawableResource(R.drawable.creditsbglytwt);
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText("About CSI");
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		Button btnCSIVESITSite = (Button)findViewById(R.id.btnCSIVESITSite);
		btnCSIVESITSite.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://csi-vesit.org/csi/")));
			}
		});
		
		Button btnFB = (Button)findViewById(R.id.btnFB);
		btnFB.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/138646719561724/")));
			}
		});
		
		
		Button btnTwitter = (Button)findViewById(R.id.btnTwitter);
		btnTwitter.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/CSI_VESIT")));
			}
		});
	}

}
