package com.codepath.apps.mytwitter.models;

import java.io.Serializable;

import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model implements Serializable{

	private static final long serialVersionUID = 1451985324123200084L;

	private JSONObject jsonObject;

	public User() {
		
	}
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "usrId", unique = true)
	private long usrId;
	
	@Column(name = "screenName")
	private String screenName;
	
	@Column(name = "profileImageUrl")
	private String profileImageUrl;

	@Column(name = "profileBackgroundImageUrl")
	private String profileBackgroundImageUrl;

	@Column(name = "numTweets")
	private int numTweets;
	
	@Column(name = "followersCount")
	private int followersCount;

	@Column(name = "friendsCount")
	private int friendsCount;	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUsrId() {
		return usrId;
	}

	public void setUsrId(long id) {
		this.usrId = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public void setNumTweets(int numTweets) {
		this.numTweets = numTweets;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
	}

	public static User fromJson(JSONObject json) {
		User u = new User();
		
		try {
			u.jsonObject = json;
			u.setName(json.getString("name"));
			u.setUsrId(json.getLong("id"));
			u.setScreenName(json.getString("screen_name"));
			u.setScreenName(json.getString("screen_name"));
			u.setProfileImageUrl(json.getString("profile_image_url"));
			u.setProfileBackgroundImageUrl(json.getString("profile_background_image_url"));
			u.setNumTweets(json.getInt("statuses_count"));
			u.setFollowersCount(json.getInt("followers_count"));
			u.setFriendsCount(json.getInt("friends_count"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return u;
	}
	
}
