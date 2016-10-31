package com.ict311.task2.tripapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class LogActivity extends FragmentActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final String TAG = "LogActivity";
     FragmentTransaction ft=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new LogFragment(), TAG);
            ft.commit();
        }


    }
    /**
     * On activity result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         Fragment fragment=getSupportFragmentManager().findFragmentByTag(TAG);
         fragment.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onBackPressed() {
        // Write your code here
        Intent intent = new Intent(LogActivity.this, TripInformationActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}