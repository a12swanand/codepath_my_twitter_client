package com.codepath.apps.mytwitter.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet extends Model implements Serializable{

	private static final long serialVersionUID = 2498106282260872746L;

	private JSONObject jsonObject;

	public Tweet() {
		
	}
	
	@Column(name = "user")
	private User user;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "tweetId", unique = true)
	private long tweetId;
	
	@Column(name = "favourited")
	private boolean favourited;
	
	@Column(name = "retweeted")
	private boolean retweeted;
	
	@Column(name = "createdAt")
	private String createdAt;
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public long getTweetId() {
		return tweetId;
	}


	public void setTweetId(long id) {
		this.tweetId = id;
	}


	public boolean isFavourited() {
		return favourited;
	}


	public void setFavourited(boolean favourited) {
		this.favourited = favourited;
	}


	public boolean isRetweeted() {
		return retweeted;
	}


	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}


	public String getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public static Tweet fromJson(JSONObject json) {

		Tweet tw = new Tweet();
		
		try {
			tw.jsonObject = json;
			tw.setUser(User.fromJson(json.getJSONObject("user")));
			tw.setBody(json.getString("text"));
			tw.setTweetId(json.getLong("id"));
			tw.setFavourited(json.getBoolean("favorited"));
			tw.setRetweeted(json.getBoolean("retweeted"));
			tw.setCreatedAt(json.getString("created_at"));
		} catch (Exception e) {
			return null;
		}
		
		return tw;
	}
	
	
	public static ArrayList<Tweet> fromJson(JSONArray jsonArray){
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for(int i=0; i < jsonArray.length(); i++){
			
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = fromJson(tweetJson);
			if(tweet != null){
				tweets.add(tweet);
			}
		}
		
		return tweets;
	}
}
