package com.quote.soundboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.quote.globals.Globals;
import com.tbbtquotes.R;

public class SoundboardFragmentActivity1 extends Fragment {

	public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";
	ArrayList<SoundboardItem> imageArry = new ArrayList<SoundboardItem>();
	SoundboardCustomQuoteAdapter adapter;
	static MediaPlayer mMediaPlayer = null;
	String soundboardTrack = null;
	Context context = getActivity();

	/**
	 * Taking documentation examples from
	 * http://docstore.mik.ua/orelly/java-ent/jnut/ch07_03.htm
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d("view created", "view created");
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
		View rootView = inflater.inflate(
				R.layout.soundboard_fragment_activity1, container, false);
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

		adapter = new SoundboardCustomQuoteAdapter(getActivity(),
				R.layout.list, imageArry);

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
				Log.d("on click listener started", "onclick listener started");
				Log.d("position", position + "");
				if (mMediaPlayer != null) {
					if (mMediaPlayer.isPlaying() == true) {
						mMediaPlayer.pause();
						playSelectedMedia(position);
					} else {
						playSelectedMedia(position);
					}
				} else {
					playSelectedMedia(position);
				}
			}

			public void playSelectedMedia(int position) {
				SoundboardItem selectedItem = (SoundboardItem) (dataList
						.getItemAtPosition(position));
				Log.d("selectedItem", selectedItem + "");
				Log.d("selectedItem.mSoundResourceId " , selectedItem.mSoundResourceId + "");
				mMediaPlayer = MediaPlayer.create(getActivity(),
						selectedItem.mSoundResourceId);
				Log.d("mMediaPlayer", mMediaPlayer + "");
				mMediaPlayer.start();
			};
		});

		dataList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@SuppressLint("ShowToast")
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				// TODO :show a view, and in that view, provide option to share
				// text, share audio
				// or set the audio as ringtone.
				setMyRingtone(pos);
				return true;
			}

			public void setMyRingtone(int pos) {

				Log.d("pos", pos + "");
				SoundboardItem selectedItem = (SoundboardItem) (dataList
						.getItemAtPosition(pos));
				Log.d("selectedItem" , selectedItem + "");
				String name = getResources().getResourceEntryName(
						selectedItem.mSoundResourceId);
				Log.d("name", name);
				Toast.makeText(getActivity(), "Ringtone " + name + " set.",
						Toast.LENGTH_LONG).show();
				File file = new File(Environment.getExternalStorageDirectory(),
						"/myRingtoneFolder/Audio/");
				if (!file.exists()) {
					file.mkdirs();
				}
				String path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/myRingtoneFolder/Audio/";

				File f = new File(path + "/", name + ".mp3");
				
				Uri mUri = Uri.parse("android.resource://"
						+ getActivity().getPackageName() + "/raw/" + name);
				Log.d("mUri", mUri + "");
				ContentResolver mCr = getActivity().getContentResolver();
				AssetFileDescriptor soundFile;
				try {
					soundFile = mCr.openAssetFileDescriptor(mUri, "r");
				} catch (FileNotFoundException e) {
					soundFile = null;
				}

				try {
					byte[] readData = new byte[1024];
					FileInputStream fis = soundFile.createInputStream();
					FileOutputStream fos = new FileOutputStream(f);
					int i = fis.read(readData);

					while (i != -1) {
						fos.write(readData, 0, i);
						i = fis.read(readData);
					}

					fos.close();
				} catch (IOException io) {
				}

				ContentValues values = new ContentValues();
				values.put(MediaStore.MediaColumns.DATA, f.getAbsolutePath());
				values.put(MediaStore.MediaColumns.TITLE, name);
				values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
				values.put(MediaStore.MediaColumns.SIZE, f.length());
				values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
				values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
				values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
				values.put(MediaStore.Audio.Media.IS_ALARM, true);
				values.put(MediaStore.Audio.Media.IS_MUSIC, true);

				Uri uri = MediaStore.Audio.Media.getContentUriForPath(f
						.getAbsolutePath());
				mCr.delete(
						uri,
						MediaStore.MediaColumns.DATA + "=\""
								+ f.getAbsolutePath() + "\"", null);
				Uri newUri = mCr.insert(uri, values);

				try {
					RingtoneManager.setActualDefaultRingtoneUri(getActivity(),
							RingtoneManager.TYPE_RINGTONE, newUri);
					Settings.System.putString(mCr, Settings.System.RINGTONE,
							newUri.toString());

				} catch (Throwable t) {
					Toast.makeText(
							getActivity(),
							"Error setting ringtone " + name
									+ " . Please try later.", Toast.LENGTH_LONG)
							.show();
				}
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
	class GetQuotesInfo extends AsyncTask<Void, Void, Void> {
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
				JSONArray jsonarr = new JSONArray(Globals.soundboardSheldon);
				int count = jsonarr.length();
				for (int i = 0; i < count; i++) {
					JSONObject jsonobj = jsonarr.getJSONObject(i);
					final String name = jsonobj.getString("name");
					soundboardTrack = jsonobj.getString("soundboard");
					final int id = getResources().getIdentifier(
							soundboardTrack, "raw", "com.quotes.bigbangtheory");
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
							SoundboardItem item = new SoundboardItem(name,
									myarrAuthor, myarrText, id);
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
