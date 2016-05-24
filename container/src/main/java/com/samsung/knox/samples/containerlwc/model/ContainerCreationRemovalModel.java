package com.samsung.knox.samples.containerlwc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.enterprise.ApplicationPolicy;
import android.app.enterprise.EnterpriseDeviceManager;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.samsung.knox.samples.containerlwc.R;
import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.controller.Controller;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.samsung.knox.samples.containerlwc.utils.SACodeUtils;
import com.sec.enterprise.knox.EnterpriseKnoxManager;
import com.sec.enterprise.knox.container.CreationParams;
import com.sec.enterprise.knox.container.KnoxConfigurationType;
import com.sec.enterprise.knox.container.KnoxContainerManager;

// This class deals with KnoxContainerManager APIs

public class ContainerCreationRemovalModel extends BaseModel {

	String TAG = "ContainerCreationRemovalModel";
	static Context mContext;

	static ContainerCreationRemovalModel containerCreationRemovalModelObj;

	private ContainerCreationRemovalModel() {
		// Get the current activity mContext
		mContext = Controller.getCurrentContext();

	}

	// This method creates an instance of this class
	public static synchronized ContainerCreationRemovalModel getInstance() {

		if (containerCreationRemovalModelObj == null) {
			containerCreationRemovalModelObj = new ContainerCreationRemovalModel();
		}
		return containerCreationRemovalModelObj;
	}

	// This method deletes the existing instance of this class
	public static synchronized void freeInstance() {

		if (containerCreationRemovalModelObj != null) {
			containerCreationRemovalModelObj = null;
		}
	}

