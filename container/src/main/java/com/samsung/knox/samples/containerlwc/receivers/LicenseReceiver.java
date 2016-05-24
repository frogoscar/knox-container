package com.samsung.knox.samples.containerlwc.receivers;

import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.samsung.knox.samples.containerlwc.R;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.ui.AdminLicenseActivationActivity;
import com.samsung.knox.samples.containerlwc.utils.SACodeUtils;
import com.samsung.knox.samples.containerlwc.utils.SAUIHelper;
import com.sec.enterprise.knox.license.KnoxEnterpriseLicenseManager;

//This BroadcastReceiver handles KLM and ELM activation

public class LicenseReceiver extends BroadcastReceiver {

	private Context mContext;
	SharedPreferences.Editor adminLicensePrefsEditor;
	SharedPreferences adminLicensePrefs;
	static AdminLicenseActivationActivity activityObj;

	public LicenseReceiver() {
		
	}

	// Get the current activity instance
	public LicenseReceiver(AdminLicenseActivationActivity activityObj) {
		LicenseReceiver.activityObj = activityObj;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		// saLoggerObj.i("Receiver onreceive ", "activityObj "+activityObj);
		mContext = context;
		adminLicensePrefsEditor = mContext.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

		adminLicensePrefs = mContext.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);

		if (intent != null) {
			String action = intent.getAction();
			if (action == null) {
				return;
			}
			// If ELM activation result intent is obtained
			else if (action
					.equals(EnterpriseLicenseManager.ACTION_LICENSE_STATUS)) {
				int errorCode = intent.getIntExtra(
						EnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE,
						SAConstants.DEFAULT_ERROR);

				// if License is successfully activated
				if (errorCode == EnterpriseLicenseManager.ERROR_NONE) {
					SAUIHelper.showToast(mContext,
							SAConstants.ELM_ACTIVATION_SUCCESS);
					// if activity is running
					if (LicenseReceiver.activityObj != null) {
						// set the UI states in Activity
						LicenseReceiver.activityObj
								.setUIStates(SAConstants.RESULT_ELM_ACTIVATED);
					}
					// if activity is not running
					else {
						saveState(SAConstants.RESULT_ELM_ACTIVATED);
					}
				}
				// if license activation failed
				else {
					SAUIHelper.showToast(mContext,
							SAConstants.ELM_ACTIVATION_FAILURE + errorCode);
				}
			}
			// If KLM activation result intent is obtained
			else if (action
					.equals(KnoxEnterpriseLicenseManager.ACTION_LICENSE_STATUS)) {
				int errorCode = intent.getIntExtra(
						KnoxEnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE,
						SAConstants.DEFAULT_ERROR);

				// if License is successfully activated
				if (errorCode == KnoxEnterpriseLicenseManager.ERROR_NONE) {
					SAUIHelper.showToast(mContext,
							SAConstants.KLM_ACTIVATION_SUCCESS);
					// if activity is running
					if (LicenseReceiver.activityObj != null) {
						// set the UI states in Activity
						LicenseReceiver.activityObj
								.setUIStates(SAConstants.RESULT_KLM_ACTIVATED);
					}
					// if activity is not running
					else {
						saveState(SAConstants.RESULT_KLM_ACTIVATED);
					}
				}
				// if license activation failed
				else {
					
					String msg = SACodeUtils.getMessage(errorCode,context);
					
					if(msg != null && !msg.equalsIgnoreCase(""))
						SAUIHelper.showToast(context,msg);
					else{
						SAUIHelper.showToast(context,context.getString(R.string.err_unknown));
					}
				}
			}
		}
	}

	// This method is used to save the state in case activity is currently not
	// running
	public void saveState(int condition) {

		switch (condition) {

		case SAConstants.RESULT_KLM_ACTIVATED:

			adminLicensePrefsEditor.putBoolean(SAConstants.KLM, true);
			adminLicensePrefsEditor.commit();

			break;

		case SAConstants.RESULT_ELM_ACTIVATED:

			adminLicensePrefsEditor.putBoolean(SAConstants.ELM, true);
			adminLicensePrefsEditor.commit();

			break;

		default:
			break;

		}

	}

}
