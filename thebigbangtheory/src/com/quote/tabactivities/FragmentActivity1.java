package com.quote.tabactivities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tbbtquotes.R;
import com.quote.globals.Globals;

public class FragmentActivity1 extends Fragment {

	public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";
	ArrayList<Item> imageArry = new ArrayList<Item>();
	CustomQuoteAdapter adapter;

	/**
	 * Taking documentation examples from
	 * http://docstore.mik.ua/orelly/java-ent/jnut/ch07_03.htm
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/**
		 * Official DOCS : layoutInfiltrator class is used to instantiate layout
		 * XML file into it's corresponding views objects. In other words, it
		 * takes input as an XML file and builds view objects from it. it is
		 * never used directly. Instead use getLayoutInfiltrator() to retrieve a
		 * standard layout infiltrator instance that is already hooked up to the
		 * current context and correctly configured for the device you are
		 * running on. LayoutInflater inflater : Create a dynamic layout We
		 * inflate the XMl which gives us the view. TODO : ViewGroup container
		 * TODO : false
		 */
		View rootView = inflater.inflate(R.layout.activity_fragment_activity1,
				container, false);
		/**
		 * Asynchronous call to be executed synchronously, for AsyncTask method
		 * to be executed getQuotesInfo, name of the class, for which AsyncTask
		 * is subclassed. .execute() to start the AsyncTask. .get() to execute
		 * the Async task synchronously,i.e., waits if necessary for the
		 * computation to complete and then retrieves its result.
		 */
		new GetQuotesInfo().execute();

		/**
		 * CustomQuoteAdapter getActivity() provides context, the current
		 * context R.layout.list : provides the layout's ID. (Custom)imageArry :
		 * provides the array of Items. Creates an instance of the
		 * CustomQuoteAdapter providing current context, ID of the list to be
		 * populated and the Item. fragment : getActivity(), else if Activity :
		 * getApplicationContect
		 */

		adapter = new CustomQuoteAdapter(getActivity(), R.layout.list,
				imageArry);

		/**
		 * Gets the list's reference from the current context.
		 * view.findViewById(int id or R.id.list) Looks for a child view with
		 * the given ID.
		 */
		final ListView dataList = (ListView) rootView.findViewById(R.id.list);

		/**
		 * Populate the row's XML with the info from Item. set the
		 * CustomQuoteAdapter view in the current list. adapter : is responsible
		 * for maintaining the data backing this List, and for producing a view
		 * to represent an item in that data set.
		 */
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

		/**
		 * Return the view generated.
		 */
		return rootView;
	}

	/**
	 * AsyncTask runs asynchronously onCreation of the current context.
	 * AsyncTask : enables proper and easy use of the UI thread. This class
	 * allows to perform background operations and publish results on the UI
	 * thread without having to manipulate threads and/or handlers.
	 * 
	 * AsyncTasks : should be used for short operations, a few seconds at the
	 * most.
	 * 
	 * If needed to run threads for long periods of time, check the API provided
	 * by java.util.concurrent package such as: Executor, ThreadPoolExecutor,
	 * and FutureTask.
	 * 
	 * AsyncTask : An AsyncTask is defined by a computation that runs on a
	 * background and whose result is published on the UI thread. Defined by 3
	 * generic types: Params, Progress, Result. 4 steps: onPreExecute,
	 * doInBackground, onProgressUpdate, onPostExecute
	 * 
	 * AsyncTask must be subclassed to be used. The subclass will override at
	 * least one method
	 * 
	 * The three types used by an asynchronous task are the following: Params,
	 * the type of the parameters sent to the task upon execution. Progress, the
	 * type of the progress units published during the background computation.
	 * Result, the type of the result of the background computation. Not all
	 * types are always used by an asynchronous task. To mark a type as unused,
	 * simply use the type Void.
	 * http://developer.android.com/reference/android/os/AsyncTask.html
	 * 
	 * @author mohit
	 */
	public class GetQuotesInfo extends AsyncTask<Void, Void, Void> {
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
				JSONArray jsonarr = new JSONArray(Globals.jsonTextSheldon);
				int count = jsonarr.length();
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
			// publishProgress();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
