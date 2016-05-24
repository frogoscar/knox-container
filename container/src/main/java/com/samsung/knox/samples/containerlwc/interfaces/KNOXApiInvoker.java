package com.samsung.knox.samples.containerlwc.interfaces;

import com.samsung.knox.samples.containerlwc.beans.ListItem;


/*
 * This interface is used by implementing classes to handle
 * calling of all KNOX APIs
 * 
 */

public interface KNOXApiInvoker {

	/*
	 * This method, defined by a class implementing this interface, handles
	 * calling of all KNOX APIs, which require a user input in an EditText
	 * field.
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

	public void invokeKNOXAPI(ListItem listItemObj, String userEnteredData,
			ActivityLauncher activityLauncher);

	/*
	 * This method, defined by a class implementing this interface, handles
	 * calling of all KNOX APIs, which can be enabled/disabled via a check box
	 * 
	 * Input parameters:
	 * 
	 * listItemObj: It contains the item object (of the listview) which was
	 * clicked by the user.
	 * 
	 * enable: A boolean to enable/disable a policy
	 */

	public void invokeKNOXAPI(ListItem listItemObj, boolean enable);

	/*
	 * This method, defined by a class implementing this interface, fetches
	 * state of policies, which then checks/un-checks the corresponding policy
	 * check box
	 * 
	 * Input parameters:
	 * 
	 * listItemObj: It contains the item object (of the listview) which was
	 * clicked by the user.
	 */

	public boolean fetchState(ListItem listItemObj);

}
