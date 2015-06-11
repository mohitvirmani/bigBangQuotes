package com.quote.tabactivities;

/**
 * 
 * @author manish
 * 
 */
public class Item {

	public String heading;
	public String author[];
	public String name[];

	public Item(String heading, String author[], String[] name) {
		super();
		this.heading = heading;
		this.author = author;
		this.name = name;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

}
