package com.ksu.dynamicregister2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;

/**
 * @testcase_name InterComponentCommunication_DynRegister1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description MainActivity registered MyReciver. The value v of a source is sent to component
 * 				MyReciver via implicit ICC. In MyReciver, it will retrieve value v and leak it. 
 * @dataflow source -> imei -> MainActivity's intent -> sink (implicit ICC)
 * 			 source -> imei -> MainActivity's intent -> MyReciver's intent -> imei -> sink
 * @number_of_leaks 2
 * @challenges The analysis must be able to handle string manipulation, resolve dynamically 
 * 				registered component, implicit ICC calls and handle data flow via different 
 * 				components.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		StringBuilder sb = new StringBuilder();
		sb.append("com.");
		sb.append("ksu");
		registerReceiver(new MyReceiver(), new IntentFilter(sb.toString()));
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String id = tm.getDeviceId(); //source
		Intent i = new Intent("com.ksu");
		i.putExtra("id", id);
		sendBroadcast(i); //leak
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
