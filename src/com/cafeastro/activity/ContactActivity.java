package com.cafeastro.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cafeastro.extra.ConnectionDetector;

public class ContactActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		// Check connection
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet(); // true or
																	// false
		if (!isInternetPresent) {
			showAlertDialog(ContactActivity.this);
		}

		// Do after press send button
		final Button send = (Button) this.findViewById(R.id.sendemail_intent);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Send As Post
				sendAsPost();
				// Back to index
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
			}
		});
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
				R.string.d_internet_contact));

		// Setting alert dialog icon
		alertDialog.setIcon(R.drawable.cancel);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public void sendAsPost() {
		// Make and Run Thread for send form as post
		new Thread(new Runnable() {
			@Override
			public void run() {

				// Get , Set , Check Email
				EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
				String TextEmail = editTextEmail.getText().toString();
				if (TextEmail.matches("")) {

				}

				// Get , Set , Check Name
				EditText editTextName = (EditText) findViewById(R.id.editTextName);
				String TextName = editTextName.getText().toString();
				if (TextName.matches("")) {

				}

				// Get , Set , Check Subject
				EditText editTextSubject = (EditText) findViewById(R.id.editTextSubject);
				String TextSubject = editTextSubject.getText().toString();
				if (TextSubject.matches("")) {

				}

				// Get , Set , Check Message
				EditText editTextMessage = (EditText) findViewById(R.id.editTextMessage);
				String TextMessage = editTextMessage.getText().toString();
				if (TextMessage.matches("")) {

				}

				// Send
				HttpClient postClient = new DefaultHttpClient();
				String postReq = "http://faragostaresh.com/modules/contact/ajax.php";
				HttpPost request = new HttpPost(postReq);
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				postParams
						.add(new BasicNameValuePair("contact_mail", TextEmail));
				postParams
						.add(new BasicNameValuePair("contact_name", TextName));
				postParams.add(new BasicNameValuePair("contact_subject",
						TextSubject));
				postParams.add(new BasicNameValuePair("contact_message",
						TextMessage));
				UrlEncodedFormEntity postEntity = null;
				try {
					postEntity = new UrlEncodedFormEntity(postParams, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				request.setEntity(postEntity);
				@SuppressWarnings("unused")
				HttpResponse response = null;
				try {
					response = postClient.execute(request);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
