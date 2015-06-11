package com.quote.soundboard;

/**
 * 
 * @author manish
 * 
 */
public class SoundboardItem {

	public String heading;
	public String author[];
	public String name[];
	int mSoundResourceId;

	public SoundboardItem(String heading, String author[], String[] name, int id) {
		super();
		this.heading = heading;
		this.author = author;
		this.name = name;
		this.mSoundResourceId = id;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

}
