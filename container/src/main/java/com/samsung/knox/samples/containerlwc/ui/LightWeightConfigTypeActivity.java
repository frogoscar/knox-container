package com.samsung.knox.samples.containerlwc.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.samsung.knox.samples.containerlwc.R;
import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.controller.Controller;
import com.samsung.knox.samples.containerlwc.enums.Enums.APIType;
import com.samsung.knox.samples.containerlwc.enums.Enums.KnoxSDKVersion;
import com.samsung.knox.samples.containerlwc.interfaces.APIListPopulator;
import com.samsung.knox.samples.containerlwc.ui.listadapter.PolicyListAdapter;

//This Activity displays all APIs of LightweightConfigurationType

public class LightWeightConfigTypeActivity extends Activity implements
		APIListPopulator {

	ListView mApiListView;
	ArrayList<ListItem> mApiList = null;
	PolicyListAdapter adapter;
	Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.api_list);
		// create items of listview
		mApiList = populateAPIList();
		adapter = new PolicyListAdapter(LightWeightConfigTypeActivity.this,
				mApiList, 4, SAConstants.LWC_MODE_CONFIG_TYPE);
		mApiListView = (ListView) findViewById(R.id.apiList);
		mApiListView.setTextFilterEnabled(true);
		mApiListView.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		super.onStart();
		// pass current activity context to Controller class
		controller = new Controller(LightWeightConfigTypeActivity.this,
				SAConstants.LWC_MODE_CONFIG_TYPE);
		// this method is called to transfer handling of all onItemClick events
		// in listview, to Controller class
		controller.handleItemClick(mApiListView);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// deletes the instance of the Model class which calls KNOX APIs
		// relevant to items in this listview
		Controller.freeInstance(SAConstants.LWC_MODE_CONFIG_TYPE);
		controller = null;
	}

	// This method creates items of the listview

	@Override
	public ArrayList<ListItem> populateAPIList() {

		ArrayList<ListItem> arrListItems = new ArrayList<ListItem>();
		int count = 0;

		arrListItems.add(new ListItem(count++,
				getString(R.string.api_set_config_type_name),
				APIType.DISPLAY_EDITTEXT_POPUP, KnoxSDKVersion.VER_2_0,
				getString(R.string.api_set_config_type_name),
				getString(R.string.enter_config_type_name), SAConstants.TEMP,
				null, null, null));

		arrListItems.add(new ListItem(count++,
				getString(R.string.api_set_folder_header_title),
				APIType.DISPLAY_EDITTEXT_POPUP, KnoxSDKVersion.VER_2_1,
				getString(R.string.api_set_folder_header_title),
				getString(R.string.enter_folder_header_title),
				SAConstants.TEMP, null, null, null));

		arrListItems.add(new ListItem(count++,
				getString(R.string.api_get_folder_header_title),
				APIType.GETTER, KnoxSDKVersion.VER_2_1));

		arrListItems.add(new ListItem(count++,
				getString(R.string.api_save_config_type_object),
				APIType.UI_BUTTON, KnoxSDKVersion.VER_2_0));

		return arrListItems;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_disable_admin_byod:

			SharedPreferences sharedpreferences = getSharedPreferences(
					SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
			Editor editor = sharedpreferences.edit();

			if (sharedpreferences.getBoolean(SAConstants.ADMIN, false)
					&& controller.deactivateAdmin()) {
				editor.putBoolean(SAConstants.ADMIN, false);
			} else if (sharedpreferences.getBoolean(SAConstants.BYOD, false)) {
				editor.putBoolean(SAConstants.BYOD, false);
			}
			editor.putBoolean(SAConstants.KLM, false);
			editor.putBoolean(SAConstants.ELM, false);
			editor.commit();
			Intent intent = new Intent(this,
					AdminLicenseActivationActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.action_about_app:
			Intent aboutAppIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutAppIntent);

			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

}
