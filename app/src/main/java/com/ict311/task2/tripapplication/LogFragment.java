package com.ict311.task2.tripapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class LogFragment  extends Fragment {
    EditText titleEditText;
    EditText dateEditText;
    Spinner spinner;
    EditText destinationEditText;
    EditText durationEditText;
    EditText commentEditText;
    ImageView photoImg;
    private static final int CAMERA_REQUEST = 1888;
    DatabaseHelper db;
    byte imageInByte[];
    TextView currentLoc;
    double latitude=0;
    double longitude=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_log, container, false);

        db = new DatabaseHelper(getActivity());
        final Calendar myCalendar = Calendar.getInstance();
        titleEditText = (EditText) v.findViewById(R.id.title);
        dateEditText = (EditText) v.findViewById(R.id.date);
        destinationEditText = (EditText) v.findViewById(R.id.destination);
        durationEditText = (EditText) v.findViewById(R.id.duration);
        commentEditText = (EditText) v.findViewById(R.id.comment);
        spinner = (Spinner) v.findViewById(R.id.type);
        photoImg =(ImageView) v.findViewById(R.id.photo);
        currentLoc=(TextView) v.findViewById(R.id.gpsLocation);
        GPSTracker gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
             longitude = gps.getLongitude();
            currentLoc.setText("Current GPS Location: Latitude : "+latitude+ " Longitude : "+longitude);
            // \n is for new line

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateEditText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.tripType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
Button takePhoto=(Button) v.findViewById(R.id.photoBtn);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCamera();
            }
        });
        Button submit=(Button) v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addTrip(new Trip(titleEditText.getText().toString(), dateEditText.getText().toString(), spinner.getSelectedItem().toString(), destinationEditText.getText().toString(), durationEditText.getText().toString(), commentEditText.getText().toString(), imageInByte,latitude,longitude));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(" Submit Results ");
                builder.setMessage("Trip information is saved successfully")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                titleEditText.setText("");
                                dateEditText .setText("");
                                destinationEditText .setText("");
                                durationEditText.setText("");
                                commentEditText.setText("");
                                spinner.setSelection(0);
                                photoImg.setImageBitmap(null);
                            }
                        });
                AlertDialog alert = builder.create();
               alert.show();


            }
        });
        Button cancel=(Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                 startActivity(intent);

            }
        });

        return v;
    }
    public void callCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, CAMERA_REQUEST);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // Do something with imagePath
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageInByte= stream.toByteArray();
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
                    Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                    photoImg.setImageBitmap(theImage);
                    // Bitmap photo = (Bitmap) data.getExtras().get("data");
                    //photoImg.setImageBitmap(photo);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                }
            }
        }
    }

}