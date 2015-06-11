package com.quote.soundboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SoundboardTabsPagerAdapter extends FragmentPagerAdapter {

	public SoundboardTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new SoundboardFragmentActivity1();
		case 1:

			return new SoundboardFragmentActivity2();
		case 2:

			return new SoundboardFragmentActivity3();
		case 3:

			return new SoundboardFragmentActivity4();
		case 4:

			return new SoundboardFragmentActivity5();
		case 5:

			return new SoundboardFragmentActivity6();
		case 6:

			return new SoundboardFragmentActivity7();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 7;
	}

}
