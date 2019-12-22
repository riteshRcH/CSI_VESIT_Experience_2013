package org.csiVesit.csiVesitExperience;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity 
{	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
		setContentView(R.layout.activity_main);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		/*TextView titleBar = (TextView)findViewById(android.R.id.title);
		if(titleBar!=null)
		{
			titleBar.setText("CSI-VESIT Experience!");
			titleBar.setTextColor(Color.WHITE);
			 ViewParent parent = titleBar.getParent();
			 if (parent != null && (parent instanceof View))
			 {
				 View parentView = (View)parent;
				 parentView.setBackgroundColor(Color.rgb(0x88, 0x33, 0x33));
			 }
		}*/
		
		if(customTitleSupported)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			TextView myTitleText = (TextView) findViewById(R.id.myTitle);
			myTitleText.setText("CSI-VESIT");
			//myTitleText.setTextColor(Color.rgb(0,0,139));
		}
		
		final Intent i = new Intent();
		i.setAction("org.csiVesit.csiVesitExperience.EventDisp");
		final Bundle extras = new Bundle(); //extra data to be passed while invoking intents = data to act upon by receiving activity that satisfies intent filtration
		
		final Button btnImpact = (Button)findViewById(R.id.btnImpact);
		btnImpact.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.SymposiumImpact);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnIV = (Button)findViewById(R.id.btnIV);
		btnIV.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.IV);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.iv1, R.drawable.iv2, R.drawable.iv3, R.drawable.iv4, R.drawable.iv5, R.drawable.iv6, R.drawable.iv7, R.drawable.iv8, R.drawable.iv9, R.drawable.iv10, R.drawable.iv11, R.drawable.iv12, R.drawable.iv13, R.drawable.iv14, R.drawable.iv15, R.drawable.iv16, R.drawable.iv17, R.drawable.iv18, R.drawable.iv19, R.drawable.iv20, R.drawable.iv21, R.drawable.iv22, R.drawable.iv23, R.drawable.iv24, R.drawable.iv25, R.drawable.iv26, R.drawable.iv27, R.drawable.iv28, R.drawable.iv29, R.drawable.iv30, R.drawable.iv31, R.drawable.iv32});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnHiddenCipher = (Button)findViewById(R.id.btnHiddenCipher);
		btnHiddenCipher.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.HiddenCipher);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.hc1, R.drawable.hc2});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnUltimateCoder = (Button)findViewById(R.id.btnUltimateCoder);
		btnUltimateCoder.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.UltimateCoder);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnEthicalHackin = (Button)findViewById(R.id.btnEthicalHackin);
		btnEthicalHackin.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.EthicalHacking);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.eh1, R.drawable.eh2, R.drawable.eh3, R.drawable.eh4, R.drawable.eh5, R.drawable.eh6});
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnPCAssembly = (Button)findViewById(R.id.btnPCAssembly);
		btnPCAssembly.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.PCAssembly);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnSwishMax = (Button)findViewById(R.id.btnSwishMax);
		btnSwishMax.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.SwishMax);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnExplorationUnravelled = (Button)findViewById(R.id.btnExplorationUnravelled);
		btnExplorationUnravelled.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.ExplorationUnravlled);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnFlashWorkshop = (Button)findViewById(R.id.btnFlashWorkshop);
		btnFlashWorkshop.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.Flash);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.flash1, R.drawable.flash2, R.drawable.flash3, R.drawable.flash4});
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnIITWorkShop = (Button)findViewById(R.id.btnIITWorkShop);
		btnIITWorkShop.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.IITWorkshop);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);			//bcoz of limited entries
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
/*	*************************************************************************************************************	*/
		
		final Button btnCodeBreaker = (Button)findViewById(R.id.BtnCodeBreaker);
		btnCodeBreaker.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.CodeBreaker);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.cb1, R.drawable.cb2, R.drawable.cb3, R.drawable.cb4});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button BtnCompoNect = (Button)findViewById(R.id.BtnCompoNect);
		BtnCompoNect.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.CompONect);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnVegasPro = (Button)findViewById(R.id.btnVegasPro);
		btnVegasPro.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.VegasPro);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnDotNetWorkshop = (Button)findViewById(R.id.btnDotNetWorkshop);
		btnDotNetWorkshop.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.DotNetWorkshop);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
/*	******************************************************************************************************************* */

		
		final Button BtnCashin = (Button)findViewById(R.id.BtnCashin);
		BtnCashin.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.CashIn);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.cashin1, R.drawable.cashin2, R.drawable.cashin4});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button BtnCricOmania = (Button)findViewById(R.id.BtnCricOmania);
		BtnCricOmania.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.cricOmania);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.cric1, R.drawable.cric2, R.drawable.cric3, R.drawable.cric4});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button BtnIdiotBox = (Button)findViewById(R.id.BtnIdiotBox);
		BtnIdiotBox.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.IdiotBox);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.ib1, R.drawable.ib2, R.drawable.ib3, R.drawable.ib4, R.drawable.ib5});
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", true);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button BtnTPP = (Button)findViewById(R.id.BtnTPP);
		BtnTPP.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.TPP);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button BtnVSM = (Button)findViewById(R.id.BtnVSM);
		BtnVSM.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.VSM);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", false);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnOpenSoftware = (Button)findViewById(R.id.btnOpenSoftware);
		btnOpenSoftware.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.OpenSoftware);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
/*	*************************************************************************************************************	*/
		
		final Button btnLanGamin = (Button)findViewById(R.id.BtnLanGamin);
		btnLanGamin.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.LANGaming);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", true);
				extras.putIntArray("ImgIDs", new int[]{R.drawable.lg1, R.drawable.lg2, R.drawable.lg3, R.drawable.lg4, R.drawable.lg5, R.drawable.lg5, R.drawable.lg6});
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnArticleWriting = (Button)findViewById(R.id.btnArticleWriting);
		btnArticleWriting.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.ArticleWriting);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnPyWorkshop = (Button)findViewById(R.id.btnPyWorkshop);
		btnPyWorkshop.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				String[] abtEvent = getResources().getStringArray(R.array.Python);
				extras.putString("EventName", abtEvent[0]);
				extras.putString("EventDescription", abtEvent[1]);
				extras.putString("WinnerCaption", null);
				extras.putString("EventWinners", null);
				extras.putBoolean("HasImgGallery", false);
				extras.putBoolean("HasRegistrations", true);
				extras.putBoolean("HasWirelessElims", false);
				i.putExtras(extras);
				startActivity(i);
			}
		});
		
		final Button btnCSIVESITNewsRSS = (Button)findViewById(R.id.btnCSIVESITNewsRSS);
		btnCSIVESITNewsRSS.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				startActivity(new Intent("org.csiVesit.csiVesitExperience.RSSFeedsDisplay"));
			}
		});
		
	}
	
	private void CreateMenu(Menu menu)
	{
		menu.add(Menu.NONE, 0, 0, "Settings");
		menu.add(Menu.NONE, 1, 1, "About CSI!");
		menu.add(Menu.NONE, 2, 2, "About App!");
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
			case 0:		//Settings
				startActivity(new Intent("org.csiVesit.csiVesitExperience.CSI_VESITAppSettings"));
			return true;
				
			case 1:		//About CSI
				startActivity(new Intent("org.csiVesit.csiVesitExperience.CSI_VESITAppAbtCSI"));
			return true;
			
			case 2:		//About App
				startActivity(new Intent("org.csiVesit.csiVesitExperience.CSI_VESITAppCredits"));
			return true;
		}
		return false;
	}
	
}
