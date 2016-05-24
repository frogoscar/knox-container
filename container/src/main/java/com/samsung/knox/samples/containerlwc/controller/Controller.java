package com.samsung.knox.samples.containerlwc.controller;

import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.samsung.knox.samples.containerlwc.app.SAApplication;
import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.samsung.knox.samples.containerlwc.model.BaseModel;
import com.samsung.knox.samples.containerlwc.utils.SAUIHelper;


//This class acts as a link between UI (view) and KNOX APIs implementation classes (all model classes)

public class Controller {

	private ListView mListViewObj;
	private static Context mContext;
	private BaseModel mModelObj;
	private ActivityLauncher activityLauncherRef;
	private ListItem listItemObj;
	static SharedPreferences sharedpreferences;
	static Editor editor;
	String TAG = "Controller";
	int mType;

	public Controller(Context context) {
		// Get the current context
		mContext = context;
		// create an instance of Model class
		mModelObj = new BaseModel(context);
	}

	public Controller(Context context, int type) {
		this(context);
		mType = type;
	}

	// This method handles onItemClick event of any item of the listview
	public void handleItemClick(ListView listViewObj) {

		mListViewObj = listViewObj;

		mListViewObj.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				listItemObj = (ListItem) mListViewObj.getItemAtPosition(position);
				final View mView = view;
				Log.i(TAG, "in on item click listener mView.isEnabled() " + mView.isEnabled());

				// if the listview item is enabled
				if (mView.isEnabled()) {

					switch (listItemObj.apiType) {

					// if an EditText alert dialog needs to be displayed
					case DISPLAY_EDITTEXT_POPUP:
						// if the item needs to launch an activity after
						// invoking a KNOX API
						if (listItemObj.classObj != null) {

							activityLauncherRef = new ActivityLauncher() {

								// Launch the Activity
								@Override
								public void launchActivity() {

									Intent intent = new Intent(listItemObj.context, listItemObj.classObj);
									listItemObj.context.startActivity(intent);

								}

								// Finish the current activity
								@Override
								public void finishCurrentActivity() {
									// TODO Auto-generated method stub

								}
							};

						}

						// Show the EditText alert dialog
						SAUIHelper.showAlertWithEditText(mContext, listItemObj.headerMsg, listItemObj.bodyMsg,
								listItemObj.editTextDefValue, listItemObj, mModelObj, activityLauncherRef, mType);
						break;

					// if a numeric keyboard EditText alert dialog needs to be
					// displayed
					case DISPLAY_NUMERIC_EDITTEXT_POPUP:

						if (listItemObj.classObj != null) {
							// if the item needs to launch an activity after
							// invoking a KNOX API
							activityLauncherRef = new ActivityLauncher() {

								// Launch the Activity
								@Override
								public void launchActivity() {

									Intent intent = new Intent(listItemObj.context, listItemObj.classObj);
									listItemObj.context.startActivity(intent);

								}

								// Finish the current activity
								@Override
								public void finishCurrentActivity() {
									// TODO Auto-generated method stub

								}
							};

						}

						// Show the numeric keyboard EditText alert dialog
						SAUIHelper.showAlertWithNumericEditText(mContext, listItemObj.headerMsg, listItemObj.bodyMsg,
								listItemObj.editTextDefValue, listItemObj, mModelObj, activityLauncherRef, mType);
						break;

					// if the item needs to launch an Activity
					case DISPLAY_ACTIVITY:

						Intent intent = new Intent(listItemObj.context, listItemObj.classObj);
						intent.putExtra(SAConstants.API_NAME, listItemObj.name);
						listItemObj.context.startActivity(intent);

						break;

					// if the item needs to display an alert dialog to ask a
					// query to user
					case DISPLAY_QUERY_POP_UP:
						SAUIHelper.showAlertToAskQuery(mContext, listItemObj.headerMsg, listItemObj.bodyMsg,
								listItemObj, mModelObj, activityLauncherRef, mType);
						break;

					// for all other cases, invoke model class routePolicy()
					// method, which routes the call
					// to specific Model class in which the KNOX API would be
					// called
					default:
						mModelObj.routePolicy(listItemObj, false, mType);
						break;

					}
				}
			}

		});

	}

	// This method routes the call to Model class to activate admin
	public boolean activateAdmin() {

		return mModelObj.activateAdmin();

	}

	// This method routes the call to Model class to deactivate admin
	public boolean deactivateAdmin() {

		return mModelObj.deactivateAdmin();

	}

	// This method routes the call to Model class to activate KLM with admin
	// enabled
	public void activateKLMWithAdminEnabled(String klmsKey) {

		mModelObj.activateKLMWithAdminEnabled(klmsKey);
	}

	// This method routes the call to Model class to activate ELM with admin
	// enabled
	public void activateELMWithAdminEnabled(String elmKey) {

		mModelObj.activateELMWithAdminEnabled(elmKey);
	}

	// This method routes the call to Model class to activate KLM
	public void activateKLM(String klmsKey) {

		mModelObj.activateKLM(klmsKey);
	}

	// This method routes the call to Model class to activate ELM
	public void activateELM(String elmKey) {

		mModelObj.activateELM(elmKey);
	}

	// This method routes the call to Model class to delete the instance of a
	// Model class. The class
	// is identified by the type passed in as an argument.
	public static void freeInstance(int type) {

		BaseModel.freeInstance(type);

	}

	// This method calls SAUIHelper's method to display a Toast by passing in
	// the current Activity context
	public static void showToast(String message) {
		SAUIHelper.showToast(mContext, message);
	}

	// This method calls SAUIHelper's method to display an alert dialog with
	// list
	// by passing in the current Activity context
	public static void showAlertWithList(String[] arr, String heading, String key) {
		SAUIHelper.showAlertWithList(mContext, heading, arr, key);
	}

	public static void showAlertWithMultiChoiceList(String heading, String[] list, String key) {
		SAUIHelper.showAlertWithMultiChoiceList(mContext, heading, list, key);
	}

	// This method calls SAUIHelper's method to display an alert dialog
	// by passing in the current Activity context
	public static void showAlert(String heading, String msg, String okMsg) {
		SAUIHelper.showAlert(mContext, heading, msg, okMsg);
	}

	// This method is used to obtain a value from Shared Preference by passing
	// in the key.
	// The context used is current Activity context
	public static Object getSharedPrefData(String key, int dataType) {
		if (mContext == null) {
			mContext = SAApplication.getAppContext();
		}
		sharedpreferences = mContext.getSharedPreferences(SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		Object value = null;
		
		switch(dataType){
		
			case SAConstants.STRING_DATATYPE:
				value = sharedpreferences.getString(key, null);
				break;
				
			case SAConstants.STRINGSET_DATATYPE:
				value = sharedpreferences.getStringSet(key, null);
				break;
				
			case SAConstants.BOOLEAN_DATATYPE:
				value = sharedpreferences.getBoolean(key, false);
				break;
	
		}
		
		return value;
	}

	// This method is used to set a value to Shared Preference by passing in the
	// key and the value.
	// The context used is current Activity context
	public static void setSharedPrefData(String key, Object val, int dataType) {
		if (mContext == null) {
			mContext = SAApplication.getAppContext();
		}
		sharedpreferences = mContext.getSharedPreferences(SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		
		switch(dataType){
			
			case SAConstants.STRING_DATATYPE:
				editor.putString(key, (String)val);
				break;
				
			case SAConstants.STRINGSET_DATATYPE:
				editor.putStringSet(key, (Set<String>)val);
				break;
				
			case SAConstants.BOOLEAN_DATATYPE:
				editor.putBoolean(key, (Boolean)val);
				break;
		
		}
		
		editor.commit();
	}

	/*public static void setSharedPrefSetData(String key, Set<String> val) {
		if (mContext == null) {
			mContext = SAApplication.getAppContext();
		}
		sharedpreferences = mContext.getSharedPreferences(SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		editor.putStringSet(key, val);
		editor.commit();
	}

	public static Set<String> getSharedPrefSetData(String key) {
		if (mContext == null) {
			mContext = SAApplication.getAppContext();
		}
		sharedpreferences = mContext.getSharedPreferences(SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		return sharedpreferences.getStringSet(key, null);
	}
*/
	// This method returns the current activity context
	public static Context getCurrentContext() {
		return mContext;
	}
}
