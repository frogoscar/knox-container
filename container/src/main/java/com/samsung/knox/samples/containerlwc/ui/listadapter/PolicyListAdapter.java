package com.samsung.knox.samples.containerlwc.ui.listadapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.samsung.knox.samples.containerlwc.R;
import com.samsung.knox.samples.containerlwc.beans.ListItem;
import com.samsung.knox.samples.containerlwc.interfaces.ActivityLauncher;
import com.samsung.knox.samples.containerlwc.model.BaseModel;

//This class is the listview adapter for all lists used in this app

public class PolicyListAdapter extends ArrayAdapter<ListItem> {
	private Context mContext;
	View mRowView;
	int mViewTypeCount;
	public String TAG = "PolicyListAdapter";
	ListItem listItemObj;
	BaseModel mModelObj;
	ActivityLauncher activityLauncherRef;
	int mType;

	// view holder class to represent check box item row
	public class ViewHolderCheckBox {
		public CheckBox chkBox;

		// TextView txtVwIndex;

		public ViewHolderCheckBox(View mainView) {
			chkBox = (CheckBox) mainView.findViewById(R.id.checkBox1);
			// txtVwIndex = (TextView) mainView.findViewById(R.id.txtVwIndex);
			chkBox.setTextColor(Color.BLACK);
			// txtVwIndex.setTextColor(Color.BLACK);
		}
	}

	// view holder class to represent button item row
	class ViewHolderButton {
		Button button;

		// TextView txtVwIndex;

		public ViewHolderButton(View mainView) {
			button = (Button) mainView.findViewById(R.id.btn1);
			// txtVwIndex = (TextView) mainView.findViewById(R.id.txtVwIndex);
			// txtVwIndex.setTextColor(Color.BLACK);
		}
	}

	// view holder class to represent textview item row
	class ViewHolderPlain {
		TextView txtView;

		// TextView txtVwIndex;

		public ViewHolderPlain(View mainView) {
			txtView = (TextView) mainView.findViewById(R.id.textView1);
			// txtVwIndex = (TextView) mainView.findViewById(R.id.txtVwIndex);
			txtView.setTextColor(Color.BLACK);
			// txtVwIndex.setTextColor(Color.BLACK);
		}
	}

	// view holder class to represent header textview item row
	class ViewHolderHdr {
		TextView txtView;

		// TextView txtVwIndex;

		public ViewHolderHdr(View mainView) {
			txtView = (TextView) mainView.findViewById(R.id.textView1);
			// txtVwIndex = (TextView) mainView.findViewById(R.id.txtVwIndex);
			//txtView.setTextColor(Color.WHITE);
			// txtVwIndex.setTextColor(Color.WHITE);
		}
	}

	public PolicyListAdapter(Context context, List<ListItem> values,
			int viewTypeCount,int type) {

		super(context, R.layout.list_cell, values);
		this.mContext = context;
		this.mViewTypeCount = viewTypeCount;
		this.mModelObj = new BaseModel(context);
		this.mType = type;
	}
	
	public PolicyListAdapter(Context context, List<ListItem> values,
			int viewTypeCount) {

		super(context, R.layout.list_cell, values);
		this.mContext = context;
		this.mViewTypeCount = viewTypeCount;
		this.mModelObj = new BaseModel(context);
	}

	@Override
	public int getViewTypeCount() {
		return mViewTypeCount;
	}

