package com.quote.tabactivities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:

			return new FragmentActivity1();
		case 1:

			return new FragmentActivity2();
		case 2:

			return new FragmentActivity3();
		case 3:

			return new FragmentActivity4();
		case 4:

			return new FragmentActivity5();
		case 5:

			return new FragmentActivity6();
		case 6:

			return new FragmentActivity7();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 7;
	}

}
