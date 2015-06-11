package com.quote.tabactivities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbbtquotes.R;

@SuppressLint("ViewHolder")
public class CustomQuoteAdapter extends ArrayAdapter<Item> {
	Context context;
	int layoutResourceId;
	LinearLayout linearMain;
	ArrayList<Item> data = new ArrayList<Item>();

	public CustomQuoteAdapter(Context context, int layoutResourceId,
			ArrayList<Item> data) {
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;

		/**
		 * LayoutInflater inflater =
		 * (LayoutInflater)context.getSystemService(Context
		 * .LAYOUT_INFLATER_SERVICE);
		 */
		LayoutInflater inflater = ((FragmentActivity) context)
				.getLayoutInflater();

		/***
		 * inflate(int resource, ViewGroup root, boolean attachToRoot) resource
		 * : ID for an XML layout resource to load (e.g., R.layout.main_page)
		 * root : if attachToRoot = true -> Optional view to be parent of
		 * generated hierarchy if attachToRoot = false -> simply an object that
		 * provides set of LayoutParams values for root of the returned
		 * hierarchy. attachToRoot : whether the inflated hierarchy should ne
		 * attached to Root parameter if false : root is only used to create the
		 * correct subclass of LayoutParams for the root view in the XML.
		 * Inflate a new view hierarchy from the specified xml resource.
		 */
		row = inflater.inflate(layoutResourceId, parent, false);

		linearMain = (LinearLayout) row.findViewById(R.id.lineraMain);
		Item myImage = data.get(position);

		// Heading customization : Set to Blue, size 20 and center
		TextView txtview = new TextView(context);
		String heading = myImage.heading;
		txtview.setText(heading);
		txtview.setTextColor(Color.BLUE);
		txtview.setTextSize(20);
		txtview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		linearMain.addView(txtview);

		for (int j = 0; j < myImage.getName().length; j++) {
			TextView labelAuthorName = new TextView(context);
			labelAuthorName.setText(myImage.author[j]);
			labelAuthorName.setTypeface(null, Typeface.BOLD);
			linearMain.addView(labelAuthorName);
			TextView labelConversation = new TextView(context);
			labelConversation.setText(myImage.name[j]);
			linearMain.addView(labelConversation);
		}
		return row;
	}
}
