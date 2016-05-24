package com.samsung.knox.samples.containerlwc.utils;

import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.samsung.knox.samples.containerlwc.R;
import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.constants.SAConstants;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.samsung.knox.samples.containerlwc.interfaces.KNOXApiInvoker;
import com.samsung.knox.samples.containerlwc.model.BaseModel;


//This class is used to display Toast messages, alert dialogs to user

public class SAUIHelper {

	public static final String TAG = SAUIHelper.class.getSimpleName();
	static String value = "";
	static KNOXApiInvoker knoxApiInvokerRef;
	static SharedPreferences sharedpreferences;
	static Editor editor;

	private SAUIHelper() {
		throw new AssertionError("Cannot create object of SAUIHelper");
	}

	// Shows an alert dialog to ask a query to user
	public static void showAlertToAskQuery(Context ctxt, String heading,
			String message, ListItem listItem, BaseModel modelObj,
			ActivityLauncher activityLauncher,int type) {

		AlertDialog.Builder alertDialogBuilder = null;
		final BaseModel modelRef = modelObj;
		final ListItem listItemRef = listItem;
		final ActivityLauncher activityLauncherRef = activityLauncher;
		final Context ctxtRef = ctxt;
		final int mType = type;

		if (ctxt != null) {
			alertDialogBuilder = new AlertDialog.Builder(ctxt);
			alertDialogBuilder.setTitle(heading);
			alertDialogBuilder.setMessage(message);

			alertDialogBuilder.setPositiveButton(ctxt.getString(R.string.yes),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Invoke this method to route the call to
							// respective Model class, which has
							// implementation of this KNOX API

							modelRef.routePolicy(listItemRef,
									ctxtRef.getString(R.string.yes),
									activityLauncherRef,mType);

						}
					});

