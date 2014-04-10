package com.codepath.apps.mytwitter.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytwitter.EndlessScrollListener;
import com.codepath.apps.mytwitter.MyTwitterApp;
import com.codepath.apps.mytwitter.MyTwitterClient;
import com.codepath.apps.mytwitter.R;
import com.codepath.apps.mytwitter.TweetsAdapter;
import com.codepath.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment {

	TweetsAdapter adapter;
	ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	PullToRefreshListView ltTweets = null;
	public long maxId = 0;
	public String requestType = "";

	private final String HOME_TIMELINE = "HomeTimeLine";
	private final String MENTION_TIMELINE = "MentionTimeLine";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
		ltTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getActivity(), tweetList);

		return v;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		ltTweets.setAdapter(adapter);

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

	public TweetsAdapter getAdapter() {
		return adapter;
	}


	public void refreshResults() {
		maxId = 0;
		adapter.clear();
		loadResults();

	}

	public void loadResults() {
		loadResults(requestType);
	}
	
	public void loadResults(String type, long max) {
		maxId  = 0;
		requestType = type;
		loadResults(requestType);
	}
	
	public void loadResults(String type) {
		
		if(type == HOME_TIMELINE){
			MyTwitterClient client = MyTwitterApp.getRestClient();
			//client.getHomeTimeLine(new JsonHttpResponseHandler() {
				client.getHomeTimeLine(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(JSONArray tweets) {
					
					loadTweets(tweets);
					ltTweets.onRefreshComplete();
				}
				
			}, maxId);

		} else {
			MyTwitterClient client = MyTwitterApp.getRestClient();
			//client.getHomeTimeLine(new JsonHttpResponseHandler() {
				client.getMentionsTimeLine(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(JSONArray tweets) {
					
					loadTweets(tweets);
					ltTweets.onRefreshComplete();
				}
				
			}, maxId);

		}

	}

	
	public void loadTweets(JSONArray tweets) {

		List<Tweet> tweetsLst = null;

			tweetsLst = Tweet.fromJson(tweets);
			adapter.addAll(tweetsLst);

	}
	
	
}
