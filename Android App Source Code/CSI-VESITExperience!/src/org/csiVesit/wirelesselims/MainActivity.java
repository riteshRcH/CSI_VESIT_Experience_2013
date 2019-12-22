package org.csiVesit.wirelesselims;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.csiVesit.csiVesitExperience.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	boolean serverConnEstablished = false;
	static String teamID = "";
	static String serverIP = "";
	public static String currentEvent;
	static BufferedWriter toServerOPStrm;
	static ObjectInputStream fromServerOIS;
	static Socket clientSocket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.we_activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getWindow().setBackgroundDrawableResource(R.drawable.bglytwt);
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText("Wireless Elims");
			//myTitleText.setTextSize(20);
			//myTitleText.setBackgroundColor(Color.rgb(220, 208, 255));
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		Toast.makeText(getBaseContext(), "Wireless Elims: "+currentEvent, Toast.LENGTH_LONG).show();
		
		final EditText editTextServerIP = (EditText)findViewById(R.id.editTextServerIP);
		InputFilter IpAddrRegexfilter = new InputFilter()
		    {
		        @Override
		        public CharSequence filter(CharSequence source, int start, int end, android.text.Spanned dest, int dstart, int dend) 
		        {
		            if (end > start) 
		            {
		                String destTxt = dest.toString();
		                String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
		                if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) 
		                    return "";
		                else 
		                {
		                    String[] splits = resultingTxt.split("\\.");
		                    for (int i=0; i<splits.length; i++) 
		                    {
		                        if (Integer.valueOf(splits[i]) > 255) 
		                            return "";
		                    }
		                }
		            }
		            return null;
		        }

		    };
		    editTextServerIP.setFilters(new InputFilter[]{IpAddrRegexfilter});
		    
		    final Button btnEstablishConn = (Button)findViewById(R.id.btnEstablishConn);
		    btnEstablishConn.setOnClickListener(new View.OnClickListener()
		    {
				@Override
				public void onClick(View v) 
				{
					ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				    if (mWifi.isConnected())
				    {
						try 
						{
							clientSocket = new Socket();
							clientSocket.connect(new InetSocketAddress(serverIP = editTextServerIP.getText().toString(), 65535), 2000);	//2secs connection timeout
							clientSocket.setSoTimeout(5000);			//5secs read timeout
							toServerOPStrm = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
							fromServerOIS = new ObjectInputStream(clientSocket.getInputStream());
							toServerOPStrm.write("Client: Hello!"+System.getProperty("line.separator"));
							toServerOPStrm.flush();
							Object serverReply = (String)fromServerOIS.readObject();
							if(serverReply!=null)
								if(serverReply.equals("Server: Hello!"))
								{
									serverConnEstablished = true;
									Toast.makeText(getBaseContext(), "Server-Client Handshake successfull!", Toast.LENGTH_SHORT).show();
									btnEstablishConn.setEnabled(false);
									findViewById(R.id.editTextServerIP).setEnabled(false);
									findViewById(R.id.btnRegTeam).setEnabled(true);
								}else
									Toast.makeText(getBaseContext(), "Unable to communicate with server .. please retry", Toast.LENGTH_SHORT).show();
							/*toServerOPStrm.close();
							fromServerOIS.close();
							clientSocket.close();*/
						}catch (UnknownHostException e) 
						{
							Toast.makeText(getBaseContext(), "Cant find server .. contact your class CSI co-ords!", Toast.LENGTH_SHORT).show();						
						}catch (IOException e) 
						{
							Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_SHORT).show();
						}catch (Exception e) 
						{
							Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_SHORT).show();
						}
				    }else
				    	Toast.makeText(getBaseContext(), "Please connect to a CSI hosted WiFi network!", Toast.LENGTH_SHORT).show();
				}
			});
		    
		    final EditText editTextPW = (EditText)findViewById(R.id.editTextPW);
		    final EditText editTextTeamName = (EditText)findViewById(R.id.EditTextTeamName);
		    final Button btnReg = (Button)findViewById(R.id.btnRegTeam);
		    final Button btnStart = (Button)findViewById(R.id.btnStart);
		    final Spinner spinnerChooseClass = (Spinner)findViewById(R.id.spinnerChooseClass);
		    btnReg.setOnClickListener(new View.OnClickListener()
		    {
				@Override
				public void onClick(View v)
				{
					if(editTextTeamName.getText().toString().trim().equals(""))
						Toast.makeText(getBaseContext(), "Please enter your the team ID provided to you.", Toast.LENGTH_SHORT).show();
					else if(editTextTeamName.getText().toString().trim().startsWith("D7A_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D7B_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D7C_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D6_TEAM_") ||editTextTeamName.getText().toString().trim().startsWith("D8_TEAM_") ||editTextTeamName.getText().toString().trim().startsWith("D9_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D10_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D11_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D12A_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D12B_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D13_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D14_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D15_TEAM_"))
					{
						if(editTextTeamName.getText().toString().trim().startsWith("D7A_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D7B_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D7C_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D10_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D11_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D13_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D14_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D15_TEAM_"))
						{
							if(editTextTeamName.getText().toString().length()==("D7A_TEAM_".length()+1))
								teamID = editTextTeamName.getText().toString();
							else
								Toast.makeText(getBaseContext(), "Invalid Team name, it must consists of 10 characters .. are you missing your team Number?", Toast.LENGTH_SHORT).show();
						}else if(editTextTeamName.getText().toString().trim().startsWith("D6_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D8_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D9_TEAM_")) 
						{
							if(editTextTeamName.getText().toString().length()==("D6_TEAM_".length()+1))
								teamID = editTextTeamName.getText().toString();
							else
								Toast.makeText(getBaseContext(), "Invalid Team name, it must consists of 9 characters .. are you missing your team Number?", Toast.LENGTH_SHORT).show();
						}else if(editTextTeamName.getText().toString().trim().startsWith("D12A_TEAM_") || editTextTeamName.getText().toString().trim().startsWith("D12B_TEAM_"))
						{
							if(editTextTeamName.getText().toString().length()==("D12A_TEAM_".length()+1))
								teamID = editTextTeamName.getText().toString();
							else
								Toast.makeText(getBaseContext(), "Invalid Team name, it must consists of 11 characters .. are you missing your team Number?", Toast.LENGTH_SHORT).show();
						}
					}
					else
						Toast.makeText(getBaseContext(), "Invalid team name", Toast.LENGTH_SHORT).show();
					
					if(!teamID.trim().equals(""))
					{
						if(editTextPW.getText().toString().trim().equals(""))
							Toast.makeText(getBaseContext(), "Please enter password", Toast.LENGTH_SHORT).show();
						else if(editTextPW.getText().toString().trim().equals("CSIVESIT"))
						{
							ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
						    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
						    
						    if(mWifi.isConnected())
						    {
								try
								{
									/*Socket clientSocket = new Socket();
									clientSocket.connect(new InetSocketAddress(serverIP = editTextServerIP.getText().toString(), 65535), 2000);
									clientSocket.setSoTimeout(5000);
									BufferedWriter toServerOPStrm = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
									ObjectInputStream fromServerOIS = new ObjectInputStream(clientSocket.getInputStream());*/
									toServerOPStrm.write("REG "+editTextTeamName.getText().toString()+System.getProperty("line.separator"));
									toServerOPStrm.flush();
									String serverReply = (String)fromServerOIS.readObject();
									if(serverReply!=null)
										if(serverReply.equals(editTextTeamName.getText().toString()+" REG Success!"))
										{
											teamID = editTextTeamName.getText().toString();
											Toast.makeText(getBaseContext(), "Team Name: "+teamID+" successfully registered with Server!", Toast.LENGTH_SHORT).show();
											btnReg.setEnabled(false);
											editTextPW.setEnabled(false);
											editTextTeamName.setEnabled(false);
											btnStart.setEnabled(true);
											spinnerChooseClass.setEnabled(false);
										}else
											Toast.makeText(getBaseContext(), "Unable to register your team name .. make sure its typed correctly!", Toast.LENGTH_SHORT).show();
									
									/*toServerOPStrm.close();
									fromServerOIS.close();
									clientSocket.close();*/
								}catch (Exception e) 
								{
									Toast.makeText(getBaseContext(), "Exception occured while connecting .. please retry.\nException Details: "+e.getMessage(), Toast.LENGTH_SHORT).show();
								}
						    }
						}else
							Toast.makeText(getBaseContext(), "Invalid Password!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		    btnStart.setOnClickListener(new View.OnClickListener()
		    {
				@Override
				public void onClick(View v)
				{
					startActivity(new Intent("org.csiVesit.wirelesselims.WEInstr"));
				}
			});
		    
		    final String[] Classes = getResources().getStringArray(R.array.Classes);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, Classes);
			
			spinnerChooseClass.setAdapter(adapter);
			spinnerChooseClass.setOnItemSelectedListener(new OnItemSelectedListener()
			{
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					int index = arg0.getSelectedItemPosition();
					if(Classes[index].equals("D6"))
						editTextTeamName.setText("D6_TEAM_");
					else if(Classes[index].equals("D7A"))
						editTextTeamName.setText("D7A_TEAM_");
					else if(Classes[index].equals("D7B"))
						editTextTeamName.setText("D7B_TEAM_");
					else if(Classes[index].equals("D7C"))
						editTextTeamName.setText("D7C_TEAM_");
					else if(Classes[index].equals("D8"))
						editTextTeamName.setText("D8_TEAM_");
					else if(Classes[index].equals("D9"))
						editTextTeamName.setText("D9_TEAM_");
					else if(Classes[index].equals("D10"))
						editTextTeamName.setText("D10_TEAM_");
					else if(Classes[index].equals("D11"))
						editTextTeamName.setText("D11_TEAM_");
					else if(Classes[index].equals("D12A"))
						editTextTeamName.setText("D12A_TEAM_");
					else if(Classes[index].equals("D12B"))
						editTextTeamName.setText("D12B_TEAM_");
					else if(Classes[index].equals("D13"))
						editTextTeamName.setText("D13_TEAM_");
					else if(Classes[index].equals("D14"))
						editTextTeamName.setText("D14_TEAM_");
					else if(Classes[index].equals("D15"))
						editTextTeamName.setText("D15_TEAM_");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0)
				{
					
				}
			});
	}
	
	private void CreateMenu(Menu menu)
	{
		menu.add(Menu.NONE, 0, 0, "About App!");
		//MenuItem aboutAppMnuItem;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return menuChoice(item);
	}

	private boolean menuChoice(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case 0:		//About App!
				startActivity(new Intent("org.csiVesit.wirelesselims.WECredits"));
			return true;
		}
		return false;
	}
}
