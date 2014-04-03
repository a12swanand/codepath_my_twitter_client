package com.codepath.apps.mytwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {

	User user;
	
	ImageView ivComposeProfile;
	TextView tvComposeName;
	EditText etComposeTweet;
	TextView tvCharLeftVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
		ivComposeProfile = (ImageView)findViewById(R.id.ivComposeProfile);
		tvComposeName = (TextView)findViewById(R.id.tvComposeName);
		etComposeTweet = (EditText)findViewById(R.id.etComposeTweet);
		tvCharLeftVal = (TextView)findViewById(R.id.tvCharLeftVal);
		
		getMyInfo();

		etComposeTweet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				int tweetLength = s.length();
				int charsLeft = 140 - tweetLength;
				tvCharLeftVal.setText(String.valueOf(charsLeft));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

	public void postTweetUpdate(View v) {
		postResults();
	}
	
	public void cancelUpdate(View v) {
		setResult(RESULT_OK, getIntent());
		finish();
		
	}
	
	public void setup(){
		if(user != null){
		ImageLoader.getInstance().displayImage(
				user.getProfileImageUrl(), ivComposeProfile);

		String formattedName = "<b>" + user.getName() + "</b>"
				+ " <small><font color='#777777'>@"
				+ user.getScreenName() + "</font></small>";
		tvComposeName.setText(Html.fromHtml(formattedName));
		} else {
			Log.d("DEBUG", "User info unavailable");
			
		}
	}
	
	
	public void postResults() { 
		
		MyTwitterClient client = MyTwitterApp.getRestClient();
		String newTweet = etComposeTweet.getText().toString();
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject arg0) {
				setResult(RESULT_OK, getIntent());
				finish();
			}
		}, newTweet);
		
	}
	
	public void getMyInfo() {

		MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject obj) {
				getInfoFromJson(obj);
				setup();

			}

			@Override
			public void onFailure(Throwable arg0) {
				
				Log.d("DEBUG", "myInfo JSON call failed");
			}
				
		});

	}
	
	public void getInfoFromJson(JSONObject json){
		user = User.fromJson(json);
		
	}
	
}
