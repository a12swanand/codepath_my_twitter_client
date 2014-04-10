package com.codepath.apps.mytwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.mytwitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.mytwitter.fragments.MentionsFragment;
import com.codepath.apps.mytwitter.fragments.TweetsListFragment;

public class MyTimeLineActivity extends FragmentActivity implements TabListener {

	private static final int REQUEST_CODE = 0;


	TweetsAdapter adapter;
	TweetsListFragment tweetFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_time_line);
		
		SetNavigationSetup();
		
		
		//tweetFragment = (TweetsListFragment)getSupportFragmentManager().findFragmentByTag(R.id.);

		//loadResults();

	}

	private void SetNavigationSetup() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home").setIcon(R.drawable.ic_home_3).setTag("HomeTimeLineFragment").setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setIcon(R.drawable.ic_mentions_3).setTag("MentionsTimeLineFragment").setTabListener(this);
	
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
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

	public void onProfile(MenuItem mi) {

		Intent i = new Intent(getApplicationContext(),
				ComposeTweetActivity.class);
		startActivity(i);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			//refreshResults();
		} else {
			Toast.makeText(getBaseContext(), "Not Refreshed",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();

		if(tab.getTag() == "HomeTimeLineFragment") {
			fts.replace(R.id.frame_container, new HomeTimeLineFragment());
		} else {
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	

}
