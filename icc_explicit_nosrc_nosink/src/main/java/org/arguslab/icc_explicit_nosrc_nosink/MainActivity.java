package org.arguslab.icc_explicit_nosrc_nosink;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

/**
 * @testcase_name ICC_Explicit_NoSrc_NoSink
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description Insensitive v is sent to component FooActivity via explicit ICC.
 * 				In FooActivity, it will retrieve value v but not leak it.
 * @dataflow v -> MainActivity's intent -> FooActivity's intent -> v
 * @number_of_leaks 0
 * @challenges The analysis must be able to resolve explicit ICC calls and handle data flow
 * 				via different components.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String v = "notSrc";

        Intent i = new Intent(getApplicationContext(), FooActivity.class);

        i.putExtra("data", v);
        startActivity(i);
    }

}