	@Override
	public int getItemViewType(int position) {
		ListItem listItemObj = (ListItem) getItem(position);
		switch (listItemObj.apiType) {
		case SETTER:
			return 0;
		case UI_HEADER:
			return 1;
		case UI_BUTTON:
			return 2;
		default:
			return 3;
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mRowView = null;
		ViewHolderPlain viewHolder = null;
		ViewHolderCheckBox viewHolderCb = null;
		ViewHolderHdr viewHolderHdr = null;
		ViewHolderButton viewHolderBtn = null;

		listItemObj = (ListItem) getItem(position);

		if (convertView == null) {
			switch (listItemObj.apiType) {
			// if check box needs to be displayed
			case SETTER:
				mRowView = inflater.inflate(R.layout.list_cell, parent, false);
				mRowView.setTag(new ViewHolderCheckBox(mRowView));
				break;

			// if this item is a header
			case UI_HEADER:
				mRowView = inflater.inflate(R.layout.list_cell_header, parent,
						false);
				mRowView.setTag(new ViewHolderHdr(mRowView));
				break;

			// if this item contains a button
			case UI_BUTTON:
				System.out.println("setting tag button " + mRowView);
				mRowView = inflater.inflate(R.layout.list_cell_button, parent,
						false);
				mRowView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
				mRowView.setTag(new ViewHolderButton(mRowView));

				break;

			// all other list items
			default:
				mRowView = inflater.inflate(R.layout.list_cell_plain, parent,
						false);
				mRowView.setTag(new ViewHolderPlain(mRowView));
			}

		} else {
			mRowView = convertView;
			mRowView.setEnabled(true);
		}

		// if item is not a header, make the background of the row white
		//if (listItemObj.apiType != APIType.UI_HEADER)
			mRowView.setBackgroundColor(mContext.getResources().getColor(R.color.screen_background));

		// checking if this API is supported. If not supported, disable this
		// item row and color it gray
		if (!BaseModel.isAPISupported(listItemObj)) {
			mRowView.setEnabled(false);
			
			switch (listItemObj.apiType) {

			// if it is a check box
			case SETTER:
				viewHolderCb = (ViewHolderCheckBox) mRowView.getTag();
				viewHolderCb.chkBox.setTextColor(mContext.getResources().getColor(
				R.color.button_text_disabled));
				break;
				
			case UI_BUTTON:
				viewHolderBtn = (ViewHolderButton) mRowView.getTag();
				viewHolderBtn.button.setTextColor(mContext.getResources().getColor(
						R.color.button_text_disabled));
				break;
				
			default:
				viewHolder = (ViewHolderPlain) mRowView.getTag();
				viewHolder.txtView.setTextColor(mContext.getResources().getColor(
						R.color.button_text_disabled));
			}
		}

		switch (listItemObj.apiType) {

		// if it is a check box
		case SETTER:
			viewHolderCb = (ViewHolderCheckBox) mRowView.getTag();
			viewHolderCb.chkBox.setText(listItemObj.name);
			// viewHolderCb.txtVwIndex.setText("" + position);
			if (!mRowView.isEnabled()) {
				//viewHolderCb.chkBox.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_disabled));
				viewHolderCb.chkBox.setClickable(false);
				viewHolderCb.chkBox.setEnabled(false);
			}

			/* persisting state of check box */
			else {
				viewHolderCb.chkBox.setEnabled(true);
				viewHolderCb.chkBox.setClickable(true);
				//viewHolderCb.chkBox.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_enabled));
				final CheckBox cBox = viewHolderCb.chkBox;
				listItemObj = (ListItem) getItem(position);
				if (mRowView.isEnabled()) {
					cBox.setChecked(mModelObj.fetchState(listItemObj,mType));
				}

				// if check box is clicked
				viewHolderCb.chkBox.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.i(TAG, "checkbox on click");
						listItemObj = (ListItem) getItem(position);
						// call this method to route the call to respective
						// Model class where this KNOX API call is implemented
						mModelObj.routePolicy(listItemObj, cBox.isChecked(),mType);
					}
				});
			}
			break;

		// if a header
		case UI_HEADER:
			viewHolderHdr = (ViewHolderHdr) mRowView.getTag();
			viewHolderHdr.txtView.setText(listItemObj.name);
			// viewHolderHdr.txtVwIndex.setText("" + position);
			break;

		// if a button item row
		case UI_BUTTON:
			System.out.println("getting tag button " + mRowView);
			viewHolderBtn = (ViewHolderButton) mRowView.getTag();
			viewHolderBtn.button.setText(listItemObj.name);
			// viewHolderBtn.txtVwIndex.setText("" + position);

			// if button is clicked
			viewHolderBtn.button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					// launch another activity or finish the current activity
					activityLauncherRef = new ActivityLauncher() {

						@Override
						public void launchActivity() {

						}

						@Override
						public void finishCurrentActivity() {
							// TODO Auto-generated method stub
							Activity activity = ((Activity) mContext);
							activity.finish();
						}
					};

					listItemObj = (ListItem) getItem(position);

					if (mRowView.isEnabled()) {
						// call this method to route the call to respective
						// Model class where this KNOX API call is implemented
						mModelObj.routePolicy(listItemObj, null,
								activityLauncherRef,mType);
					}
				}
			});

			break;

		// for all other item rows
		default:
			viewHolder = (ViewHolderPlain) mRowView.getTag();
			viewHolder.txtView.setText(listItemObj.name);
			// viewHolder.txtVwIndex.setText("" + position);
			break;
		}

		return mRowView;
	}
}
