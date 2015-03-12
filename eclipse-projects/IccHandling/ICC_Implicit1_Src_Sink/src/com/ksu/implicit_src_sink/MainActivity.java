package com.ksu.implicit_src_sink;

import com.ksu.implicit_src_sink.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;

/**
 * @testcase_name InterComponentCommunication_Implicit1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description The value v of a source is sent to component FooActivity via implicit ICC. 
 * 				In FooActivity, it will retrieve value v and leak it. 
 * @dataflow source -> imei -> MainActivity's intent -> sink (implicit ICC)
 * 			 source -> imei -> MainActivity's intent -> FooActivity's intent -> imei -> sink
 * @number_of_leaks 2
 * @challenges The analysis must be able to resolve implicit (Action) ICC calls and handle data flow 
 * 				via different components.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String imei = tel.getDeviceId();
		
		Intent i = new Intent("amandroid.impliciticctest_action.testaction");
		i.putExtra("data", imei);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
