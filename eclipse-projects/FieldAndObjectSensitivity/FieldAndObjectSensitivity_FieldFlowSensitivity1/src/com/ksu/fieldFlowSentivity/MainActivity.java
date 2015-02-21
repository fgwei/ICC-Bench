package com.ksu.fieldFlowSentivity;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

/**
 * @testcase_name FieldAndObjectSensitivity_FieldFlowSensitivity1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description Normal data reterived from Data object and log it. Then, reterive 
 * 				sensitive data from SensitiveData, and do some process.
 * @dataflow source -> _
 * @number_of_leaks 0
 * @challenges The analysis have to build interprocedural control flow graph in a flow sensitive way.
 */
public class MainActivity extends Activity {

	Data data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Data d = new Data();
		this.data = d;
		Log.d("id", this.data.retrieveData(this)); //sink, no leak
		Data sd = new SensitiveData();
		this.data = sd;
		process(this.data.retrieveData(this));
	}
	
	public void process(String data){
		// do something. no leak
	}
}
