package com.ksu.explicittargeticc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FooActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		String v = i.getStringExtra("data");
		v.trim();
	}
}
