package com.ict311.task2.tripapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Spinner;

import java.io.ByteArrayOutputStream;


public class TripInformationActivity extends FragmentActivity {
    private static final String TAG = "TripInformationActivity";
    FragmentTransaction ft=null;
    int TripID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         TripID = Integer.parseInt(intent.getStringExtra("tripID"));


        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            Bundle bundle = new Bundle();
              bundle.putString("tripID", String.valueOf(TripID) );
            ft = getSupportFragmentManager().beginTransaction();
            TripInformationFragment tif= new TripInformationFragment();
            tif.setArguments(bundle);
            ft.add(android.R.id.content, tif, TAG);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment fm = getSupportFragmentManager().findFragmentByTag(TAG);

        if(fm!=null)
            if(fm.isVisible()) {
                View v = fm.getView();
                EditText titleEditText = (EditText) v.findViewById(R.id.title);
                EditText dateEditText = (EditText) v.findViewById(R.id.date);
                EditText destinationEditText = (EditText) v.findViewById(R.id.destination);
                EditText durationEditText = (EditText) v.findViewById(R.id.duration);
                EditText commentEditText = (EditText) v.findViewById(R.id.comment);
                Spinner spinner = (Spinner) v.findViewById(R.id.type);
               ImageView photoImg =(ImageView) v.findViewById(R.id.photo);
                DatabaseHelper db = new DatabaseHelper(this);
                photoImg.setBackgroundResource(0);
                photoImg.setDrawingCacheEnabled(true);

                photoImg.buildDrawingCache();

                Bitmap bm = photoImg.getDrawingCache();


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                photoImg.setBackgroundResource(R.drawable.roundborder);
                db.updateTrip(TripID, titleEditText.getText().toString(), dateEditText.getText().toString(),
                        destinationEditText.getText().toString(), durationEditText.getText().toString(), commentEditText.getText().toString(),
                        spinner.getSelectedItem().toString(), byteArray);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment fragment=getSupportFragmentManager().findFragmentByTag(TAG);
        fragment.onActivityResult(requestCode, resultCode, data);

    }
}
