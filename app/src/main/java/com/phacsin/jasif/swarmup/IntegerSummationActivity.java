package com.phacsin.jasif.swarmup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntegerSummationActivity extends AppCompatActivity {
    Button look;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integer_summation);
        look = (Button) findViewById(R.id.btn_look);
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ConnectedDeviceActivity.class));
            }
        });
    }
}
