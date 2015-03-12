package com.ksu.explicit_src_sink;

import com.ksu.explicit_src_sink.R;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.content.Intent;

/**
 * @testcase_name InterComponentCommunication_Explicit1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description The value v of a source is sent to component FooActivity via explicit ICC. 
 * 				In FooActivity, it will retrieve value v and leak it. 
 * @dataflow source -> imei -> MainActivity's intent -> FooActivity's intent -> imei -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to resolve explicit ICC calls and handle data flow 
 * 				via different components.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String imei = tel.getDeviceId();
		
		Intent i = new Intent(getApplicationContext(), FooActivity.class);
		
		i.putExtra("data", imei);
		startActivity(i);
	}

}
