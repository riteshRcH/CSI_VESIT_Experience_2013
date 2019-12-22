package org.csiVesit.csiVesitExperience;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

public class SplashActivity extends Activity 
{
	static boolean showSplashScreen = true;
	private Thread mSplashThread;
	File CSIVESITAppDir = new File(Environment.getExternalStorageDirectory(), ".CSI VESIT Experience!");
	File HiddenCipherAppDir = new File(CSIVESITAppDir, "HiddenCipher");
	File codeBreakerAppDir = new File(CSIVESITAppDir, "CodeBreaker");
	File lanGamingAppDir = new File(CSIVESITAppDir, "LANGaming");
	File componectAppDir = new File(CSIVESITAppDir, "CompONect");
	File articleWritingAppDir = new File(CSIVESITAppDir, "ArticleWriting");
	File tppAppDir = new File(CSIVESITAppDir, "TPP");
	File openSoftwareAppDir = new File(CSIVESITAppDir, "OpenSoftware");
	File ultimateCoderAppDir = new File(CSIVESITAppDir, "UltimateCoder");
	File VSMAppDir = new File(CSIVESITAppDir, "VSM");
	File cricomaniaAppDir = new File(CSIVESITAppDir, "CricOmania");
	File idiotBoxAppDir = new File(CSIVESITAppDir, "IdiotBox");
	File explorationUnravlledAppDir = new File(CSIVESITAppDir, "ExplorationUnravelled");
	File cashinAppDir = new File(CSIVESITAppDir, "Cashin");
	File symposiumImpactAppDir = new File(CSIVESITAppDir, "ImpactSymposium");
	File ethicalHackingAppDir = new File(CSIVESITAppDir, "EthicalHacking");
	File PCAssemblyAppDir = new File(CSIVESITAppDir, "PCAssembly");
	File vegasProAppDir = new File(CSIVESITAppDir, "VegasPro");
	File IVAppDir = new File(CSIVESITAppDir, "IV");
	File memberNamesAppDir = new File(CSIVESITAppDir, "memberNames");
	File rssFeedsDataAppDir = new File(CSIVESITAppDir, "RSSFeedsData");
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
      
        File[] temp = {CSIVESITAppDir, IVAppDir, vegasProAppDir, PCAssemblyAppDir, ethicalHackingAppDir, symposiumImpactAppDir, cashinAppDir, explorationUnravlledAppDir, idiotBoxAppDir, cricomaniaAppDir, VSMAppDir, ultimateCoderAppDir, openSoftwareAppDir, tppAppDir, HiddenCipherAppDir, codeBreakerAppDir, lanGamingAppDir, componectAppDir, articleWritingAppDir, memberNamesAppDir, rssFeedsDataAppDir};
        for(File f:temp)
        	if(!f.exists())
        		f.mkdirs();
        
        if(showSplashScreen)
        {
        	setContentView(R.layout.activity_splash);
        
        	//final SplashActivity sPlashScreen = this;   
        
        // 	The thread to wait for splash screen events
        	mSplashThread =  new Thread()
        	{
        		@Override
        		public void run()
        		{
        			try 
        			{
        				//synchronized(this)
        				//{
                        	// Wait given period of time or exit on touch
                        		Thread.sleep(2000);
                        		//}
        			}
        			catch(InterruptedException ex)
        			{
        			}
        			finish();
        			startActivity(new Intent("org.csiVesit.csiVesitExperience.MainActivity"));
        		}
        	};
        
        	mSplashThread.start();
        	}else
        	{
        		finish();
            	startActivity(new Intent("org.csiVesit.csiVesitExperience.MainActivity"));
        	}
        	
    }


    public boolean onTouchEvent(MotionEvent me)
    {
    	if(me.getAction() == MotionEvent.ACTION_DOWN)
    		synchronized (mSplashThread) 
    		{
    			mSplashThread.stop();
			}
    	return true;
    }
    
}
