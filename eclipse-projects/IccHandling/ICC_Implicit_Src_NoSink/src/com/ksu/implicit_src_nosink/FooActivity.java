package com.ksu.implicit_src_nosink;

import com.ksu.implicit_src_nosink.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FooActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foo);
		Intent i = getIntent();
		String imei = i.getStringExtra("data");
		imei.trim();
	}
}
