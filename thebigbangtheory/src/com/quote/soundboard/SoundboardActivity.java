package com.quote.soundboard;

import java.util.ArrayList;

import com.tbbtquotes.R;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class SoundboardActivity extends ListActivity {

	private ArrayList<Sound> mSounds = null;
	private SoundAdapter mAdapter = null;
	static MediaPlayer mMediaPlayer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soundboard);
		// create a simple list
		mSounds = new ArrayList<Sound>();
		Sound s = new Sound();
		s.setDescription("Elephant");
		//s.setSoundResourceId(R.raw.elephant);
		mSounds.add(s);
		s = new Sound();
		s.setDescription("Rooster");
		//s.setSoundResourceId(R.raw.rooster);
		mSounds.add(s);
		s = new Sound();
		s.setDescription("Kitten");
		//s.setSoundResourceId(R.raw.kitten);
		mSounds.add(s);
		mAdapter = new SoundAdapter(this, R.layout.list_row, mSounds);
		setListAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.soundboard, menu);
		return true;
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Sound s = (Sound) mSounds.get(position);
		MediaPlayer mp = MediaPlayer.create(this, s.getSoundResourceId());
		mp.stop();
		mp.start();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
