package com.ksu.implicit5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HookActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hook);
		Intent i = getIntent();
		String imei = i.getStringExtra("data");
		Log.d("deviceid", imei);
	}
}
