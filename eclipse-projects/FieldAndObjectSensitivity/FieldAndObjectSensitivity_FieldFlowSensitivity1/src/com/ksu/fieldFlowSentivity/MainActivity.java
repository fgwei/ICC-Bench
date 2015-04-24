package com.ksu.fieldFlowSentivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @testcase_name FieldAndObjectSensitivity_FieldFlowSensitivity1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description Normal data reterived from Data object and log it (in FooActivity). Then, reterive 
 * 				sensitive data from Data, and do some process (no leak, in FooActivity).
 * @dataflow source -> _
 * @number_of_leaks 0
 * @challenges The analysis have to build intercomponent control flow graph in a flow sensitive way.
 */
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Data d = new Data();
		d.setData1("data");
		TelephonyManager tel = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tel.getDeviceId(); //source
		d.setData2(imei);
		Intent i = new Intent(getApplicationContext(), FooActivity.class);
		i.putExtra("data", d);
		startActivity(i);
	}
	
	
}
