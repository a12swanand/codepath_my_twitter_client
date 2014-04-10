package com.codepath.apps.mytwitter.fragments;

import android.os.Bundle;

public class HomeTimeLineFragment extends TweetsListFragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadResults("HomeTimeLine",0);
	}
	
	


}