			alertDialogBuilder.setNegativeButton(ctxt.getString(R.string.no),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Invoke this method to route the call to
							// respective Model class, which has
							// implementation of this KNOX API
							modelRef.routePolicy(listItemRef,
									ctxtRef.getString(R.string.no),
									activityLauncherRef,mType);
						}
					});

			alertDialogBuilder.show();

		}

	}

	// show an alert dialog
	public static void showAlert(Context ctxt, String heading, String message,
			String okButtonText) {
		if (ctxt != null)
			new AlertDialog.Builder(ctxt).setTitle(heading).setMessage(message)
					.setNegativeButton(okButtonText, null).show();
	}

	// show no internet connection alert dialog
	public static void showNoInternetConnectionAlert(Context ctxt) {
		if (ctxt != null)
			showAlert(ctxt, null,
					ctxt.getString(R.string.no_internet_detail_message),
					ctxt.getString(R.string.ok));
	}

	// show a Toast message
	public static void showToast(Context ctxt, String message) {
		if (ctxt != null)
			Toast.makeText(ctxt, message, Toast.LENGTH_LONG).show();
	}

	// show an alert dialog with a numeric keyboard EditText
	public static String showAlertWithNumericEditText(Context ctxt,
			String heading, String message, String editTextDefVal,
			ListItem api, BaseModel modelObj, ActivityLauncher activityLauncher,int type) {

		final BaseModel modelRef = modelObj;
		final ListItem apiRef = api;
		final ActivityLauncher activityLauncherRef = activityLauncher;
		AlertDialog.Builder alert = new AlertDialog.Builder(ctxt);
		final int mType = type;
		
		alert.setTitle(heading);
		alert.setMessage(message);

		// Set an EditText view to get user input
		final EditText input = new EditText(ctxt);
		input.setText(editTextDefVal);
		// Display a numeric keyboard
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);
		final Context context = ctxt;
		final String headingMsg = heading;
		final String bodyMsg = message;

		alert.setPositiveButton(SAConstants.OK,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						value = input.getText().toString();
						if (value != null) {
							if (value.equalsIgnoreCase("")) {
								SAUIHelper.showAlert(context, headingMsg,
										bodyMsg, SAConstants.OK);
							} else {
								// Invoke this method to route the call to
								// respective Model class, which has
								// implementation of this KNOX API
								modelRef.routePolicy(apiRef, value,
										activityLauncherRef,mType);
							}
						}

					}
				});

		alert.setNegativeButton(SAConstants.CANCEL,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						value = null;
					}
				});

		alert.show();
		return value;
	}

	// show an alert dialog with an EditText
	public static String showAlertWithEditText(Context ctxt, String heading,
			String message, String editTextDefVal, ListItem api,
			BaseModel modelObj, ActivityLauncher activityLauncher,int type) {
		final BaseModel modelRef = modelObj;
		final ListItem apiRef = api;
		final ActivityLauncher activityLauncherRef = activityLauncher;
		AlertDialog.Builder alert = new AlertDialog.Builder(ctxt);
		final int mType = type;
		
		alert.setTitle(heading);
		alert.setMessage(message);

		// Set an EditText view to get user input
		final EditText input = new EditText(ctxt);
		input.setText(editTextDefVal);
		alert.setView(input);
		final Context context = ctxt;
		final String headingMsg = heading;
		final String bodyMsg = message;

		alert.setPositiveButton(SAConstants.OK,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						value = input.getText().toString();
						if (value != null) {
							if (value.equalsIgnoreCase("")) {
								SAUIHelper.showAlert(context, headingMsg,
										bodyMsg, SAConstants.OK);
							} else {
								// Invoke this method to route the call to
								// respective Model class, which has
								// implementation of this KNOX API
								modelRef.routePolicy(apiRef, value,
										activityLauncherRef,mType);
							}

						}

					}
				});

		alert.setNegativeButton(SAConstants.CANCEL,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						value = null;
					}
				});

		alert.show();
		return value;
	}

	// show an alert dialog with list
	public static void showAlertWithList(Context ctxt, String heading,
			String[] list, String key) {

		final Context ctxtRef = ctxt;
		final String keyObj = key;
		final String[] listStr = list;
		sharedpreferences = ctxt.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctxt);
		builderSingle.setTitle(heading);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				ctxt, android.R.layout.simple_list_item_1);

		// add the items in the list
		for (String str : list) {
			arrayAdapter.add(str);
		}

		if (arrayAdapter != null && !(arrayAdapter.isEmpty())) {

			builderSingle.setNegativeButton(SAConstants.CANCEL,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							if (listStr != null && listStr.length > 0) {
								// save the first item of the list in Shared
								// preference
								editor.putString(keyObj, listStr[0]);
							} else {
								editor.putString(keyObj, null);
							}
							editor.commit();
							dialog.dismiss();
						}
					});

			builderSingle.setAdapter(arrayAdapter,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String strObj = arrayAdapter.getItem(which);
							// save the selected item of the list in shared
							// preference
							editor.putString(keyObj, strObj);
							editor.commit();
							dialog.dismiss();
							showToast(ctxtRef,
									sharedpreferences.getString(keyObj, null)
											+ " is selected");
						}
					});

			builderSingle.show();
		}
		// if there are no items in the list
		else {
			editor.putString(keyObj, null);
			showToast(ctxt, SAConstants.NO_ITEMS_TO_SHOW);
		}
	}
	
	// show an alert dialog with list, where multiple items can be selected
		public static void showAlertWithMultiChoiceList(Context ctxt, String heading,
				String[] list, String key) {

			final Set<String> selectedItems = new HashSet<String>();
			
			final Context ctxtRef = ctxt;
			final String keyObj = key;
			final String[] listStr = list;
			sharedpreferences = ctxt.getSharedPreferences(
					SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
			editor = sharedpreferences.edit();
			AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctxt);
			builderSingle.setTitle(heading);

			if (list != null && (list.length > 0)) {
				
				builderSingle.setPositiveButton(SAConstants.OK, new DialogInterface.OnClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int id) {
		                   // User clicked OK, so save the mSelectedItems results somewhere
		                   // or return them to the component that opened the dialog
		            	   editor.putStringSet(keyObj, selectedItems);
		            	   editor.commit();
		               }
		           });
				
				builderSingle.setNegativeButton(SAConstants.CANCEL,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								editor.putStringSet(keyObj, null);
				            	editor.commit();
								dialog.dismiss();
							}
						});

				builderSingle.setMultiChoiceItems(list, null,
	                      new DialogInterface.OnMultiChoiceClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int which,
		                       boolean isChecked) {
		                   if (isChecked) {
		                       // If the user checked the item, add it to the selected items
		                	   selectedItems.add(listStr[which]);
		                   } else if (selectedItems.contains(which)) {
		                       // Else, if the item is already in the array, remove it 
		                	   selectedItems.remove(which);
		                   }
		               }
		           });

				builderSingle.show();
			}
			// if there are no items in the list
			else {
				editor.putString(keyObj, null);
				showToast(ctxt, SAConstants.NO_ITEMS_TO_SHOW);
			}
		}

}