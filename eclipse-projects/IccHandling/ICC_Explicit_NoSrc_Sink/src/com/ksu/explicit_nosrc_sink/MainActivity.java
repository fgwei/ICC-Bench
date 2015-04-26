package com.ksu.explicit_nosrc_sink;

import com.ksu.explicit_nosrc_sink.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

/**
 * @testcase_name ICC_Explicit_NoSrc_Sink
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description Insensitive value v is sent to component FooActivity via explicit ICC. 
 * 				In FooActivity, it will retrieve value v and leak it. 
 * @dataflow v -> MainActivity's intent -> FooActivity's intent -> v -> sink
 * @number_of_leaks 0
 * @challenges The analysis must be able to resolve explicit ICC calls and handle data flow 
 * 				via different components.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String v = "noSrc";
		
		Intent i = new Intent(getApplicationContext(), FooActivity.class);
		
		i.putExtra("data", v);
		startActivity(i);
	}

}
