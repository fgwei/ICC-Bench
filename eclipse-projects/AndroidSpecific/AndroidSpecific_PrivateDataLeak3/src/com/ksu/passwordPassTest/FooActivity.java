package com.ksu.passwordPassTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FooActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foo);
		Intent i = getIntent();
		String pw = i.getStringExtra("password");
		Intent newI = new Intent();
		newI.setClass(getApplicationContext(), BarActivity.class);
		newI.putExtra("NewKey", pw);
		startActivity(newI);
	}
}
