package com.cafeastro.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cafeastro.extra.MainListAdapter;
import com.cafeastro.extra.WVersionManager;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity<Index> extends Activity {
	public ListView lv;
	public MainListAdapter adapter;

	public static final String KEY_TITLE = "title";
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String URL_VERSION = "http://service.faragostaresh.com/android/cafeastro/version/index.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Check for new version
		checkVersion();
		
		// Adad ads
		//Adad.setTestMode(true);
		
		// Set list array
		ArrayList<HashMap<String, String>> listview_main = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put(KEY_TITLE, "اخبار");
		map1.put(KEY_THUMBNAIL, "icon1");
		listview_main.add(map1);

		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put(KEY_TITLE, "دانشمندان");
		map2.put(KEY_THUMBNAIL, "icon2");
		listview_main.add(map2);

		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put(KEY_TITLE, "دانستنی ها");
		map3.put(KEY_THUMBNAIL, "icon4");
		listview_main.add(map3);

		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put(KEY_TITLE, "مریخ نورد کنجکاوی");
		map4.put(KEY_THUMBNAIL, "icon3");
		listview_main.add(map4);

		HashMap<String, String> map5 = new HashMap<String, String>();
		map5.put(KEY_TITLE, "درباره ما");
		map5.put(KEY_THUMBNAIL, "icon5");
		listview_main.add(map5);

		HashMap<String, String> map6 = new HashMap<String, String>();
		map6.put(KEY_TITLE, "تماس با ما");
		map6.put(KEY_THUMBNAIL, "icon6");
		listview_main.add(map6);

		// Getting adapter by ArrayList
		adapter = new MainListAdapter(this, listview_main);

		// Set custom list view
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(adapter);
		lv.setDivider(null);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					Intent cid1 = new Intent(getApplicationContext(),
							TopicActivity.class);
					cid1.putExtra("cid", 1);
					startActivity(cid1);
					break;

				case 1:
					Intent cid2 = new Intent(getApplicationContext(),
							TopicActivity.class);
					cid2.putExtra("cid", 2);
					startActivity(cid2);
					break;

				case 2:
					Intent cid3 = new Intent(getApplicationContext(),
							TopicActivity.class);
					cid3.putExtra("cid", 3);
					startActivity(cid3);
					break;

				case 3:
					Intent cid4 = new Intent(getApplicationContext(),
							TopicActivity.class);
					cid4.putExtra("cid", 4);
					startActivity(cid4);
					break;

				case 4:
					startActivity(new Intent(getApplicationContext(),
							AboutActivity.class));
					break;

				case 5:
					startActivity(new Intent(getApplicationContext(),
							ContactActivity.class));
					break;
				}
			}
		});
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

	private void checkVersion() {
		WVersionManager versionManager = new WVersionManager(this);

		versionManager.setVersionContentUrl(URL_VERSION);
		versionManager
				.setTitle(getResources().getString(R.string.update_title));
		versionManager.setUpdateNowLabel(getResources().getString(
				R.string.update_now));
		versionManager.setRemindMeLaterLabel(getResources().getString(
				R.string.update_remind_later));
		versionManager.setIgnoreThisVersionLabel(getResources().getString(
				R.string.update_ignore));
		versionManager.setReminderTimer(Integer.valueOf("1"));

		versionManager.checkVersion();
	}
}
