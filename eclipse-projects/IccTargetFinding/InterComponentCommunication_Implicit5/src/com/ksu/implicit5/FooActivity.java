package com.ksu.implicit5;

import com.ksu.implicit5.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class FooActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foo);
		Intent i = new Intent();
		i.setAction("test_action2");
		i.addCategory("test_category3");
		i.addCategory("test_category4");
		Uri uri = Uri.parse("amandroid://ksu:8888/xxx/xxx.com");
		i.setDataAndType(uri, "test/type");
		i.putExtra("data", getIntent().getStringExtra("data"));
		startActivity(i);
	}
}
