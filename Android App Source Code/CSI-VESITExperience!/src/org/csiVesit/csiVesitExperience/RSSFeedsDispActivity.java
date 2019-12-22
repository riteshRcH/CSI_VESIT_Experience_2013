package org.csiVesit.csiVesitExperience;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RSSFeedsDispActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ListView listView = getListView();
		listView.setChoiceMode(1);				//allow clicking only 1 list item
		listView.setTextFilterEnabled(true);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Hello", "Hello2"}));
	}
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Toast.makeText(this, "You have clicked a list item", Toast.LENGTH_SHORT).show();
	}
}
