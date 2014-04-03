package com.codepath.apps.mytwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.codepath.apps.mytwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.tweet_item, null);
		}

		Tweet tweet = getItem(position);

		ImageView ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		TextView tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
		ImageLoader.getInstance().displayImage(
				tweet.getUser().getProfileImageUrl(), ivProfile);

		String formattedName = "<b>" + tweet.getUser().getName() + "</b>"
				+ " <small><font color='#777777'>@"
				+ tweet.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));

		tvBody.setText(tweet.getBody());

		String timeStamp = "<small><font color='#777777'>"
		+ getRelativeDate(tweet.getCreatedAt()) + "</font></small>";
		tvTimestamp.setText(Html.fromHtml(timeStamp));
		
		return view;
	}

	private String getRelativeDate(String d) {
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str = "";

		if (date != null) {
			str = (DateUtils.getRelativeDateTimeString(

			getContext(), date.getTime(), DateUtils.MINUTE_IN_MILLIS,
					DateUtils.WEEK_IN_MILLIS, 0)).toString();
		}
		Log.d("DEBUG", "relative date : " + str);
		return str;
	}
}
