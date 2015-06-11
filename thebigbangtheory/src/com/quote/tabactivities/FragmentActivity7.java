package com.quote.tabactivities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tbbtquotes.R;
import com.quote.globals.Globals;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentActivity7 extends Fragment {

	// Hashmap for ListView
	ArrayList<Item> imageArry = new ArrayList<Item>();
	CustomQuoteAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_fragment_activity1,
				container, false);
		new GetQuotesInfo().execute();
		adapter = new CustomQuoteAdapter(getActivity(), R.layout.list,
				imageArry);
		final ListView dataList = (ListView) rootView.findViewById(R.id.list);
		dataList.setAdapter(adapter);
		adapter.clear();

		dataList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Item selectedItem = (Item) (dataList
						.getItemAtPosition(position));
				String finalContentSubject = selectedItem.heading + "\n";
				String finalContent = "";
				for (int i = 0; i < selectedItem.getName().length; i++) {
					finalContent += selectedItem.author[i];
					finalContent += ":\n";
					finalContent += selectedItem.name[i];
					finalContent += "\n";
				}
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = finalContent;
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						finalContentSubject);
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});
		return rootView;
	}

	// Async Task to load data
	class GetQuotesInfo extends AsyncTask<Void, Void, Void> {
		int count;

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					adapter.notifyDataSetChanged();

				}
			});
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				JSONArray jsonarr = new JSONArray(Globals.jsonTextBernadette);
				count = jsonarr.length();
				for (int i = 0; i < count; i++) {
					JSONObject jsonobj = jsonarr.getJSONObject(i);
					final String name = jsonobj.getString("name");
					JSONArray jarr = jsonobj.getJSONArray("quotes");
					final String[] myarrText = new String[jarr.length()];
					final String[] myarrAuthor = new String[jarr.length()];

					for (int j = 0; j < jarr.length(); j++) {
						JSONObject jobj = jarr.getJSONObject(j);
						String author = jobj.getString("author");
						String text = jobj.getString("text");
						myarrText[j] = text;
						myarrAuthor[j] = author;
					}

					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							Item item = new Item(name, myarrAuthor, myarrText);
							imageArry.add(item);
							adapter.notifyDataSetChanged();
						}
					});
					/**
					 * Used to publish one or more units of progress. These
					 * values are published on the UI thread, in the
					 * onProgressUpdate(Progress...) step.
					 */
					// publishProgress();
				}
			} catch (Exception ex) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
