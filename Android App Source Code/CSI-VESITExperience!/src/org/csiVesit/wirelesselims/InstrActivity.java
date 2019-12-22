package org.csiVesit.wirelesselims;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.csiVesit.csiVesitExperience.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InstrActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.we_activity_instr);
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
		
		((TextView)findViewById(R.id.txtViewInstr)).setTextColor(Color.WHITE);
		
		final Button btnStart = (Button)findViewById(R.id.BtnStartQs);
		btnStart.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent("org.csiVesit.wirelesselims.WEQs"));
				finish();
			}
		});
		
		final Button btnGetQsFromServer = (Button)findViewById(R.id.btnGetQsFromServer);
		btnGetQsFromServer.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v)
			{
				if(getQsXMLFileFromServer())
				{
					btnGetQsFromServer.setEnabled(false);
					btnStart.setEnabled(true);
				}else
					Toast.makeText(getBaseContext(), "An Error occured loading Questions .. please re-try!", Toast.LENGTH_SHORT).show();
			}
		});
		
		if(getPerQTimeOutSecsFromServer())
			btnGetQsFromServer.setEnabled(true);
		else
			Toast.makeText(getBaseContext(), "An Error occured loading Questions settings .. please re-try!", Toast.LENGTH_SHORT).show();
	}

	private boolean getPerQTimeOutSecsFromServer()	//returns true if successfully received time out in seconds per Question
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
				MainActivity.toServerOPStrm.write("GETQSTIMEOUTSECS "+System.getProperty("line.separator"));
				MainActivity.toServerOPStrm.flush();
				
				Object serverReply = MainActivity.fromServerOIS.readObject();		//deserialize object
				
				if(serverReply instanceof String && ((String)serverReply).startsWith("GETQSTIMEOUTSECS "))
				{
					QuestionsActivity.timeOutPerQSecs = Integer.parseInt(((String)serverReply).replace("GETQSTIMEOUTSECS ", ""));
					return true;
				}else
					throw new Exception();
				/*toServerOPStrm.close();
				fromServerOIS.close();
				clientSocket.close();*/
			}catch (Exception e) 
			{
				Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	    return false;
	}

	private boolean getQsXMLFileFromServer()			//returns true if successfully received and made Qs XML File else false
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
				MainActivity.toServerOPStrm.write("GETQSFILE "+MainActivity.currentEvent+"QsXML.zip"+System.getProperty("line.separator"));
				MainActivity.toServerOPStrm.flush();
				
				Object serverReply = MainActivity.fromServerOIS.readObject();		//deserialize object
				
				if(serverReply instanceof String && ((String)serverReply).endsWith(" File Request Rejected!"))
				{
					Toast.makeText(getBaseContext(), "Unable to load Questions, make sure you are playing through desired Event .. please try again!", Toast.LENGTH_LONG).show();
					return false;
				}else if(serverReply instanceof byte[])
				{
					File zipFile;
					FileOutputStream currentEventfos = new FileOutputStream(zipFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+".CSI VESIT Experience!"+File.separator+MainActivity.currentEvent+File.separator+MainActivity.currentEvent+"QsXML.zip"));
					currentEventfos.write((byte[])serverReply);
					currentEventfos.close();
					
					File unzippedDestinationDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+".CSI VESIT Experience!"+File.separator+MainActivity.currentEvent+File.separator+MainActivity.currentEvent+"QsXML"+File.separator);
					if(!unzippedDestinationDir.exists())
						unzippedDestinationDir.mkdirs();
					ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
					ZipEntry ze = null;
					FileOutputStream fout = null;
					while((ze = zis.getNextEntry())!=null)
					{
						fout = new FileOutputStream(new File(unzippedDestinationDir, ze.getName()));
						for(int c=zis.read();c!=-1;c=zis.read())
							fout.write(c);
						fout.close();
						zis.closeEntry();
					}
					zis.close();
					
					Toast.makeText(getBaseContext(), "Questions Loaded Successfully!", Toast.LENGTH_SHORT).show();
					
					return true;
				}
				/*toServerOPStrm.close();
				fromServerOIS.close();
				clientSocket.close();*/
			}catch (Exception e) 
			{
				Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	    return false;
	}
}
