package com.ksu.passwordPassTest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @testcase_name AndroidSpecific_PrivateDataLeak3
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei@ksu.edu & sroy@ksu.edu
 * 
 * @description The password of a source is sent to component FooActivity via explicit ICC. 
 * 				In FooActivity, it will retrieve password and sent to component BarActivity via explicit ICC. 
 * 				In BarActivity, it will retrieve password and leak it
 * @dataflow source -> editText -> pw -> MainActivity's intent -> FooActivity's intent -> HookActivity's intent -> pw -> sink
 * @number_of_leaks 1
 * @challenges The analysis has to collect password edittext as source. 
 * 			   The analysis must be able to resolve explicit ICC calls and handle data flow 
 * 			   via different components.
 */
public class MainActivity extends Activity {

	EditText editText = null;
	Button passButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.password);
		passButton = (Button) findViewById(R.id.passButton);
		passButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Editable pw = editText.getText();
				Intent i = new Intent();
				i.setClass(getApplicationContext(), FooActivity.class);
				i.putExtra("password", pw.toString());
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
