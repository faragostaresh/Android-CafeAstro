package com.cafeastro.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ImageView img1 = (ImageView) findViewById(R.id.faragostaresh);
		img1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse("http://www.faragostaresh.com/"));
				startActivity(intent);
			}
		});

		/*
		 * ImageView img2 = (ImageView)findViewById(R.id.cafeastro);
		 * img2.setOnClickListener(new View.OnClickListener(){ public void
		 * onClick(View v){ Intent intent = new Intent();
		 * intent.setAction(Intent.ACTION_VIEW);
		 * intent.addCategory(Intent.CATEGORY_BROWSABLE);
		 * intent.setData(Uri.parse("http://www.cafeastro.net//"));
		 * startActivity(intent); } });
		 */
	}
}
