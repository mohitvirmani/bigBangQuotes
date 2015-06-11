package com.quote.soundboard;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.tbbtquotes.R;

@SuppressLint("NewApi")
public class SoundboardTabbedActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private ViewPager viewPager;
	private SoundboardTabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	ArrayList<String> quotesarrlist = new ArrayList<String>();

	private String[] tabs = new String[7];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soundboard_tabbed);
		// Bundle b = getIntent().getExtras();
		// int selected = b.getInt("selected");
		int selected = 0;
		quotesarrlist.add("Sheldon");
		quotesarrlist.add("Leonard");
		quotesarrlist.add("Penny");
		quotesarrlist.add("Howard");
		quotesarrlist.add("Raj");
		quotesarrlist.add("Bernadette");
		quotesarrlist.add("Miscellaneous");

		for (int i = 0; i < quotesarrlist.size(); i++) {
			tabs[i] = quotesarrlist.get(i);
		}
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.soundboardpager);
		actionBar = getActionBar();
		mAdapter = new SoundboardTabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		actionBar.setSelectedNavigationItem(selected);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	public Context getCurrentActivity() {
		return this;
	}
}
