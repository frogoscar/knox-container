package com.samsung.knox.samples.containerlwc.interfaces;

import java.util.ArrayList;

import com.samsung.knox.samples.containerlwc.beans.ListItem;


//This interface is used by implementing classes to create all listview item objects and 
//display them in the listview

public interface APIListPopulator {

	public ArrayList<ListItem> populateAPIList();

}
