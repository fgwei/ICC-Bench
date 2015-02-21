package com.ksu.impliciticctest_action;

import com.ksu.impliciticctest_action.R;
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
		String v = i.getStringExtra("data");
		Log.d("deviceid", v);
	}
}
