package com.android.lab6exercise2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private MyService demoBindService;
    private boolean isBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalService localService = (MyService.LocalService) service;
            demoBindService = localService.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    public void store(View view) {

        Toast.makeText(MainActivity.this, "Start Service", Toast.LENGTH_SHORT).show();


        synchronized (this) {
            try {
                wait(1000);

                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("my name", "my password");
                editor.apply();

            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(MainActivity.this, "End Service", Toast.LENGTH_SHORT).show();
    }

    public void display(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String pass = sharedPreferences.getString("my name", "");
        TextView sName = (TextView) findViewById(R.id.textView2);
        TextView sPass = (TextView) findViewById(R.id.textView3);
        sName.setText("my name");
        sPass.setText(pass);
    }


    @Override
    protected void onDestroy(){
        if(isBound){
            unbindService(serviceConnection);
            isBound = false;
        }

        super.onDestroy();
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
}
