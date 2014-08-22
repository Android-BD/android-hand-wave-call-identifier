package com.example.buttonwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
/**
 * Created with IntelliJ IDEA.
 * User: RAVI
 * Date: 28/6/14
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    Button button1,button2;
	String str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent start = new Intent(this, Serv.class);
        switch (view.getId()) {
            case R.id.button1:
                Toast.makeText(this, "start", Toast.LENGTH_LONG).show();
                this.startService(start);
                break;
            case R.id.button2:
                Toast.makeText(this, "stop", Toast.LENGTH_LONG).show();
                this.stopService(start);
                break;
        }

    }
}
