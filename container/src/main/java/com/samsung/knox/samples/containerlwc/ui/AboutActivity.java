package com.samsung.knox.samples.containerlwc.ui;


import android.app.Activity;
import android.os.Bundle;

import com.samsung.knox.samples.containerlwc.R;


public class AboutActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getActionBar().setTitle(getResources().getString(R.string.about_app));
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

}
