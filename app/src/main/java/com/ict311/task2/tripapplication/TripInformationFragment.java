package com.ict311.task2.tripapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TripInformationFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    DatabaseHelper db;
    EditText titleEditText;
    EditText dateEditText;
    Spinner spinner;
    EditText destinationEditText;
    EditText durationEditText;
    EditText commentEditText;
    ImageView photoImg;
    int tripID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tripID = Integer.parseInt(this.getArguments().getString("tripID"));
        final View v = inflater.inflate(R.layout.activity_trip_information, container, false);
        db = new DatabaseHelper(getActivity());
        Trip t = db.getTrip(tripID);
        titleEditText = (EditText) v.findViewById(R.id.title);
        dateEditText = (EditText) v.findViewById(R.id.date);
        destinationEditText = (EditText) v.findViewById(R.id.destination);
        durationEditText = (EditText) v.findViewById(R.id.duration);
        commentEditText = (EditText) v.findViewById(R.id.comment);
        spinner = (Spinner) v.findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.tripType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        photoImg = (ImageView) v.findViewById(R.id.photo);
        titleEditText.setText(t.getTripTitle());
        dateEditText.setText(t.getTripDate());
        destinationEditText.setText(t.getTripDestination());
        durationEditText.setText(t.getTripDuration());
        commentEditText.setText(t.getTripComment());
        if (t.getTripType().equals("Work"))
            spinner.setSelection(0);
        else if (t.getTripType().equals("Personal"))
            spinner.setSelection(1);

        else
            spinner.setSelection(2);
        if (t.getTripPhoto() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(t.getTripPhoto());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            photoImg.setImageBitmap(theImage);
        }
        Button delete = (Button) v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTrip(tripID);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button location = (Button) v.findViewById(R.id.gpsLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("tripID", String.valueOf(tripID));
                startActivity(intent);
            }
        });
        Button takePhoto = (Button) v.findViewById(R.id.photoBtn);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCamera();
            }
        });

        return v;
    }

    public void callCamera() {
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
                    byte[] imageInByte = stream.toByteArray();
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
