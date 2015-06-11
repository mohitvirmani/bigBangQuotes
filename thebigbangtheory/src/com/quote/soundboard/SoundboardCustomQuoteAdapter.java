package com.quote.soundboard;

import java.util.ArrayList;

import com.tbbtquotes.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SoundboardCustomQuoteAdapter extends ArrayAdapter<SoundboardItem> {
	Context context;
	int layoutResourceId;
	LinearLayout linearMain;
	ArrayList<SoundboardItem> data = new ArrayList<SoundboardItem>();

	public SoundboardCustomQuoteAdapter(Context context, int layoutResourceId,
			ArrayList<SoundboardItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	/**
	 * LayoutInfiltrator : When you use a custom view in a ListView, you must
	 * define the row layout. Create an XML where to place android widgets and
	 * then in the Adapter's code.
	 */
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		LayoutInflater inflater = ((FragmentActivity) context)
				.getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		linearMain = (LinearLayout) row.findViewById(R.id.lineraMain);
		SoundboardItem myImage = data.get(position);

		// Heading customization : Set to Blue, size 20 and center
		TextView txtview = new TextView(context);
		String heading = myImage.name[0];
		txtview.setText(heading);
		txtview.setTextColor(Color.BLUE);
		txtview.setTextSize(20);
		txtview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(0, 30, 0, 30); // llp.setMargins(left, top, right,
										// bottom);
		txtview.setLayoutParams(llp);
		linearMain.addView(txtview);

		// for (int j = 0; j < myImage.getName().length; j++) {
		// TextView labelAuthorName = new TextView(context);
		// labelAuthorName.setText(myImage.author[j]);
		// labelAuthorName.setTypeface(null, Typeface.BOLD);
		// linearMain.addView(labelAuthorName);
		// TextView labelConversation = new TextView(context);
		// labelConversation.setText(myImage.name[j]);
		// linearMain.addView(labelConversation);
		// }
		return row;
	}
}