	/*
	 * This method is an overridden method from class BaseModel. It handles
	 * calling of all such KNOX APIs, which enables/disables a policy by
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
		// TODO Auto-generated method stub

		int listItemObjId = listItemObj.itemId;
		Log.i(TAG,
				"in ContainerCreationRemovalModel invokeKNOXAPI, listItemObjId "
						+ listItemObjId);
		// itemId is the row index of the item of the listview, which was
		// clicked
		switch (listItemObjId) {

		// Get configuration Types
		case 0:

			String[] knoxConfigTypeNameList = null;
			// Get the configuration types
			List<KnoxConfigurationType> knoxConfigTypeList = KnoxContainerManager
					.getConfigurationTypes();
			knoxConfigTypeNameList = new String[knoxConfigTypeList.size()];
			int i = 0;
			for (KnoxConfigurationType knoxConfigTypeObj : knoxConfigTypeList) {
				knoxConfigTypeNameList[i++] = knoxConfigTypeObj.getName();
			}
			if (knoxConfigTypeNameList != null
					&& knoxConfigTypeNameList.length > 0) {
				// Show all configuration types in a list
				Controller.showAlertWithList(knoxConfigTypeNameList,
						SAConstants.SELECT_CONTAINER_TYPE, SAConstants.TYPE);
			} else
				Controller.showToast(SAConstants.NO_ITEMS_TO_SHOW);

			break;

		// Create container
		case 2:

			try {
				// Get the container type selected by user
				String type = (String) Controller.getSharedPrefData(
						SAConstants.TYPE, SAConstants.STRING_DATATYPE);
				// Create the container of the user selected type. If the user
				// did not select any container type, knox-b2b is selected
				// as type by default
				int requestid = KnoxContainerManager.createContainer(type);
				Log.i(TAG, "Case 2 Enming createContainer(type) is called");

				// If an error is thrown
				if (requestid < 0) {
					// Get the message (to be displayed to user), by passing in
					// the error code
					String messageToUser = SACodeUtils.getMessage(requestid,
							mContext);
					// show toast message to user displaying the issue
					Controller.showToast(messageToUser);
				}
				// if container is created successfully
				else {
					Log.d(TAG, SAConstants.CONTAINER_CREATION_PROGRESS
							+ requestid);
					Controller
							.showToast(SAConstants.CONTAINER_CREATION_PROGRESS
									+ requestid);
				}
			} catch (SecurityException e) {
				Log.e(TAG, e.getMessage());
			}
			break;

		// Create SDP enabled container
		case 3:

			try {
				// Get the container type selected by user
				String type = (String) Controller.getSharedPrefData(
						SAConstants.TYPE, SAConstants.STRING_DATATYPE);
				// create a CreationParams object
				CreationParams params = new CreationParams();

				// Build creation params as per needs.
				params.setConfigurationName(type);
				// The key used by administrator in following API is mandatory
				// to enable SDP otherwise appropriate error code will be
				// returned.

				// Password reset token shall be at least 8 characters long, and
				// composed of a combination with at least one letter and one
				// non-letter.
				params.setPasswordResetToken(SAConstants.PWD_RESET_TOKEN);

				// create container by passing in the CreationParams object
				int requestid = KnoxContainerManager.createContainer(params);
				Log.i(TAG, "Case 2 Enming createContainer(params) is called");

				// If an error is thrown
				if (requestid < 0) {
					// Get the message (to be displayed to user), by passing in
					// the error code
					String messageToUser = SACodeUtils.getMessage(requestid,
							mContext);
					// show toast message to user displaying the issue
					Controller.showToast(messageToUser);
				}
				// if container is created successfully
				else {
					Log.d(TAG, SAConstants.CONTAINER_CREATION_PROGRESS
							+ requestid);
					Controller
							.showToast(SAConstants.CONTAINER_CREATION_PROGRESS
									+ requestid);
				}
			} catch (SecurityException e) {
				Log.e(TAG, e.getMessage());
			}
			break;

		// Get containers
		case 4:
			List<Integer> arrListContainers = KnoxContainerManager
					.getContainers();
			if (arrListContainers != null && arrListContainers.size() > 0) {
				// Format container ids by putting commas between two ids
				String csvContainers = arrListContainers.toString()
						.replace("[", "").replace("]", "").replace(", ", ",");

				Controller.showToast(listItemObj.name + " : " + csvContainers);
			} else {
				Controller.showToast(mContext
						.getString(R.string.no_container_exists));
			}
			break;
		}
	}

	/*
	 * This method is an overridden method from class BaseModel. It handles
	 * calling of all such KNOX APIs, which require a user input in an EditText
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
	@Override
	public void invokeKNOXAPI(ListItem listItemObj, String userEnteredData,
			ActivityLauncher activityLauncher) {

		List<Integer> arrListContainers = null;
		boolean containerIdFound = false;
		int listItemObjId = listItemObj.itemId;
		int userEnteredContainerId;
		String type = null;
		switch (listItemObjId) {

		// Create BYOD container with admin inside container
		case 2:

			try {

				int requestid = -1;

				// Get the container type selected by user
				type = (String) Controller.getSharedPrefData(SAConstants.TYPE,
						SAConstants.STRING_DATATYPE);

				// check if user wants to uninstall self from device and
				// save
				// the user
				// response in a shared preference file
				if (userEnteredData.equalsIgnoreCase(mContext
						.getString(R.string.yes))) {
					Controller.setSharedPrefData(SAConstants.DO_SELF_UNINSTALL,
							SAConstants.YES, SAConstants.STRING_DATATYPE);
				} else {
					Controller.setSharedPrefData(SAConstants.DO_SELF_UNINSTALL,
							SAConstants.NO, SAConstants.STRING_DATATYPE);
				}

				requestid = KnoxContainerManager.createContainer(type, mContext.getPackageName());
				Log.i(TAG, "Case 2 Enming createContainer(type, mContext.getPackageName()) is called");

				// If an error is thrown
				if (requestid < 0) {
					// Get the message (to be displayed to user), by passing
					// in
					// the error code
					String messageToUser = SACodeUtils.getMessage(requestid,
							mContext);
					// show toast message to user displaying the issue
					Controller.showToast(messageToUser);
				}
				// if container is created successfully
				else {
					Log.d(TAG, SAConstants.CONTAINER_CREATION_PROGRESS
							+ requestid);
					Controller
							.showToast(SAConstants.CONTAINER_CREATION_PROGRESS
									+ requestid);
				}
			} catch (SecurityException e) {
				Log.e(TAG, e.getMessage());
			}
			break;

		case 3:

			try {

				// Get the container type selected by user
				type = (String) Controller.getSharedPrefData(SAConstants.TYPE,
						SAConstants.STRING_DATATYPE);

				// check if user wants to uninstall self from device and
				// save
				// the user
				// response in a shared preference file
				if (userEnteredData.equalsIgnoreCase(mContext
						.getString(R.string.yes))) {
					Controller.setSharedPrefData(SAConstants.DO_SELF_UNINSTALL,
							SAConstants.YES, SAConstants.STRING_DATATYPE);
				} else {
					Controller.setSharedPrefData(SAConstants.DO_SELF_UNINSTALL,
							SAConstants.NO, SAConstants.STRING_DATATYPE);
				}

				// create a CreationParams object
				CreationParams params = new CreationParams();

				// Build creation params as per needs.
				params.setConfigurationName(type);
				// The key used by administrator in following API is mandatory
				// to enable SDP otherwise appropriate error code will be
				// returned.

				// Password reset token shall be at least 8 characters long, and
				// composed of a combination with at least one letter and one
				// non-letter.
				params.setPasswordResetToken(SAConstants.PWD_RESET_TOKEN);
				params.setAdminPackageName(mContext.getPackageName());

				// create container by passing in the CreationParams object
				int requestid = KnoxContainerManager.createContainer(params);
				Log.i(TAG, "Case 2 Enming createContainer(params) is called");

				// If an error is thrown
				if (requestid < 0) {
					// Get the message (to be displayed to user), by passing in
					// the error code
					String messageToUser = SACodeUtils.getMessage(requestid,
							mContext);
					// show toast message to user displaying the issue
					Controller.showToast(messageToUser);
				}
				// if container is created successfully
				else {
					Log.d(TAG, SAConstants.CONTAINER_CREATION_PROGRESS
							+ requestid);
					Controller
							.showToast(SAConstants.CONTAINER_CREATION_PROGRESS
									+ requestid);
				}
			} catch (SecurityException e) {
				Log.e(TAG, e.getMessage());
			}
			break;

		// Remove container
		case 5:
			// Get all the containers owned by this admin
			arrListContainers = KnoxContainerManager.getContainers();
			// Get the container id entered by user
			userEnteredContainerId = Integer.parseInt(userEnteredData);

			for (int id : arrListContainers) {
				if (userEnteredContainerId == id) {
					containerIdFound = true;
					break;
				}
			}
			// if the container with container id entered by user is owned by
			// this admin
			if (containerIdFound) {
				try {
					// Remove container
					int errorCode = KnoxContainerManager
							.removeContainer(userEnteredContainerId);
					// if successful
					if (errorCode == KnoxContainerManager.REMOVE_CONTAINER_SUCCESS) {
						// success
						String currContIdStr = (String) Controller
								.getSharedPrefData(
										SAConstants.CURRENT_CONTAINER_ID,
										SAConstants.STRING_DATATYPE);
						int currContIdInt = -1;
						// if current container id is the one, which is removed
						if (currContIdStr != null) {
							currContIdInt = Integer.parseInt(currContIdStr);
						}
						if (currContIdInt != -1
								&& userEnteredContainerId == currContIdInt) {
							// set current container id to null in shared
							// preference
							Controller.setSharedPrefData(
									SAConstants.CURRENT_CONTAINER_ID, null,
									SAConstants.STRING_DATATYPE);
						}
						Controller
								.showToast(SAConstants.CONTAINER_UNINSTALLATION_SUCCESSFUL);

					}
					// if not successful
					else {
						// Get the message (to be displayed to user) by passing
						// in error code
						String messageToUser = SACodeUtils.getMessage(
								errorCode, mContext);
						Controller.showToast(messageToUser);
					}

				} catch (SecurityException e) {
					Log.e(TAG, e.getMessage());
				}

			}
			// if the container id entered by user does not exist or not owned
			// by this admin
			else {
				Controller.showToast(SAConstants.CONTAINER_DOES_NOT_EXIST);
			}
			containerIdFound = false;
			break;

		///// Install Application into the container (and backup and restore its data and preferences)
		case 6:
			// Get all the containers owned by this admin
			arrListContainers = KnoxContainerManager.getContainers();
			// Get the container id entered by user
			userEnteredContainerId = Integer.parseInt(userEnteredData);

			for (int id : arrListContainers) {
				if (userEnteredContainerId == id) {
					containerIdFound = true;
					break;
				}
			}
			// if the container with container id entered by user is owned by
			// this admin
			if (containerIdFound) {
				try {
                    //final String testpackageName = "com.paris.casino";
                    //final String testpackageName = "com.android.chrome";
                    //final String testpackageName = "com.samsung.knox.samples.containerlwc";
                    //final String testpackageName = "com.facebook.katana";
                    final String testpackageName = "com.trantrigroup.note";

                    // When you create container successfully, containerID will be returned via intent.
                    // Use this containerID in below API.
                    EnterpriseKnoxManager ekm = EnterpriseKnoxManager.getInstance();
                    KnoxContainerManager kmcm = ekm.getKnoxContainerManager(mContext, userEnteredContainerId);
                    final ApplicationPolicy appPolicyContainer = kmcm.getApplicationPolicy();

                    // This is the ApplicationPolicy for managing App in normal case
                    EnterpriseDeviceManager edm = new EnterpriseDeviceManager(mContext);
                    final ApplicationPolicy appPolicyNormal = edm.getApplicationPolicy();

                    final File dataFile = new File(mContext.getFilesDir(), testpackageName + ".data");

                    // 1. Backup the data of the Application
					Runnable taskToBackupData = new Runnable() {
						public void run() {
							ParcelFileDescriptor dataDesc;
							// we use dataTempFile so we don't mess up existing backup data if
							// operation fails
							//File dataTempFile = new File(mContext.getFilesDir(), testpackageName + ".temp");

							int ret = 0;
							try {
								dataDesc = ParcelFileDescriptor.open(dataFile, ParcelFileDescriptor.MODE_READ_WRITE
										| ParcelFileDescriptor.MODE_CREATE | ParcelFileDescriptor.MODE_TRUNCATE);
								try {
									ret = appPolicyNormal.backupApplicationData(testpackageName, dataDesc);
								} catch (SecurityException e) {
									Log.w(TAG, "Back SecurityException: " + e.getMessage());
                                    //Controller.showToast("Back SecurityException: " + e.getMessage());
								}
								if (ret == ApplicationPolicy.BACKUP_APPLICATION_SUCCESS) {
                                    //Controller.showToast("Backup : data is backed up successfully");
                                    Log.v(TAG, "Backup : data is backed up successfully");
                                    dataDesc.close();
                                    // if operation was successfull, store the data in the permanent
									// file
//									if (dataFile.exists()) {
//										dataFile.delete();
//									}
									// at this point, you need to copy from dataTempFile to dataFile
									//pyFiles(dataTempFile, dataFile);
								} else if (ret == ApplicationPolicy.BACKUP_APPLICATION_BUSY_SERVICE) {
                                    Log.w(TAG, "Backup : data backup service is busy");
                                    //Controller.showToast("Backup : data backup service is busy");
									// a conflicting operation is already running, try
									// again later (we cannot predict, but usually a
									// few minutes will be more
									// than enough) or
									// put a broadcast receiver listening for the intent
									// ACTION_EDM_BACKUP_SERVICE_AVAILABLE to retry
									// when the resource is available
								} else if (ret == ApplicationPolicy.BACKUP_APPLICATION_SERVICE_ERROR) {
                                    Log.w(TAG, "Backup : the Enterprise service was not available");
                                    //Controller.showToast("Backup : the Enterprise service was not available");
									// the Enterprise service was not available
								} else if (ret == ApplicationPolicy.BACKUP_APPLICATION_FAILED) {
                                    Log.e(TAG, "Backup : restore data failed, error");
                                    //Controller.showToast("Backup : restore data failed, error");
									// a generic error occurred between the operations like an
									// IOException.
								}
//								if (dataTempFile.exists()) {
//									dataTempFile.delete();
//								}
							} catch (FileNotFoundException e) {
                                //Controller.showToast("Backup Error: file not found" + e.getMessage());
								Log.w(TAG, "Backup Error: file not found");
							} catch (IOException e) {
                                //Controller.showToast("Backup Error: file not found" + e.getMessage());
								Log.w(TAG, "Backup Error: IO exception");
							}
						}
					};
					Thread threadToBackupData = new Thread(taskToBackupData);
					threadToBackupData.start();

                    Thread.sleep(1000);

					// 2. Migrate the Application into container
					try {
						boolean result = appPolicyContainer.installApplication(testpackageName);
						if (true == result) {
							Log.d(TAG, "Installing an application package has been successful!");
							//Controller.showToast(SAConstants.CONTAINER_INSTALLATION_APPLICATION_SUCCESSFUL);
						} else {
							Log.w(TAG, "Installing an application package has failed.");
							//Controller.showToast(SAConstants.CONTAINER_INSTALLATION_APPLICATION_FAILED);
						}
					} catch (SecurityException e) {
                        //Controller.showToast("Installation SecurityException: " + e.getMessage());
						Log.w(TAG, "Installation SecurityException: " + e.getMessage());
					}

					// 3. Restore the data of the Application
					Runnable taskToRestoreData = new Runnable() {
						public void run() {
							ParcelFileDescriptor dataDesc = null;
							//File dataFile = new File(mContext.getFilesDir(), testpackageName + ".data");
							try {
								dataDesc = ParcelFileDescriptor.open(dataFile, ParcelFileDescriptor.MODE_READ_ONLY
										| ParcelFileDescriptor.MODE_CREATE);
							} catch (FileNotFoundException e) {
                                //Controller.showToast("Restore Error: file not found");
								Log.w(TAG, "Restore Error: file not found");
							}
							int ret2 = 0;
							try {
								ret2 = appPolicyContainer.restoreApplicationData(testpackageName, dataDesc);
							} catch (SecurityException e) {
                                //Controller.showToast("Restore SecurityException: " + e.getMessage());
								Log.w(TAG, "Restore SecurityException: " + e.getMessage());
							}
							if (ret2 == ApplicationPolicy.BACKUP_APPLICATION_SUCCESS) {
                                //Controller.showToast("Restore : data is restored successfully");
								Log.v(TAG, "Restore : data is restored successfully");
                                try {
                                    dataDesc.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // data was restored
							} else if (ret2 == ApplicationPolicy.BACKUP_APPLICATION_BUSY_SERVICE) {
                                //Controller.showToast("Restore : data restore service is busy");
								Log.w(TAG, "Restore : data restore service is busy");
								// a conflicting operation is already running, try
								// again later (we cannot predict, but usually a
								// few minutes will be more
								// than enough) or
								// put a broadcast receiver listening for the intent
								// ACTION_EDM_BACKUP_SERVICE_AVAILABLE to retry
								// when the resource is available
							} else if (ret2 == ApplicationPolicy.BACKUP_APPLICATION_SERVICE_ERROR) {
                                //Controller.showToast("Restore : the Enterprise service was not available");
								Log.w(TAG, "Restore : the Enterprise service was not available");
								// the Enterprise service was not available
							} else if (ret2 == ApplicationPolicy.BACKUP_APPLICATION_FAILED) {
                                //Controller.showToast("Restore : restore data failed, error");
								Log.e(TAG, "Restore : restore data failed, error");
								// a generic error occurred between the operations like
								// an IOException or data was invalid.
							}
						}
					};
					Thread threadToRestoreData = new Thread(taskToRestoreData);
					threadToRestoreData.start();

//                    // 4. Uninstall the Application outside the container
//                    try {
//                        boolean result = appPolicyNormal.uninstallApplication(testpackageName, false);
//                        if (true == result) {
//                            Log.d(TAG, "Uninstallation of an application package has been successful!");
//                        } else {
//                            Log.w(TAG, "Uninstallation of an application package has failed.");
//                        }
//                    } catch (SecurityException e) {
//                        Log.w(TAG, "SecurityException: " + e);
//                    }

                } catch (SecurityException e) {
                    Controller.showToast(e.getMessage());
					Log.e(TAG, e.getMessage());
				} catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			// if the container id entered by user does not exist or not owned
			// by this admin
			else {
				Controller.showToast(SAConstants.CONTAINER_DOES_NOT_EXIST);
			}
			containerIdFound = false;
			break;
		}

	}

}
