package com.ksu.passwordPassTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BarActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar);
		String pw = getIntent().getStringExtra("NewKey");
		TextView tv = (TextView) findViewById(R.id.passwordView);
		tv.setText(pw);
		Log.d("password", pw.toString());
	}
}
