package com.codepath.apps.mytwitter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.codepath.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class MyTimeLineActivity extends Activity {

	private static final int REQUEST_CODE = 0;
	public long maxId = 0;

	TweetsAdapter adapter;
	ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	PullToRefreshListView ltTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_time_line);

		ltTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);

		adapter = new TweetsAdapter(this, tweetList);
		ltTweets.setAdapter(adapter);
		loadResults();

		ltTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				if (tweetList != null && tweetList.size() > 0) {
					maxId = tweetList.get(0).getTweetId();
					for (Tweet tweet : tweetList) {
						if (tweet.getTweetId() < maxId) {
							maxId = tweet.getTweetId();
						}
					}
				}
				loadResults();
			}
		});

		ltTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				refreshResults();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_time_line, menu);
		return true;
	}

	public void onComposeclicked(MenuItem mi) {

		Intent i = new Intent(getApplicationContext(),
				ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			refreshResults();
		} else {
			Toast.makeText(getBaseContext(), "Not Refreshed",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void loadResults() {

		MyTwitterClient client = MyTwitterApp.getRestClient();
		client.getHomeTimeLine(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray tweets) {

				loadTweets(tweets, true);
				ltTweets.onRefreshComplete();
			}
			
			 @Override
			public void onFailure(Throwable arg0, JSONArray tweets) {
				Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
				loadTweets(tweets, false);
				ltTweets.onRefreshComplete();
			}
		}, maxId);
	}

	public void loadTweets(JSONArray tweets, boolean flag) {

		List<Tweet> tweetsLst = null;

		if (flag) {
			tweetsLst = Tweet.fromJson(tweets);

			adapter.addAll(tweetsLst);

			for (Tweet tweet : tweetsLst) {
				tweet.getUser().save();
				tweet.save();
			}

		} else {
			// Load offline
			if (maxId > 0) {
				tweetsLst = new Select().from(Tweet.class).limit(25)
						.where("tweetId < ?", maxId).orderBy("tweetId DESC")
						.execute();
			} else {
				adapter.clear();
				tweetsLst = new Select().from(Tweet.class).limit(25)
						.orderBy("tweetId DESC").execute();
			}
			
			adapter.addAll(tweetsLst);
		}

	}

	public void refreshResults() {
		maxId = 0;
		adapter.clear();
		loadResults();

	}
}
