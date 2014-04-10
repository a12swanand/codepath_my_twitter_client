package com.codepath.apps.mytwitter.fragments;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadResults("MentionTimeLine",0);
	}
}
