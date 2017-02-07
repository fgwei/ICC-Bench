package org.arguslab.icc_implicit_mix1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class FooActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foo);
        Intent i = new Intent();
        i.setAction("test_action2");
        i.addCategory("test_category3");
        i.addCategory("test_category4");
        Uri uri = Uri.parse("amandroid://fgwei:8888/xxx/xxx.com");
        i.setDataAndType(uri, "test/type");
        i.putExtra("data", getIntent().getStringExtra("data"));
        startActivity(i); // sink
    }

}
