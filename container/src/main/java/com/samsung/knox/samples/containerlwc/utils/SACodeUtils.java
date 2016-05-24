package com.samsung.knox.samples.containerlwc.utils;

import android.content.Context;
import android.util.SparseIntArray;

import com.samsung.knox.samples.containerlwc.R;
import com.sec.enterprise.knox.container.KnoxContainerManager;
import com.sec.enterprise.knox.license.KnoxEnterpriseLicenseManager;

//This class creates a map of all codes and corresponding messages to be displayed to user,
//if a particular code is returned by an API

public class SACodeUtils {

	public static final String TAG = SACodeUtils.class.getSimpleName();
	static final SparseIntArray mapCodes = new SparseIntArray();

	private SACodeUtils() {
		throw new AssertionError();
	}

	// populate the codes in a map
	public static void populateCodes() {
		mapCodes.clear();
		mapCodes.put(KnoxContainerManager.ERROR_ADMIN_ACTIVATION_FAILED,
				R.string.err_admin_act_failed);
		mapCodes.put(KnoxContainerManager.ERROR_ADMIN_INSTALLATION_FAILED,
				R.string.err_admin_install_failed);
		mapCodes.put(
				KnoxContainerManager.ERROR_CONTAINER_MODE_CREATION_FAILED_BYOD_NOT_ALLOWED,
				R.string.err_container_creation_failed_byod_na);
		mapCodes.put(
				KnoxContainerManager.ERROR_CONTAINER_MODE_CREATION_FAILED_CONTAINER_EXIST,
				R.string.err_container_creation_failed_container_exist);
		mapCodes.put(
				KnoxContainerManager.ERROR_CONTAINER_MODE_CREATION_FAILED_KIOSK_ON_OWNER_EXIST,
				R.string.err_container_creation_failed_kiosk_exist);
		mapCodes.put(KnoxContainerManager.ERROR_CREATION_ALREADY_IN_PROGRESS,
				R.string.err_creation_in_progress);
		mapCodes.put(KnoxContainerManager.ERROR_CREATION_CANCELLED,
				R.string.err_creation_cancelled);
		mapCodes.put(
				KnoxContainerManager.ERROR_CREATION_FAILED_CONTAINER_MODE_EXIST,
				R.string.err_creation_failed_container_mode_exists);
		mapCodes.put(KnoxContainerManager.ERROR_CREATION_FAILED_TIMA_DISABLED,
				R.string.err_creation_failed_tima_disabled);
		mapCodes.put(KnoxContainerManager.ERROR_DOES_NOT_EXIST,
				R.string.err_does_not_exist);
		mapCodes.put(KnoxContainerManager.ERROR_FILESYSTEM_ERROR,
				R.string.err_filesystem_error);
		mapCodes.put(KnoxContainerManager.ERROR_INTERNAL_ERROR,
				R.string.err_internal_error);
		mapCodes.put(KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN,
				R.string.err_invalid_password_reset_token);
		mapCodes.put(KnoxContainerManager.ERROR_MAX_LIMIT_REACHED,
				R.string.err_max_limit_reached);
		mapCodes.put(KnoxContainerManager.ERROR_NO_ADMIN_APK,
				R.string.err_no_admin_apk);
		mapCodes.put(KnoxContainerManager.ERROR_NO_CONFIGURATION_TYPE,
				R.string.err_no_config_type);
		mapCodes.put(
				KnoxContainerManager.ERROR_PLATFORM_VERSION_MISMATCH_IN_CONFIGURATION_TYPE,
				R.string.err_platform_version_mismatch);
		mapCodes.put(KnoxContainerManager.ERROR_SDP_NOTSUPPORTED,
				R.string.err_sdp_not_supported);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_CLOSE_COMMUNICATION_ERROR,
				R.string.tz_common_close_comm_error);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_COMMUNICATION_ERROR,
				R.string.tz_common_comm_error);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_INIT_ERROR,
				R.string.tz_common_init_error);
		mapCodes.put(
				KnoxContainerManager.TZ_COMMON_INIT_ERROR_TAMPER_FUSE_FAIL,
				R.string.tz_common_init_error_tamper_fuse_fail);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_INIT_MSR_MISMATCH,
				R.string.tz_init_msr_mismatch);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_INIT_MSR_MODIFIED,
				R.string.tz_init_msr_modified);
		mapCodes.put(
				KnoxContainerManager.TZ_COMMON_INIT_UNINITIALIZED_SECURE_MEM,
				R.string.tz_common_init_uninitialized_secure_mem);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_INTERNAL_ERROR,
				R.string.tz_internal_error);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_NULL_POINTER_EXCEPTION,
				R.string.tz_npe);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_RESPONSE_REQUEST_MISMATCH,
				R.string.tz_common_res_req_mismatch);
		mapCodes.put(KnoxContainerManager.TZ_COMMON_UNDEFINED_ERROR,
				R.string.tz_common_undefined_error);
		mapCodes.put(KnoxContainerManager.TZ_KEYSTORE_ERROR,
				R.string.tz_keystore_error);
		mapCodes.put(KnoxContainerManager.TZ_KEYSTORE_INIT_FAILED,
				R.string.tz_keystore_init_failed);
		mapCodes.put(KnoxContainerManager.REMOVE_CONTAINER_SUCCESS,
				R.string.remove_container_success);
		mapCodes.put(KnoxContainerManager.CONTAINER_ACTIVE,
				R.string.container_active);
		mapCodes.put(KnoxContainerManager.CONTAINER_INACTIVE,
				R.string.container_inactive);
		mapCodes.put(KnoxContainerManager.CONTAINER_CREATION_IN_PROGRESS,
				R.string.container_creation_in_progress);
		mapCodes.put(KnoxContainerManager.CONTAINER_LOCKED,
				R.string.container_locked);
		mapCodes.put(KnoxContainerManager.CONTAINER_DOESNT_EXISTS,
				R.string.container_does_not_exist);
		mapCodes.put(KnoxContainerManager.CONTAINER_REMOVE_IN_PROGRESS,
				R.string.container_removal_in_progress);
		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_INTERNAL,
				R.string.err_internal);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_INTERNAL_SERVER,
				R.string.err_internal_server);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_INVALID_LICENSE,
				R.string.err_licence_invalid_license);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_INVALID_PACKAGE_NAME,
				R.string.err_invalid_package_name);

		mapCodes.put(
				KnoxEnterpriseLicenseManager.ERROR_LICENSE_ACTIVATION_NOT_FOUND,

				R.string.err_licence_activation_not_found);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED,
				R.string.err_licence_deactivated);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_LICENSE_EXPIRED,
				R.string.err_licence_expired);

		mapCodes.put(
				KnoxEnterpriseLicenseManager.ERROR_LICENSE_QUANTITY_EXHAUSTED,

				R.string.err_licence_quantity_exhausted);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_LICENSE_TERMINATED,
				R.string.err_licence_terminated);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_NETWORK_DISCONNECTED,
				R.string.err_network_disconnected);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_NETWORK_GENERAL,
				R.string.err_network_general);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_NONE, R.string.err_none);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_NOT_CURRENT_DATE,
				R.string.err_unknown);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_NULL_PARAMS,
				R.string.err_not_current_date);

		mapCodes.put(KnoxEnterpriseLicenseManager.ERROR_UNKNOWN,
				R.string.err_null_params);

		mapCodes.put(
				KnoxEnterpriseLicenseManager.ERROR_USER_DISAGREES_LICENSE_AGREEMENT,

				R.string.err_user_disagrees_license_agreement);

	}

	// Get the message to be displayed to user by passing in code
	public static String getMessage(int code, Context context) {
		if (context.getString(mapCodes.get(code)) != null)
			return context.getString(mapCodes.get(code));
		else
			return "";
	}
}