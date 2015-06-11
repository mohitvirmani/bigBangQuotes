package com.quote.activities;

import com.tbbtquotes.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class WebViewFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Retrieving the currently selected item number
		int position = getArguments().getInt("position");
		String url = getArguments().getString("url");
		// List of rivers
		String[] menus = getResources().getStringArray(R.array.menus);

		// Creating view corresponding to the fragment
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		super.onCreate(savedInstanceState);

		// Updating the action bar title
		getActivity().getActionBar().setTitle(menus[position]);

		GridView gridview = (GridView) getActivity()
				.findViewById(R.id.gridview);

		// Context context = getApplicationContext();
		// GridView gridview = (GridView) findViewById(R.id.gridview);
		// gridview.setAdapter(new ImageAdapter(getContext()));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});

		// Initializing and loading url in webview
		// WebView webView = (WebView)v.findViewById(R.id.webView);
		// webView.getSettings().setJavaScriptEnabled(true);
		// webView.loadUrl(url);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
}