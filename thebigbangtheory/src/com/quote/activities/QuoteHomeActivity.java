package com.quote.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.quote.about.AboutActivity;
import com.quote.globals.Globals;
import com.quote.help.HelpActivity;
import com.quote.soundboard.SoundboardTabbedActivity;
import com.quote.tabactivities.MainFragment;
import com.tbbtquotes.R;

//For left Nav Dashboard 

public class QuoteHomeActivity extends Activity {

	ArrayList<String> quotesarrlist = new ArrayList<String>();

	ListView listView;

	// Within which the entire activity is enclosed
	private DrawerLayout mDrawerLayout;

	// ListView represents Navigation Drawer
	private ListView mDrawerList;

	// ActionBarDrawerToggle indicates the presence of Navigation Drawer in the
	// action bar
	private ActionBarDrawerToggle mDrawerToggle;

	private String mTitle = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_activity_main_fragment);
		setContentView(R.layout.activity_quote_home);
		// Getting reference to the DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(Color.parseColor("#00FFFFFF"));
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}

		};

		mDrawerLayout.openDrawer(mDrawerList);
		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.drawer_list_item, getResources()
						.getStringArray(R.array.menus));

		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);

		// Enabling Home button
		getActionBar().setHomeButtonEnabled(true);

		// Enabling Up navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Getting an array of rivers
				String[] menuItems = getResources().getStringArray(
						R.array.menus);

				// Currently selected river
				mTitle = menuItems[position];
				Intent intent;

				switch (position) {

				case 0:
					mDrawerLayout.closeDrawer(mDrawerList);
					break;

//				case 1:
//					intent = new Intent(getApplicationContext(),
//							SoundboardTabbedActivity.class);
//					startActivity(intent);
//					break;

				case 1:
					intent = new Intent(getApplicationContext(),
							AboutActivity.class);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(getApplicationContext(),
							HelpActivity.class);
					startActivity(intent);
					break;

				default:
					mDrawerLayout.closeDrawer(mDrawerList);
					break;

				}

				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		// Parsing json and storing into file
		new GetJson().execute();

		quotesarrlist.add("Sheldon");
		quotesarrlist.add("Leonard");
		quotesarrlist.add("Penny");
		quotesarrlist.add("Howard");
		quotesarrlist.add("Raj");
		quotesarrlist.add("Amy");
		quotesarrlist.add("Bernadette");

		listView = (ListView) findViewById(R.id.list_quotesctype);

		listView.setAdapter(new ArrayAdapter(getApplicationContext(),
				R.layout.userlist_settingactivity, quotesarrlist));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				Intent intent = new Intent(getApplicationContext(),
						MainFragment.class);

				// Get the position of the item selected
				intent.putExtra("selected", pos);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_bottom,
						R.anim.abc_slide_out_bottom);
			}
		});

	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("The Big Bang Theory Quotes")
				.setMessage("Are you sure you want to Exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								System.exit(0);
							}

						}).setNegativeButton("No", null).show();
	}

	protected String getUrl(int position) {
		switch (position) {
		case 0:
			return "http://javatechig.com";
		case 1:
			return "http://javatechig.com/category/android/";
		case 2:
			return "http://javatechig.com/category/blackberry/";
		case 3:
			return "http://javatechig.com/category/j2me/";
		case 4:
			return "http://javatechig.com/category/sencha-touch/";
		case 5:
			return "http://javatechig.com/category/phonegap/";
		case 6:
			return "http://javatechig.com/category/java/";
		default:
			return "http://javatechig.com";
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		menu.findItem(R.id.action_settings).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class GetJson extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {

			Globals.jsonTextSheldon = getJSONDataFromRawFile("sheldon");
			Globals.jsonTextLeonard = getJSONDataFromRawFile("leonard");
			Globals.jsonTextPenny = getJSONDataFromRawFile("penny");
			Globals.jsonTextHoward = getJSONDataFromRawFile("howard");
			Globals.jsonTextRaj = getJSONDataFromRawFile("raj");
			Globals.jsonTextAmy = getJSONDataFromRawFile("amy");
			Globals.jsonTextBernadette = getJSONDataFromRawFile("bernadette");

			Globals.soundboardBernadette = getJSONDataFromRawFile("soundboardbernadette");
			Globals.soundboardmiscellaneous = getJSONDataFromRawFile("soundboardmiscellaneous");
			Globals.soundboardHoward = getJSONDataFromRawFile("soundboardhoward");
			Globals.soundboardLeonard = getJSONDataFromRawFile("soundboardleonard");
			Globals.soundboardSheldon = getJSONDataFromRawFile("soundboardsheldon");
			Globals.soundboardPenny = getJSONDataFromRawFile("soundboardpenny");
			Globals.soundboardRaj = getJSONDataFromRawFile("soundboardraj");

			return null;
		}
	}

	protected String getJSONDataFromRawFile(String character) {
		// GET data from file

		InputStream is;
		switch (character) {
		case "sheldon":
			is = getResources().openRawResource(R.raw.sheldon);
			break;

		case "leonard":
			is = getResources().openRawResource(R.raw.leonard);
			break;

		case "penny":
			is = getResources().openRawResource(R.raw.penny);
			break;

		case "howard":
			is = getResources().openRawResource(R.raw.howard);
			break;

		case "raj":
			is = getResources().openRawResource(R.raw.raj);
			break;

		case "amy":
			is = getResources().openRawResource(R.raw.amy);
			break;

		case "bernadette":
			is = getResources().openRawResource(R.raw.bernadette);
			break;

		case "soundboardsheldon":
			is = getResources().openRawResource(R.raw.soundboardsheldon);
			break;

		case "soundboardleonard":
			is = getResources().openRawResource(R.raw.soundboardleonard);
			break;

		case "soundboardpenny":
			is = getResources().openRawResource(R.raw.soundboardpenny);
			break;

		case "soundboardhoward":
			is = getResources().openRawResource(R.raw.soundboardhoward);
			break;

		case "soundboardraj":
			is = getResources().openRawResource(R.raw.soundboardraj);
			break;

		case "soundboardmiscellaneous":
			is = getResources().openRawResource(R.raw.soundboardmiscellaneous);
			break;

		case "soundboardbernadette":
			is = getResources().openRawResource(R.raw.soundboardbernadette);
			break;

		default:
			is = getResources().openRawResource(R.raw.sheldon);
			break;
		}

		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException io) {

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String jsonString = writer.toString();
		return jsonString;
	}

}
