package com.ksu.impliciticctest_Categories;

import com.ksu.impliciticctest_Categories.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FooActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foo);
		Intent i = getIntent();
		String imei = i.getStringExtra("data");
		Log.d("deviceid", imei);
	}
}
