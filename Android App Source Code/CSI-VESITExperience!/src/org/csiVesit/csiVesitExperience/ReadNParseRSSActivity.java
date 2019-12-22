package org.csiVesit.csiVesitExperience;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReadNParseRSSActivity extends Activity
{
	File RSSFeedsXMLFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+".CSI VESIT Experience!"+File.separator+"RSSFeedsData"+File.separator+"RSSFeedsXML.xml");
	String RSSFileContent = "";
	LinkedHashMap<Long, CSIRSSItem> allRSSItems = new LinkedHashMap<Long, CSIRSSItem>();				//GUID and its respective RSSItem
	ListView listDisplayRSSFeeds;
	CSIRSSItem[] allRSSItemsArr = null;
	CSIFeedsCustomAdapter listAdapter;
	int initialRSSCount;
	ProgressDialog progressDialog;
	boolean hasInternetAccess = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_read_nparse_rss);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText("CSI-VESIT News!");
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		listDisplayRSSFeeds = ((ListView)findViewById(R.id.listDisplayRSSFeeds));
		listDisplayRSSFeeds.setAdapter(null);
		listDisplayRSSFeeds.setTextFilterEnabled(true);
	
		listDisplayRSSFeeds.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				Toast.makeText(getBaseContext(), "Redirecting to "+allRSSItemsArr[position].getToFeedURL(), Toast.LENGTH_SHORT).show();
			}
		});
		
		//initialLoadRSSFromFile();
		if(isNetworkAvailable())
			new UpdateRSS().execute(true, false);
		else
	    	Toast.makeText(getBaseContext(), "No network available!", Toast.LENGTH_SHORT).show();
		
		Button btnGetRSS = (Button)findViewById(R.id.btnGetRSS);
		btnGetRSS.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				//loadRSSItems(true);
				if(isNetworkAvailable())
					new UpdateRSS().execute(false, true);
				else
					Toast.makeText(getBaseContext(), "No network available!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	void initialLoadRSSFromFile()
	{
		//if file exists then load its RSS items else read whole XML File from internet and create file and then load its RSS Items
		try
		{
			BufferedReader br;
			boolean RSSFileExists;
			//load already received feeds or download from URL
			br = new BufferedReader((RSSFileExists = RSSFeedsXMLFile.exists())?new FileReader(RSSFeedsXMLFile):new InputStreamReader((CSIRSSItem.RSSURL).openConnection().getInputStream()));
			String temp = "";
			while((temp = br.readLine())!=null)
				RSSFileContent += temp + System.getProperty("line.separator");
			br.close();
			
			if(!RSSFileExists)
			{
				FileWriter fw = new FileWriter(RSSFeedsXMLFile);
				fw.write(RSSFileContent);
				fw.close();
			}
			loadRSSItems(false);					//load File RSS
		}catch(IOException ioe)	
		{
			ioe.printStackTrace();
		}
	}
	void displayRSS()
	{
		TreeMap<Long, CSIRSSItem> descSortedMap = new TreeMap<Long, CSIRSSItem>(Collections.reverseOrder());
		descSortedMap.putAll(allRSSItems);
		
		long index = 0;
		allRSSItemsArr = new CSIRSSItem[descSortedMap.size()];
		for(long i=allRSSItemsArr.length;i>=1;i--)
			allRSSItemsArr[(int) index++] = descSortedMap.get(i);
		listDisplayRSSFeeds.setAdapter(listAdapter = new CSIFeedsCustomAdapter(this, R.layout.listview_item_row, allRSSItemsArr));
	
		//listDisplayRSSFeeds.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, descSortedMap.values().toArray()));
		Toast.makeText(getBaseContext(), "RSS Feeds Updated", Toast.LENGTH_SHORT).show();
	}

	protected void loadRSSItems(boolean loadRSSFromSiteURL)
	{
		initialRSSCount = allRSSItems.size();
		try
		{
			XmlPullParser xpp = Xml.newPullParser();
		    xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		    if(loadRSSFromSiteURL)
		    	xpp.setInput(CSIRSSItem.RSSURL.openConnection().getInputStream(), "UTF-8");
		    else
		    	xpp.setInput(new StringReader(RSSFileContent));
		
		    boolean insideItem = false;												//to set a flag if parser is inside <item> because both <item> and <channel> have <title>
		    int eventType = xpp.getEventType();
		    CSIRSSItem rssItem = null;
		    while (eventType != XmlPullParser.END_DOCUMENT)
		    {
		        if (eventType == XmlPullParser.START_TAG)
		        {
		            if (xpp.getName().equalsIgnoreCase("item"))
		            {
		            	insideItem = true;
		            	rssItem = new CSIRSSItem();
		            }else if(xpp.getName().equalsIgnoreCase("guid") && insideItem)
		            {
		            	Long guid = Long.parseLong(xpp.nextText());
		            	if(allRSSItems.containsKey(guid))
		            		insideItem = false;
		            	else
		            		rssItem.setGuid(guid);
		            }else if(insideItem && xpp.getName().equalsIgnoreCase("title"))
		            		rssItem.setFeedTitle(xpp.nextText());
		            else if(insideItem && xpp.getName().equalsIgnoreCase("link"))
		            	rssItem.setToFeedURL(new URL(xpp.nextText()));
		            else if(insideItem && xpp.getName().equalsIgnoreCase("description"))
		            		rssItem.setFeedDescription(xpp.nextText());
		            else if(insideItem && xpp.getName().equalsIgnoreCase("pubDate"))
		            	rssItem.setPubDate(new Date(Date.parse(xpp.nextText())));
		            	//rssItem.setPubDate(new SimpleDateFormat("E,d MMM yyyy hh:mm:ss Z", Locale.ENGLISH).parse(xpp.nextText()));
		            
		        }else if(insideItem && eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
		        {
		        	insideItem = false;
		        	allRSSItems.put(rssItem.getGuid(), rssItem);
		        	rssItem = null;
		        }
		        eventType = xpp.next(); //move to next element
		    }
		}catch (MalformedURLException e)
		{
			e.printStackTrace();
		}catch (XmlPullParserException e)
		{
			//Toast.makeText(getBaseContext(), "An error occured while reading news,  please retry!", Toast.LENGTH_SHORT).show();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private class UpdateRSS extends AsyncTask<Boolean, Void, Void>
	{
		boolean usedURL = false;
		protected void onPreExecute()
		{
			progressDialog = new ProgressDialog(ReadNParseRSSActivity.this);
			progressDialog.setTitle("Processing ..");
			progressDialog.setMessage("Please wait.");
			progressDialog.setCancelable(true);
			progressDialog.setIndeterminate(true);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
		          public void onCancel(DialogInterface dialog)
		          {
		              UpdateRSS.this.cancel(true);
		              //finish() if you want to finish current activity
		          }
		    });
			progressDialog.show();
		}
		@Override
		protected Void doInBackground(Boolean... params)
		{
			if(hasInternetAccess = hasActiveInternetConnection())
			{
				if(params[0])										//auto unbox and 1st boolean var is initialLoadRSSFromFile(), 2nd var is loadRSSFromSiteURL as param to loadRSSItems
					initialLoadRSSFromFile();
				else
					loadRSSItems(usedURL = params[1]);
			}else
				this.cancel(true);
					
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			if(progressDialog!=null)
				progressDialog.dismiss();
			if(hasInternetAccess)
			{
				if(allRSSItems.size()>initialRSSCount)
				{
					displayRSS();
					if(usedURL)
						writeNewRSSItems();
				}else
					Toast.makeText(getBaseContext(), "News up to date, Stay tuned! ", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(getBaseContext(), "Make sure you are connected to the internet!", Toast.LENGTH_SHORT).show();
		}
		
		private void writeNewRSSItems()
		{
			try
			{
				/*String temp = "", fileContents = "";
				BufferedReader br = new BufferedReader(new FileReader(RSSFeedsXMLFile));
				while((temp=br.readLine())!=null)
				{
					fileContents += temp + System.getProperty("line.separator");
					if(temp.startsWith("</item>"))
						if(br.readLine().startsWith("</channel>"))
							break;
				}
				FileOutputStream fos = new FileOutputStream(RSSFeedsXMLFile);
				fos.getChannel().truncate(0);
				fos.write(fileContents.getBytes());
				for(long i=initialRSSCount+1;i<=allRSSItems.size();i++)
					fos.write(allRSSItems.get(i).getXMLToWrite().getBytes());
				fos.write(("</channel>"+System.getProperty("line.separator")+"</rss>").getBytes());
				fos.close();
				br.close();*/
				
				String temp="", fileContents = "";
				BufferedReader br = new BufferedReader(new FileReader(RSSFeedsXMLFile));
				while((temp = br.readLine())!=null)
					fileContents += temp;
				br.close();
				
				String toWriteXML = "";
				FileWriter fw = new FileWriter(RSSFeedsXMLFile);
				for(long i=initialRSSCount+1;i<=allRSSItems.size();i++)
					toWriteXML+=allRSSItems.get(i).getXMLToWrite();
				fw.write(fileContents.replace("</channel></rss>", toWriteXML));
				fw.write("</channel></rss>");
				fw.close();
				
				/*RandomAccessFile randomAccessFile = new RandomAccessFile(RSSFeedsXMLFile, "rw");
				randomAccessFile.seek(fileContents.lastIndexOf("</item>"));
				randomAccessFile.writeUTF("</item>"+System.getProperty("line.separator"));
				for(long i=initialRSSCount+1;i<=allRSSItems.size();i++)
					randomAccessFile.writeUTF(allRSSItems.get(i).getXMLToWrite());
				randomAccessFile.writeUTF("</channel>"+System.getProperty("line.separator")+"</rss>");
				randomAccessFile.close();*/
				
				/*for(long i=initialRSSCount+1;i<=allRSSItems.size();i++)
					Toast.makeText(getBaseContext(), allRSSItems.get(i).getXMLToWrite(), Toast.LENGTH_SHORT).show();*/
			}catch(Exception e)
			{
				Log.d("debug", e.getMessage());
				Toast.makeText(getBaseContext(), "Cant Update Files .. retry", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		protected void onCancelled()
		{
			
		}
	}
	public boolean hasActiveInternetConnection()
	{
	    try
	    {
	    	HttpURLConnection urlConn = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	    	urlConn.setRequestProperty("User-Agent", "Test");
	        urlConn.setRequestProperty("Connection", "close");
	        urlConn.setConnectTimeout(1000); 
	        urlConn.connect();
	        return (urlConn.getResponseCode() == 200);
	    }catch (IOException e)
	    {
	    	e.printStackTrace();
	    }
	    	
	    return false;
	}
	private boolean isNetworkAvailable()
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnectedOrConnecting();
	}
}
