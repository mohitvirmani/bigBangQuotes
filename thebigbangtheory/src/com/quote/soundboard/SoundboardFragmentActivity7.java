package com.quote.soundboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tbbtquotes.R;
import com.quote.globals.Globals;

import android.support.v4.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class SoundboardFragmentActivity7 extends Fragment {

	// Hashmap for ListView
	ArrayList<SoundboardItem> imageArry = new ArrayList<SoundboardItem>();
	SoundboardCustomQuoteAdapter adapter;
	String soundboardTrack = null;
	Context context = getActivity();
	static MediaPlayer mMediaPlayer = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(
				R.layout.soundboard_fragment_activity1, container, false);
		new GetQuotesInfo().execute();
		adapter = new SoundboardCustomQuoteAdapter(getActivity(),
				R.layout.list, imageArry);
		final ListView dataList = (ListView) rootView.findViewById(R.id.list);
		dataList.setAdapter(adapter);
		adapter.clear();

		dataList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
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
				mMediaPlayer = MediaPlayer.create(getActivity(),
						selectedItem.mSoundResourceId);
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

				SoundboardItem selectedItem = (SoundboardItem) (dataList
						.getItemAtPosition(pos));
				String name = getResources().getResourceEntryName(
						selectedItem.mSoundResourceId);
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
				JSONArray jsonarr = new JSONArray(
						Globals.soundboardmiscellaneous);
				count = jsonarr.length();
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
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
