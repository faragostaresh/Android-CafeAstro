package com.cafeastro.activity;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cafeastro.database.DatabaseHandler;
import com.cafeastro.database.Story;
import com.cafeastro.extra.ConnectionDetector;
import com.google.analytics.tracking.android.EasyTracker;

public class SplashActivity extends Activity {

	// splash screen delay time
	// private static final int SPLASH_DISPLAY_TIME = 3000;

	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// Check connection
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();

		// Set Database and get count
		DatabaseHandler db = new DatabaseHandler(this);
		int count = db.getStoryCount();

		// Check Database and connection
		if (count > 0) {
			if (!isInternetPresent) {
				showAlertDialog(SplashActivity.this,
						getResources().getString(R.string.d_internet_access));
			} else {
				new GetLstStory().execute();
				// showHandler();
			}
		} else {
			if (!isInternetPresent) {
				showAlertDialog(
						SplashActivity.this,
						getResources().getString(
								R.string.d_internet_access_count));
			} else {
				new GetLstStory().execute();
				// showHandler();
			}
		}

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

	private class GetLstStory extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(SplashActivity.this);
			progressDialog.setTitle(getResources().getString(
					R.string.d_processing));
			progressDialog
					.setMessage(getResources().getString(R.string.d_wait));
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... ars) {

			DatabaseHandler db = new DatabaseHandler(SplashActivity.this);
			int lastid = db.getStoryLastId();

			String url = "http://www.cafeastro.net/modules/news/ajax.php?op=story&storyid="
					+ lastid + "&limit=100";

			// TODO 4 new activity with custom adapter to show schedules
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				JSONArray ja = new JSONArray(client.execute(get,
						responseHandler));
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					db.addItem(new Story(jo.getInt("story_id"), jo
							.getInt("story_topic"),
							jo.getString("story_title"), jo
									.getString("story_body"), jo
									.getString("story_img"), jo
									.getString("story_publish")));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			progressDialog.dismiss();

			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, MainActivity.class);

			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
		}
	}

	/**
	 * Function to display simple Alert Dialog
	 * 
	 * @param context
	 **/
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String Message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(getResources().getString(R.string.d_internet));

		// Setting Dialog Message
		alertDialog.setMessage(Message);

		// Setting alert dialog icon
		alertDialog.setIcon(R.drawable.cancel);

		// Setting OK Button
		alertDialog.setButton(getResources().getString(R.string.d_ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(getApplicationContext(),
								MainActivity.class));
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	/*
	 * public void showHandler() { new Handler().postDelayed(new Runnable() {
	 * public void run() {
	 * 
	 * Intent intent = new Intent(); intent.setClass(SplashActivity.this,
	 * MainActivity.class);
	 * 
	 * SplashActivity.this.startActivity(intent); SplashActivity.this.finish();
	 * 
	 * } }, SPLASH_DISPLAY_TIME); }
	 */
}
