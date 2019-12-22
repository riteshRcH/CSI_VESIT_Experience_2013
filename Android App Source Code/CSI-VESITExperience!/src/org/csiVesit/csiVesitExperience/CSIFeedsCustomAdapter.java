package org.csiVesit.csiVesitExperience;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class CSIFeedsCustomAdapter extends ArrayAdapter<CSIRSSItem>
{
	Context context; 
    int layoutResourceId;    
    CSIRSSItem feeds[] = null;
    
    public CSIFeedsCustomAdapter(Context readNParseRSSActivity, int layoutResourceId, CSIRSSItem[] feeds)
    {
        super(readNParseRSSActivity, layoutResourceId, feeds);
        this.layoutResourceId = layoutResourceId;
        this.context = readNParseRSSActivity;
        this.feeds = feeds;
    }

    public CSIFeedsCustomAdapter(Context readNParseRSSActivity, int layoutResourceId)
    {
		// TODO Auto-generated constructor stub
    	super(readNParseRSSActivity, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = readNParseRSSActivity;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        CSIRSSFeedsPerRowLayoutHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new CSIRSSFeedsPerRowLayoutHolder();
            holder.txtviewFeedTitle = (TextView)row.findViewById(R.id.txtviewFeedTitle);
            holder.txtviewFeedDescription = (TextView)row.findViewById(R.id.txtviewFeedDescription);
            
            row.setTag(holder);
        }else
            holder = (CSIRSSFeedsPerRowLayoutHolder)row.getTag();
        
        CSIRSSItem rssItem = feeds[position];
        holder.txtviewFeedTitle.setText(rssItem.getFeedTitle().trim()+System.getProperty("line.separator")+"[ "+rssItem.getPubDate()+" ]");
        holder.txtviewFeedDescription.setText(feeds[position].getFeedDescription().trim());
        return row;
    }
    
    static class CSIRSSFeedsPerRowLayoutHolder
    {
        TextView txtviewFeedTitle, txtviewFeedDescription;
    }
}