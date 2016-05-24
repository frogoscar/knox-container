package com.samsung.knox.samples.containerlwc.model;

import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.controller.Controller;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.sec.enterprise.knox.container.KnoxContainerManager;
import com.sec.enterprise.knox.container.LightweightConfigurationType;

//This class deals with LightweightConfigurationType APIs

public class LightWeightConfigTypeModel extends BaseModel {

	LightweightConfigurationType mLWCType;
	static LightWeightConfigTypeModel lightWeightConfigTypeModelObj;

	private LightWeightConfigTypeModel() {

		if (mLWCType == null) {
			// Get the LightweightConfigurationType instance
			LightweightConfigurationType predefinedConfig = (LightweightConfigurationType) KnoxContainerManager
					.getConfigurationTypeByName(SAConstants.KNOX_LWC);
			// Clone and assign a new name to the new type to be created
			mLWCType = predefinedConfig.clone(SAConstants.TEMP);
			mLWCType.setFolderHeaderTitle(SAConstants.TEMP);
		}
	}

	// This method creates an instance of this class
	public static synchronized LightWeightConfigTypeModel getInstance() {

		if (lightWeightConfigTypeModelObj == null) {
			lightWeightConfigTypeModelObj = new LightWeightConfigTypeModel();
		}
		return lightWeightConfigTypeModelObj;
	}

	// This method deletes the existing instance of this class
	public static synchronized void freeInstance() {

		if (lightWeightConfigTypeModelObj != null) {
			lightWeightConfigTypeModelObj = null;
		}
	}

	/*
	 * This method is an overridden method from class BaseModel. It
	 * handles calling of all such KNOX APIs, which enables/disables a policy by
	 * checking/un-checking a check box or which performs some operation, like,
	 * creating a container. Basically, all those APIs are handled by this
	 * method, which do not require a user input.
	 * 
	 * Input parameters:
	 * 
	 * listItemObj: It contains the item object (of the listview) which was
	 * clicked by the user.
	 * 
	 * enable: It specifies whether to set the policy to true or false
	 */
	@Override
	public void invokeKNOXAPI(ListItem listItemObj, boolean enable) {

		int listItemObjId = listItemObj.itemId;

		switch (listItemObjId) {

		// Get Folder header title
		case 2:

			String folderHeaderTitle = mLWCType.getFolderHeaderTitle();
			Controller.showToast(SAConstants.FOLDER_HEADER_TITLE_IS
					+ folderHeaderTitle);
			break;

		}

	}

	/*
	 * This method is an overridden method from class BaseModel. It
	 * handles calling of all such KNOX APIs, which require a user input in an
	 * EditText field.
	 * 
	 * Input parameters:
	 * 
	 * userEnteredData: It captures the user input.
	 * 
	 * listItemObj: It contains the item object (of the listview) which was
	 * clicked by the user.
	 * 
	 * activityLauncher: It is a reference variable of the interface
	 * 'ActivityLauncher'. It will be used to launch an activity after calling a
	 * KNOX API.
	 */
	@Override
	public void invokeKNOXAPI(ListItem listItemObj, String userEnteredData,
			ActivityLauncher activityLauncher) {

		int listItemObjId = listItemObj.itemId;
		boolean success = false;

		switch (listItemObjId) {

		// Set name of the configuration type
		case 0:

			if (mLWCType != null) {
				mLWCType.setName(userEnteredData);
			}
			break;

		// Set Folder header title
		case 1:

			if (mLWCType != null) {
				mLWCType.setFolderHeaderTitle(userEnteredData);
				Controller.showToast(SAConstants.FOLDER_HEADER_TITLE_SET_TO
						+ userEnteredData);
			}
			break;

		// add the newly created configuration type
		case 3:
			success = KnoxContainerManager.addConfigurationType(
					Controller.getCurrentContext(), mLWCType);
			if (success) {
				Controller.showToast(SAConstants.CONFIGURATION_TYPE_ADDED);
				// after adding the type, go back to
				// ContainerCreationRemovalActivity.java
				// by finishing this activity
				if (activityLauncher != null) {
					activityLauncher.finishCurrentActivity();
				}
			}
			// if the config type name used by user already exists
			else {
				Controller.showToast(SAConstants.CONFIG_NAME_ALREADY_EXISTS);
			}

		}

	}

}
