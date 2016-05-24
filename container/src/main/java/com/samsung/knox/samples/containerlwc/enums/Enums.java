package com.samsung.knox.samples.containerlwc.enums;

//This class declares all enums used in the project

public class Enums {

	// This enum represents the type of the item in the listview

	public enum APIType {
		SETTER, // represents a check box in a listview item
		GETTER, // represents a query API
		OPERATION, // represents an operation like createContainer() API
		UI_HEADER, // represents the header of a listview
		UI_BUTTON, // represents a button in a listview item
		DISPLAY_ACTIVITY, // represents that the item needs to launch an
							// activity
		DISPLAY_EDITTEXT_POPUP, // represents that the item needs to display an
								// EditText alert dialog
		DISPLAY_NUMERIC_EDITTEXT_POPUP, // represents that the item needs to
										// display a numeric keyboard EditText
										// alert dialog
		DISPLAY_LIST_POPUP, // represents that the item needs to display a list
							// alert dialog
		DISPLAY_QUERY_POP_UP, // represents that the item needs to display an
								// alert dialog asking a query to user
		DISPLAY_MULTI_CHOOSER_LIST_POPUP // represents that the item needs to display a list
										 //alert dialog, where a user can select multiple items	
		
	}

	// This enum represents the KNOX versions

	public enum KnoxSDKVersion {
		NONE, VER_1_0, VER_1_0_1, VER_1_0_2, VER_1_1_0, VER_1_2_0, VER_2_0, VER_2_1, VER_2_2, VER_2_3, VER_2_4
	}

	// This enum represents the KNOX Standard SDK/SAFE SDK versions

	public enum SAFESDKVersion {
		NONE, VER_1_0, VER_2_0, VER_2_1, VER_2_2, VER_3_0, VER_4_0, VER_4_0_1, VER_4_1, VER_5_0, VER_5_1, VER_5_2, VER_5_3, VER_5_4
	}

}
