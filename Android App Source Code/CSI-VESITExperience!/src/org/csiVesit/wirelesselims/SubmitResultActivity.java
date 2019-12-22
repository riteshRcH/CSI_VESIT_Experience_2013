package org.csiVesit.wirelesselims;

import org.csiVesit.csiVesitExperience.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitResultActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.we_activity_submit_result);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().setBackgroundDrawableResource(R.drawable.bg2lytwt);
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText("Wireless Elims");
			//myTitleText.setTextSize(20);
			//myTitleText.setBackgroundColor(Color.rgb(220, 208, 255));
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		final Button btnSubmitResult = (Button)findViewById(R.id.btnSubmitResult);
		btnSubmitResult.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View btn)
			{
				// TODO Auto-generated method stub
				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			    
			    if(mWifi.isConnected())
			    {
					try
					{
						//Socket clientSocket = new Socket(MainActivity.serverIP, 65535);
						/*Socket clientSocket = new Socket();
						clientSocket.connect(new InetSocketAddress(MainActivity.serverIP, 65535), 2000);
						clientSocket.setSoTimeout(5000);
						BufferedWriter toServerOPStrm = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
						ObjectInputStream fromServerOIS = new ObjectInputStream(clientSocket.getInputStream());*/
						MainActivity.toServerOPStrm.write("RESULT "+MainActivity.teamID+" out of "+QuestionsActivity.finishedQsID.size()+": "+QuestionsActivity.correctAnsCnt+System.getProperty("line.separator"));
						MainActivity.toServerOPStrm.flush();
						
						QuestionsActivity.finishedQsID.clear();
						QuestionsActivity.correctAnsCnt = 0;
						
						String serverReply = (String)MainActivity.fromServerOIS.readObject();
						if(serverReply!=null)
							if(serverReply.equals(MainActivity.teamID+" RESULT ENTRY Success!"))
							{
								Toast.makeText(getBaseContext(), "Score of Team ID: "+MainActivity.teamID+" successfully entered in DB at Server!", Toast.LENGTH_LONG).show();
								btnSubmitResult.setEnabled(false);
								MainActivity.toServerOPStrm.close();
								MainActivity.fromServerOIS.close();
								MainActivity.clientSocket.close();
								finish();
							    startActivity(new Intent("org.csiVesit.wirelesselims.WECredits"));
							}else
								Toast.makeText(getBaseContext(), "Unable to make your score entry .. please try again!", Toast.LENGTH_SHORT).show();
						
					}catch (Exception e) 
					{
						Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_SHORT).show();
					}
			    }
			}
		});
	}
}
