package com.cafeastro.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cafeastro.database.DatabaseHandler;
import com.cafeastro.database.Story;
import com.google.analytics.tracking.android.EasyTracker;

public class TopicActivity extends Activity {

	public int GetId;
	public int SetId;

	private ListView lv;
	private ArrayList<String> listview_title = new ArrayList<String>();
	private ArrayList<Integer> listview_id = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			GetId = extras.getInt("cid");
		}

		// Read page from DB
		DatabaseHandler db = new DatabaseHandler(this);
		List<Story> stores = db.getAllItemCid(GetId);

		// Add to array
		for (Story cn : stores) {
			listview_title.add(cn.getTitle());
			listview_id.add(cn.getId());
		}

		// Make list view
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,
				R.layout.activity_topic_list, listview_title));
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SetId = listview_id.get(arg2);

				Intent item = new Intent(getApplicationContext(),
						StoryActivity.class);
				item.putExtra("id", SetId);
				startActivity(item);
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
}
