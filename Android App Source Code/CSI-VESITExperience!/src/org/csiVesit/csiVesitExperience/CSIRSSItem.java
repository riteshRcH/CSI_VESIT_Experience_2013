package org.csiVesit.csiVesitExperience;

import java.net.URL;
import java.util.Date;

public class CSIRSSItem
{
	private String feedTitle;
	private String feedDescription;
	private URL toFeedURL;
	private Date pubDate;
	private long guid;
	public static URL RSSURL;
	
	static
	{
		try
		{
			RSSURL = new URL("http://www.csi-vesit.org/csi/feeds");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getFeedTitle()
	{
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle)
	{
		this.feedTitle = feedTitle;
	}
	public String getFeedDescription()
	{
		return feedDescription;
	}
	public void setFeedDescription(String feedDescription)
	{
		this.feedDescription = feedDescription;
	}
	public URL getToFeedURL()
	{
		return toFeedURL;
	}
	public void setToFeedURL(URL toFeedURL)
	{
		this.toFeedURL = toFeedURL;
	}
	public Date getPubDate() 
	{
		return pubDate;
	}
	public void setPubDate(Date pubDate)
	{
		this.pubDate = pubDate;
	}
	public long getGuid()
	{
		return guid;
	}
	public void setGuid(long guid)
	{
		this.guid = guid;
	}
	public String toString()
	{
		return getGuid()+") "+getFeedTitle()+System.getProperty("line.separator")+"[ "+getPubDate()+" ]"+System.getProperty("line.separator")+getFeedDescription()+System.getProperty("line.separator")+getToFeedURL();
	}
	public String getXMLToWrite()
	{
		return "<item>"+System.getProperty("line.separator")+"<guid>"+getGuid()+"</guid>"+System.getProperty("line.separator")+"<title>"+getFeedTitle()+"</title>"+System.getProperty("line.separator")+"<link>"+getToFeedURL()+"</link>"+System.getProperty("line.separator")+"<description>"+getFeedDescription()+"</description>"+System.getProperty("line.separator")+"<pubDate>"+getPubDate().toGMTString()+"</pubDate>"+System.getProperty("line.separator")+"</item>"+System.getProperty("line.separator");
	}
}
