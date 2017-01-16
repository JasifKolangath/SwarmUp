package com.phacsin.jasif.swarmup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RadioButton delegate,worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView integerCard = (CardView) findViewById(R.id.cardViewInteger);
        CardView mandelCard = (CardView) findViewById(R.id.cardViewMandel);
        CardView faceCard = (CardView) findViewById(R.id.cardViewFace);
        delegate = (RadioButton) findViewById(R.id.delegate_radio);
        worker = (RadioButton) findViewById(R.id.worker_radio);

        integerCard.setOnClickListener(this);
        mandelCard.setOnClickListener(this);
        faceCard.setOnClickListener(this);

        delegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delegate.isChecked())
                    worker.setChecked(false);
                else
                    worker.setChecked(true);
            }
        });

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(worker.isChecked())
                    delegate.setChecked(false);
                else
                    delegate.setChecked(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(!delegate.isChecked() && !worker.isChecked())
            Toast.makeText(getApplicationContext(),"Select Role",Toast.LENGTH_LONG).show();
        switch (v.getId())
        {
            case R.id.cardViewInteger:
                if(delegate.isChecked())
                    startActivity(new Intent(getApplicationContext(),IntegerSummationActivity.class));

                break;
            case R.id.cardViewMandel:
                if(delegate.isChecked())
                    startActivity(new Intent(getApplicationContext(),MandelbrotActivity.class));

                break;
            case R.id.cardViewFace:
                if(delegate.isChecked())
                    startActivity(new Intent(getApplicationContext(),IntegerSummationActivity.class));
                break;
        }
    }
}
