package com.samsung.knox.samples.containerlwc.beans;

import android.content.Context;

import com.samsung.knox.samples.containerlwc.enums.Enums;


/* Every item of the listview is represented by an instance of this class 
 * 
 * Instance variables description:
 * 
 * itemId : This is an unique integer assigned to each item. This is taken as the case numbers in switch statement
 * all Model classes
 * 
 * name : Name of the API/functionality
 * 
 * apiType : This is an enum to indicate whether this item is a check box setter, getter,
 * requires a user input, performs an operation (calls KNOX API) and so on.
 * 
 * knoxSdkVersionReqd : This is an enum to indicate the KNOX version required for this API
 * 
 * safeSdkVersionReqd : This is an enum to indicate the SAFE/KNOX Standard SDK version required for this API
 * 
 * context : This captures the current Activity context from which this item is clicked
 * 
 * classObj : This represents the class object of the Activity, which needs to be launched on clicking this item.  
 * 
 * headerMsg : This is used for the items, which take a user input or inform something to user. This represents 
 * the heading message in the alert dialog, which is displayed to the user.
 * 
 * bodyMsg: This represents the body message in the alert dialog shown to the user
 * 
 * editTextDefValue : This is used for items, which display an EditText to user to take his/her input. The value
 * in this variable is written in that EditText by default.
 * 
 * key : This is used for the items, which need to persist data in shared preference. This is used as key of that
 * shared preference item
 * 
 */

public class ListItem {
	public int itemId;
	public String name;
	public Enums.APIType apiType;
	public Enums.KnoxSDKVersion knoxSdkVersionReqd;
	public Enums.SAFESDKVersion safeSdkVersionReqd;
	public Context context;
	public Class classObj;
	public String headerMsg;
	public String bodyMsg;
	public String editTextDefValue;
	public String[] keys;

	public ListItem(int itemId, String name, Enums.APIType apiType) {
		this.itemId = itemId;
		this.name = name;
		this.apiType = apiType;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Enums.KnoxSDKVersion knoxSdkVersionReqd, Context context,
			Class classObj) {
		this(itemId, name, apiType);
		this.knoxSdkVersionReqd = knoxSdkVersionReqd;
		this.context = context;
		this.classObj = classObj;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Context context, Class classObj) {
		this(itemId, name, apiType);
		this.context = context;
		this.classObj = classObj;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Enums.KnoxSDKVersion knoxSDKVersionReqd) {
		this(itemId, name, apiType);
		this.knoxSdkVersionReqd = knoxSDKVersionReqd;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Enums.KnoxSDKVersion knoxSDKVersionReqd, String headerMsg,
			String bodyMsg, String editTextDefValue,
			String[] keys, Context context, Class classObj) {
		this(itemId, name, apiType, knoxSDKVersionReqd);	
		this.headerMsg = headerMsg;
		this.bodyMsg = bodyMsg;
		this.editTextDefValue = editTextDefValue;
		this.context = context;
		this.classObj = classObj;
		this.keys = keys;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Enums.SAFESDKVersion safeSdkVersionReqd) {
		this(itemId, name, apiType);
		this.safeSdkVersionReqd = safeSdkVersionReqd;
	}

	public ListItem(int itemId, String name, Enums.APIType apiType,
			Enums.SAFESDKVersion safeSDKVersionReqd, String headerMsg,
			String bodyMsg, String editTextDefValue,
			String[] keys, Context context, Class classObj) {
		this(itemId, name, apiType);
		this.safeSdkVersionReqd = safeSDKVersionReqd;
		this.headerMsg = headerMsg;
		this.bodyMsg = bodyMsg;
		this.editTextDefValue = editTextDefValue;
		this.context = context;
		this.classObj = classObj;
		this.keys = keys;
	}

}