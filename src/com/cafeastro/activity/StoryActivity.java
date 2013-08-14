package com.cafeastro.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafeastro.database.DatabaseHandler;
import com.cafeastro.database.Story;
import com.cafeastro.extra.ConnectionDetector;
import com.cafeastro.extra.ImageLoader;
import com.google.analytics.tracking.android.EasyTracker;

public class StoryActivity extends Activity {
	public int GetId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);

		// Adad ads
		//Adad.setTestMode(true);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			GetId = extras.getInt("id");
		}

		// Read page from DB
		DatabaseHandler db = new DatabaseHandler(this);
		Story item = db.getItem(GetId);

		// Set title for layout
		TextView showTitle = (TextView) findViewById(R.id.showtitle);
		showTitle.setText(item.getTitle());

		// Set body for layout
		TextView showBody = (TextView) findViewById(R.id.showbody);
		showBody.setText(item.getBody());

		// Check connection
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet(); // true or
																	// false
		if (!isInternetPresent) {
			showAlertDialog(StoryActivity.this);
		}

		// Imageview to show
		ImageView image = (ImageView) findViewById(R.id.showimage);
		// Image url
		String location_url = "http://www.cafeastro.net/uploads/news/image/medium/";
		String image_url = location_url + item.getImage();
		// ImageLoader class instance
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		imgLoader.DisplayImage(image_url, image);
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().setContext(this);
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().setContext(this);
		EasyTracker.getInstance().activityStop(this);
	}

	/**
	 * Function to display simple Alert Dialog
	 * 
	 * @param context
	 **/
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(getResources().getString(R.string.d_internet));

		// Setting Dialog Message
		alertDialog.setMessage(getResources().getString(
				R.string.d_internet_image));

		// Setting alert dialog icon
		alertDialog.setIcon(R.drawable.cancel);

		// Setting OK Button
		alertDialog.setButton(getResources().getString(R.string.d_ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		// Showing Alert Message
		alertDialog.show();
	}
}
