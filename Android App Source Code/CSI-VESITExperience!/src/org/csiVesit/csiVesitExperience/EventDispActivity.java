package org.csiVesit.csiVesitExperience;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDispActivity extends Activity
{
	String eventName="", winnersName="", winnerCaption="";
	boolean hasFotoGallery = true;
	static int[] imgIDs;
	Bundle receivedData;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_event_disp);
		
		receivedData = getIntent().getExtras();
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText(receivedData.getString("EventName"));
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg2lytwt));
		
		TextView textViewAbtEvent = (TextView)findViewById(R.id.textViewAbtEvent);
		textViewAbtEvent.setText(receivedData.getString("EventDescription"));
		
		TextView textViewWinnerCaption = (TextView)findViewById(R.id.textViewWinnerCaption);
		if((winnerCaption = receivedData.getString("WinnerCaption"))!=null)
			textViewWinnerCaption.setText(winnerCaption);
		else
		{
			textViewWinnerCaption.setVisibility(View.GONE);
			findViewById(R.id.seperator1).setVisibility(View.GONE);
		}
		
		TextView textViewWinnersName = (TextView)findViewById(R.id.textViewWinnersName);
		if((winnersName = receivedData.getString("EventWinners"))!=null)
			textViewWinnersName.setText(winnersName);
		else
		{
			textViewWinnersName.setVisibility(View.GONE);
			findViewById(R.id.seperator2).setVisibility(View.GONE);
		}
		
		Button btnImgGallery = (Button)findViewById(R.id.BtnImgGallery);
		if(receivedData.getBoolean("HasImgGallery"))
		{
			imgIDs = getIntent().getIntArrayExtra("ImgIDs");
			btnImgGallery.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					startActivity(new Intent("org.csiVesit.csiVesitExperience.CSI_VESITAppFotoGallery"));
				}
			});
		}else
			btnImgGallery.setVisibility(View.GONE);
		
		Button btnEventReg = (Button)findViewById(R.id.btnEventReg);
		if(receivedData.getBoolean("HasRegistrations"))
		{
			btnEventReg.setOnClickListener(new View.OnClickListener()
			{	
				@Override
				public void onClick(View v) 
				{
					//startActivity(new Intent(""));
					Toast.makeText(getBaseContext(), "Via App Registrations coming soon!", Toast.LENGTH_SHORT).show();
				}
			});
		}else
			btnEventReg.setVisibility(View.GONE);
		
		Button btnWE = (Button)findViewById(R.id.btnWE);
		if(receivedData.getBoolean("HasWirelessElims"))
		{
			btnWE.setOnClickListener(new View.OnClickListener()
			{	
				@Override
				public void onClick(View v) 
				{
					org.csiVesit.wirelesselims.MainActivity.currentEvent = ((TextView)findViewById(R.id.myTitle)).getText().toString().replaceAll("[\\s!-]", "");
					startActivity(new Intent("org.csiVesit.wirelesselims.MainActivity"));
				}

			});
		}else
			btnWE.setVisibility(View.GONE);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_disp, menu);
		return true;
	}*/

}
