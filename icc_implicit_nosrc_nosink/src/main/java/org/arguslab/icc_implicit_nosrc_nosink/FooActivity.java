package org.arguslab.icc_implicit_nosrc_nosink;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class FooActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foo);
        Intent i = getIntent();
        String v = i.getStringExtra("data");
        v.trim();
    }

}
