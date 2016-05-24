package com.samsung.knox.samples.containerlwc.model;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.enterprise.EnterpriseDeviceManager;
import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.samsung.knox.samples.containerlwc.interfaces.KNOXApiInvoker;
import com.samsung.knox.samples.containerlwc.receivers.DeviceAdministrator;
import com.samsung.knox.samples.containerlwc.utils.SAUIHelper;
import com.sec.enterprise.knox.EnterpriseKnoxManager;
import com.sec.enterprise.knox.license.KnoxEnterpriseLicenseManager;

/* 
 * This class is called by Controller class and from here, the call is routed to respective model class
 * to call KNOX APIs. This class is the point of contact to the outside world (Controller class). It
 * itself is not aware, to which Model class, the call is routed. It is aware of the interface and not
 * the class.This is an example of "programming to an interface and not an implementation". Apart from
 * this, it contains the logic to activate/deactivate admin,KLM,ELM,create/delete other Model class instances
 * using Factory Design Pattern,check if an API is supported in a device and so on.
 * 
 */

public class BaseModel implements KNOXApiInvoker {

	SAUIHelper saUiHelperObj;
	static BaseModel modelObj;
	SharedPreferences.Editor adminLicensePrefsEditor;
	SharedPreferences adminLicensePrefs;
	Context mContext;
	DevicePolicyManager mDPM;
	ComponentName mCN;
	public String TAG = "Model";
	static EnterpriseKnoxManager mKNOXMgr;
	static EnterpriseDeviceManager mEDM;

	public BaseModel() {

	}

	public BaseModel(Context context) {
		// current context
		mContext = context;
		if (BaseModel.mKNOXMgr == null && BaseModel.mEDM == null) {
			BaseModel.mKNOXMgr = EnterpriseKnoxManager.getInstance();
			BaseModel.mEDM = (EnterpriseDeviceManager) context
					.getSystemService(EnterpriseDeviceManager.ENTERPRISE_POLICY_SERVICE);
		}
	}

	public void routePolicy(ListItem listItemObj, boolean enable, int type) {

		BaseModel baseModelObj = getObj(type);
		Log.i(TAG, "in Model setKNOXAPI call");
		baseModelObj.invokeKNOXAPI(listItemObj, enable);
	}

	public void routePolicy(ListItem listItemObj, String userEnteredData, ActivityLauncher activityLauncher, int type) {

		BaseModel baseModelObj = getObj(type);
		baseModelObj.invokeKNOXAPI(listItemObj, userEnteredData, activityLauncher);
	}

	public boolean fetchState(ListItem listItemObj, int type) {

		BaseModel baseModelObj = getObj(type);
		return baseModelObj.fetchState(listItemObj);
	}

	// This method is used to check if a particular API is supported by the
	// device
	public static boolean isAPISupported(ListItem listItemObj) {
		if (listItemObj.knoxSdkVersionReqd != null
				&& mKNOXMgr.getVersion().ordinal() < listItemObj.knoxSdkVersionReqd.ordinal()) {
			return false;
		} else if (listItemObj.safeSdkVersionReqd != null
				&& mEDM.getEnterpriseSdkVer().ordinal() < listItemObj.safeSdkVersionReqd.ordinal()) {
			return false;
		}
		return true;
	}

	/*
	 * This method creates an instance of a model class extending this class
	 * (based on the type passed in as an argument).The type is passed in by the
	 * activity, because it is the only entity which knows, what type of policy
	 * it is. This represents "Factory Design Pattern"
	 */

	public static BaseModel getObj(int type) {

		BaseModel baseModelObj = null;

		switch (type) {

		case SAConstants.CONTAINER_CREATION_REMOVAL:
			baseModelObj = ContainerCreationRemovalModel.getInstance();
			break;

		case SAConstants.LWC_MODE_CONFIG_TYPE:
			baseModelObj = LightWeightConfigTypeModel.getInstance();
			break;


		}

		return baseModelObj;
	}

	/*
	 * This method deletes an instance of a model class based on the type passed
	 * in as an argument. The type is passed in by the listview item, because it
	 * is the only entity which knows, what type of policy it is.
	 */

	public static void freeInstance(int type) {

		switch (type) {

		case SAConstants.CONTAINER_CREATION_REMOVAL:
			ContainerCreationRemovalModel.freeInstance();
			break;

		case SAConstants.LWC_MODE_CONFIG_TYPE:
			LightWeightConfigTypeModel.freeInstance();
			break;

		}

	}

	// This method contains the logic to activate admin

	public boolean activateAdmin() {

		try {
			if (mDPM == null) {
				mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
			}
			if (mCN == null) {
				mCN = new ComponentName(mContext, DeviceAdministrator.class);
			}

			if (mDPM != null && !mDPM.isAdminActive(mCN)) {
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mCN);
				((Activity) mContext).startActivityForResult(intent, SAConstants.RESULT_ENABLE_ADMIN);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
		return false;
	}

	// This method contains the logic to deactivate admin

	public boolean deactivateAdmin() {

		if (mDPM == null) {
			mDPM = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
		}
		if (mCN == null) {
			mCN = new ComponentName(mContext, DeviceAdministrator.class);
		}

		try {
			if (mDPM != null && mDPM.isAdminActive(mCN)) {
				mDPM.removeActiveAdmin(mCN);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
		return false;
	}

	// This method contains the logic to activate KLM with Admin enabled
	public void activateKLMWithAdminEnabled(String klmsKey) {

		System.out.println("just about to activate KLM");

		KnoxEnterpriseLicenseManager klmsMgr = null;

		try {
			if (klmsMgr == null) {
				klmsMgr = KnoxEnterpriseLicenseManager.getInstance(mContext);
			}

			klmsMgr.activateLicense(klmsKey);
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
	}

	// This method contains the logic to activate ELM with Admin enabled
	public void activateELMWithAdminEnabled(String elmKey) {

		EnterpriseLicenseManager elmMgr = null;

		try {
			if (elmMgr == null) {
				elmMgr = EnterpriseLicenseManager.getInstance(mContext);
			}

			elmMgr.activateLicense(elmKey);
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
	}

	// This method contains the logic to activate KLM
	public void activateKLM(String klmsKey) {

		System.out.println("just about to activate KLM");

		KnoxEnterpriseLicenseManager klmsMgr = null;

		try {
			if (klmsMgr == null) {
				klmsMgr = KnoxEnterpriseLicenseManager.getInstance(mContext);
			}

			klmsMgr.activateLicense(klmsKey, mContext.getPackageName());
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
	}

	// This method contains the logic to activate ELM
	public void activateELM(String elmKey) {

		EnterpriseLicenseManager elmMgr = null;

		try {
			if (elmMgr == null) {
				elmMgr = EnterpriseLicenseManager.getInstance(mContext);
			}

			elmMgr.activateLicense(elmKey, mContext.getPackageName());
		} catch (Exception e) {
			Log.e(BaseModel.this.getClass().getSimpleName(), e.getMessage());
		}
	}

	@Override
	public void invokeKNOXAPI(ListItem listItemObj, String userEnteredData,
			ActivityLauncher activityLauncher) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invokeKNOXAPI(ListItem listItemObj, boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fetchState(ListItem listItemObj) {
		// TODO Auto-generated method stub
		return false;
	}


	

}
