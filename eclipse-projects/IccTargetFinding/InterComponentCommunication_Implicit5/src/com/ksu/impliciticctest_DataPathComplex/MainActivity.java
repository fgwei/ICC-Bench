package com.ksu.impliciticctest_DataPathComplex;

import com.ksu.impliciticctest_DataPathComplex.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;

/**
 * @testcase_name InterComponentCommunication_Implicit5
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description The value v of a source is sent to component FooActivity via implicit ICC. 
 * 				In FooActivity, it will retrieve value v and sent to component HookActivity via implicit ICC. 
 * 				In HookActivity, it will retrieve value v and leak it
 * @dataflow source -> imei -> MainActivity's intent -> sink (implicit ICC);
 * 			 source -> imei -> MainActivity's intent -> FooActivity's intent -> sink (implicit ICC);
 * 			 source -> imei -> MainActivity's intent -> FooActivity's intent -> HookActivity's intent -> sink
 * @number_of_leaks 3
 * @challenges The analysis must be able to resolve implicit (Data&MType) ICC calls and handle data flow 
 * 				via different components.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String imei = tel.getDeviceId();
		
		Intent i = new Intent();
		i.setAction("test_action");
		i.addCategory("test_category1");
		i.addCategory("test_category2");
		Uri uri = Uri.parse("amandroid://ksu:8888/abc/def");
		i.setDataAndType(uri, "test/type");
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
