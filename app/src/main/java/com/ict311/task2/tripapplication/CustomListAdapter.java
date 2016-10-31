package com.ict311.task2.tripapplication;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;

    Trip tempValues=null;
    public TextView tripID;
    public TextView tripTitle;
    public TextView tripDate;
    public TextView tripType;
    public TextView tripDestination;
    public TextView tripDuration;
    public TextView tripComment;
    public ImageView tripPhoto;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CustomListAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;



    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/




    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, null);

        if(data.size()>0) {
            tripID = (TextView) convertView.findViewById(R.id.tripID);
            tripTitle = (TextView) convertView.findViewById(R.id.tripTitle);
            tripDate = (TextView) convertView.findViewById(R.id.tripDate);
            tripType = (TextView) convertView.findViewById(R.id.tripType);
            tripDestination = (TextView) convertView.findViewById(R.id.tripDestination);
            tripDuration = (TextView) convertView.findViewById(R.id.tripDuration);
            tripComment = (TextView) convertView.findViewById(R.id.tripComment);
            tripPhoto = (ImageView) convertView.findViewById(R.id.tripPhoto);

            /************  Set holder with LayoutInflater ************/

            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (Trip) data.get(position);

            /************  Set Model values in Holder elements ***********/
            tripID.setText(Long.toString(tempValues.getTripID()));
            tripTitle.setText(tempValues.getTripTitle());
            tripDate.setText(tempValues.getTripDate());
            tripType.setText(tempValues.getTripType() + " Trip");
            tripDestination.setText("Destination : "+tempValues.getTripDestination());

            tripDuration.setText("Duration : " +tempValues.getTripDuration());
            tripComment.setText("Comment : " +tempValues.getTripComment());
            byte[] outImage = tempValues.getTripPhoto();
            if (outImage != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                tripPhoto.setImageBitmap(theImage);

            }
        }
        return convertView;
    }


}