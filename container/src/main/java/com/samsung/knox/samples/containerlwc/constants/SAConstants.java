package com.samsung.knox.samples.containerlwc.constants;

//This interface captures all constants used in the project

public interface SAConstants {

	String ENABLE_ADMIN = "Enable Admin";
	String DISABLE_ADMIN = "Disable Admin";
	String MY_PREFS_NAME = "SampleApps";
	String ADMIN = "admin";
	String BYOD = "byod";
	String KLM = "klm";
	String ELM = "elm";
	String ADMIN_ALREADY_ENABLED = "Admin already enabled";
	String ADMIN_ALREADY_DISABLED = "Admin already disabled";
	String DEVICE_ADMIN_ENABLED = "Device Admin enabled";
	String DEVICE_ADMIN_DISABLED = "Device Admin disabled";
	String DISABLE_ADMIN_WARNING = "Do you want to disable the administrator?";
	String CANCELLED_ENABLING_DEVICE_ADMIN = "Cancelled Enabling Device Administrator";
	String CANCELLED_DISABLING_DEVICE_ADMIN = "Cancelled Disabling Device Administrator";

	String PLEASE_DISABLE_ADMIN_FIRST = "Please disable the admin first";
	String CONTAINER_UNINSTALLATION_SUCCESSFUL = "Container uninstallation is successful";
	String CONTAINER_INSTALLATION_APPLICATION_SUCCESSFUL = "Installation of Application in Container is successful";
	String CONTAINER_INSTALLATION_APPLICATION_FAILED = "Installation of Application in Container failed";
	String CONTAINER_UNINSTALLATION_FAILED = "Container uninstallation failed";
	String CONTAINER_DOES_NOT_EXIST = "This container does not exist";
	String CURRENT_CONTAINER_ID = "current container id";
	String NO_ITEMS_TO_SHOW = "No items to show";
	String PWD_RESET_TOKEN = "SamSung1!";
	String KLM_ACTIVATION_SUCCESS = "KLM activation successful";
	String KLM_ACTIVATION_FAILURE = "KLM activation failed with errorcode: ";
	String ELM_ACTIVATION_SUCCESS = "ELM activation successful";
	String ELM_ACTIVATION_FAILURE = "ELM activation failed with errorcode: ";
	String LICENSE_KEY = "License Key";
	String ENTER_KLM_KEY = "Please enter the KLM key";
	String ENTER_ELM_KEY = "Please enter the ELM key";
	String OK = "OK";
	String CANCEL = "Cancel";
	String LICENSE_RESULT = "License result";
	String KNOX_B2B = "knox-b2b";
	String KNOX_COM = "knox-b2b-com";
	String CONTAINER_CREATION = "Container Creation";
	String CONTAINER_CREATION_FAILED = "Container creation failed with errorcode: ";
	String CONTAINER_CREATION_PROGRESS = "Container creation in progress with id: ";
	String FAKE_INTENT = "Intent belongs to another MDM or it is a fake intent";
	String CONTAINER_CREATED_SUCCESSFULLY = "Container created successfully";
	String NOT_SUPPORTED = "This API is not supported on this device";
	String MODEL_TAG = "Model";

	String CONFIGURATION_TYPE_ADDED = "Configuration Type added successfully";
	String CONFIG_NAME_ALREADY_EXISTS = "This configuration type name already exists";
	String KNOX_LWC = "knox-b2b-lwc";
	
	int NONE = -1;
	int INITIAL_STATE = 0;
	int RESULT_ENABLE_ADMIN = 1;
	int RESULT_DISABLE_ADMIN = 2;
	int RESULT_KLM_ACTIVATED = 3;
	int RESULT_ELM_ACTIVATED = 4;
	int RESULT_BYOD_ENABLED = 5;
	int RESULT_BYOD_DISABLED = 6;
	int DEFAULT_ERROR = -888;
	String SELECT_CONTAINER_TYPE = "Select a Container type";

	String DO_SELF_UNINSTALL = "do self uninstall";
	String YES = "yes";
	String NO = "no";
	String TYPE = "type";
	String TEMP = "temp";

	int CONTAINER_CREATION_REMOVAL = 0;
	int LWC_MODE_CONFIG_TYPE = 1;
	
	String FOLDER_HEADER_TITLE_SET_TO = "Folder header title set to ";
	String FOLDER_HEADER_TITLE_IS = "Folder header title is ";
	String CUSTOM_CONFIGURATION_DELETED = "Custom configuration type deleted successfully";
	String CANNOT_DELETE_CUSTOM_CONFIG = "Unable to delete custom configuration type";
	String CAMERA_APP_PKG_NAME = "com.sec.android.app.camera";

	String API_NAME = "API_NAME";

	int STRING_DATATYPE = 0;
	int STRINGSET_DATATYPE = 1;
	int BOOLEAN_DATATYPE = 2;
	
}