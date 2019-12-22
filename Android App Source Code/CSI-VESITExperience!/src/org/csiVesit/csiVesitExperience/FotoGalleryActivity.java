package org.csiVesit.csiVesitExperience;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class FotoGalleryActivity extends Activity  implements ViewFactory
{
	
	private ImageSwitcher imageSwitcher1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto_gallery);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		imageSwitcher1 = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		imageSwitcher1.setFactory(this);
		imageSwitcher1.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imageSwitcher1.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		
		Gallery gallery = (Gallery)findViewById(R.id.Gallery1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
			{
				imageSwitcher1.setImageResource(EventDispActivity.imgIDs[position]);
			}
		});
	}
	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		private int itemBackground;
		
		public ImageAdapter(Context c)
		{
			context = c;
			TypedArray a = obtainStyledAttributes(R.styleable.gallery1);
			itemBackground = a.getResourceId(R.styleable.gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		@Override
		public int getCount()
		{
			return EventDispActivity.imgIDs.length;
		}

		@Override
		public Object getItem(int position) 
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(EventDispActivity.imgIDs[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
			imageView.setBackgroundResource(itemBackground);
			
			return imageView;
		}
	}
	@Override
	public View makeView()
	{
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

}
