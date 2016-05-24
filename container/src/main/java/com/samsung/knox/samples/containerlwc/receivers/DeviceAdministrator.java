package com.samsung.knox.samples.containerlwc.receivers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.ui.AdminLicenseActivationActivity;
import com.samsung.knox.samples.containerlwc.utils.SAUIHelper;

//This BroadcastReceiver handles device admin activation and deactivation

public class DeviceAdministrator extends DeviceAdminReceiver {

	SharedPreferences.Editor adminLicensePrefsEditor;
	SharedPreferences adminLicensePrefs;
	static AdminLicenseActivationActivity activityObj;

	public DeviceAdministrator() {
	}

	// Get the current activity instance
	public DeviceAdministrator(AdminLicenseActivationActivity activityObj) {
		DeviceAdministrator.activityObj = activityObj;
	}

	// if device admin is enabled

	@Override
	public void onEnabled(Context context, Intent intent) {
		adminLicensePrefsEditor = context.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

		adminLicensePrefs = context.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);

		SAUIHelper.showToast(context, SAConstants.DEVICE_ADMIN_ENABLED);
		if (adminLicensePrefs.getString(SAConstants.DO_SELF_UNINSTALL, null) == null) {
			// if activity is running
			if (DeviceAdministrator.activityObj != null) {
				// set the UI states in Activity to enable KLM,ELM activation
				// buttons
				DeviceAdministrator.activityObj
						.setUIStates(SAConstants.RESULT_ENABLE_ADMIN);
			}
			// if activity is not running
			else {
				saveState(SAConstants.RESULT_ENABLE_ADMIN);
			}
		}
	}

	// when request to disable device admin is made
	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		return SAConstants.DISABLE_ADMIN_WARNING;
	}

	// when device admin is disabled
	@Override
	public void onDisabled(Context context, Intent intent) {
		adminLicensePrefsEditor = context.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

		adminLicensePrefs = context.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		SAUIHelper.showToast(context, SAConstants.DEVICE_ADMIN_DISABLED);
		// if activity is running
		if (DeviceAdministrator.activityObj != null) {
			// set the UI states in Activity to disable KLM,ELM activation
			// buttons
			DeviceAdministrator.activityObj
					.setUIStates(SAConstants.RESULT_DISABLE_ADMIN);
		}
		// if activity is not running
		else {
			saveState(SAConstants.RESULT_DISABLE_ADMIN);
		}
	}

	// This method is used to save the state in case activity is currently not
	// running
	public void saveState(int condition) {

		switch (condition) {

		case SAConstants.RESULT_ENABLE_ADMIN:

			adminLicensePrefsEditor.putBoolean(SAConstants.ADMIN, true);
			adminLicensePrefsEditor.commit();

			break;

		case SAConstants.RESULT_DISABLE_ADMIN:

			adminLicensePrefsEditor.putBoolean(SAConstants.ADMIN, false);
			adminLicensePrefsEditor.putBoolean(SAConstants.KLM, false);
			adminLicensePrefsEditor.putBoolean(SAConstants.ELM, false);
			adminLicensePrefsEditor.commit();

			break;

		default:
			break;

		}

	}

}
