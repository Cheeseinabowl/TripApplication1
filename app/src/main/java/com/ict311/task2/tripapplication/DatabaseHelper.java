package com.ict311.task2.tripapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TripDetails";

    // Contacts table name


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Trip (TripID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TripTitle VARCHAR, TripDate VARCHAR, TripType VARCHAR, TripDestination VARCHAR, TripDuration VARCHAR, TripComment VARCHAR,  TripPhoto BLOB, TripLatitude double, TripLongitude double);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Setting(name VARCHAR, ID int, email VARCHAR, gender VARCHAR, comment VARCHAR )");

        int count = 0;

           try {
               String selectQuery = "SELECT  count(*) As rowCount FROM Setting ";


               Cursor cursor = db.rawQuery(selectQuery, null);
               // looping through all rows and adding to list
               if (cursor.moveToFirst()) {
                   count = cursor.getInt(0);
                   if (count == 0)
                       db.execSQL("INSERT INTO Setting values('Your name', 1,'Your email','male', 'Your Course information' )");

               }
           }
           catch (Exception e) {
               e.printStackTrace();

           }


}


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Trip");
        db.execSQL("DROP TABLE IF EXISTS Setting");

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read) Operations
     */
public void updateSetting(String name,int ID, String email,String gender,String comment )
{
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("name",name);
    values.put("ID", ID);
    values.put("email", email);
    values.put("gender", gender);
    values.put("comment", comment);
    db.update("Setting", values, null, null);



}
    public Setting getSetting( )
    {
        String selectQuery = "SELECT  * FROM Setting ";
        int count=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Setting s=new Setting();
            s.name=cursor.getString(0);
            s.ID=cursor.getInt(1);
            s.email=cursor.getString(2);
            s.gender=cursor.getString(3);
            s.comment=cursor.getString(4);

            return  s;
        }

        return null;

    }
    public void deleteTrip(int tripID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Trip","TripID=?",new String[] { String.valueOf(tripID) });
    }
    public// Adding new contact
    void addTrip(Trip trip) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("TripTitle", trip.getTripTitle());
            values.put("TripDate", trip.getTripDate());
            values.put("TripType", trip.getTripType());
            values.put("TripDestination", trip.getTripDestination());
            values.put("TripDuration", trip.getTripDuration());
            values.put("TripComment", trip.getTripComment());
            values.put("TripPhoto", trip.getTripPhoto());
            values.put("TripLatitude",trip.getTripLatitude());
                    values.put("TripLongitude",trip.getTripLongitude());

            // Inserting Row
            db.insert("Trip", null, values);

             // Closing database connection



        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    void updateTrip(int tripID,String title,String  date,String  destination, String duration,String  comment, String  spinner,byte[] photoImg)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TripTitle",title);
        values.put("TripDate", date);
        values.put("TripDestination", destination);
        values.put("TripDuration", duration);
        values.put("TripComment", comment);
        values.put("TripType", spinner);
        values.put("TripPhoto", photoImg);

        db.update("Trip", values, "TripID=?", new String[] { String.valueOf(tripID) });

    }

    // Getting single contact
    Trip getTrip(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Trip", new String[] {"TripID","TripTitle", "TripDate", "TripType", "TripDestination", "TripDuration", "TripComment", "TripPhoto" }, "TripID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trip trip = new Trip(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getBlob(7));


        return trip;

    }
    public double getTripLongitude(int tripID) {



        SQLiteDatabase db=this.getReadableDatabase();
        try {
            String selectQuery = "SELECT  TripLongitude FROM Trip where TripID="+tripID;



            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {


                return cursor.getDouble(0);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  0.0;
    }
    public double getTripLatitude(int tripID) {
        double lat = 0;

        SQLiteDatabase db=this.getReadableDatabase();
        try {
            String selectQuery = "SELECT  TripLatitude FROM Trip where TripID="+tripID;



            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {


                return cursor.getDouble(0);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  0.0;
    }
    public int getUserSettings() {
        int count = 0;

        SQLiteDatabase db=this.getReadableDatabase();
        try {
            String selectQuery = "SELECT  count(*) As rowCount FROM Setting ";



            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);

                return count;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  count;
    }
    // Getting All Contacts
    public ArrayList<Trip> getAllTrips() {
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Trip ORDER BY TripID";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip();
                trip.setTripID((cursor.getInt(0)));
                trip.setTripTitle(cursor.getString(1));
                trip.setTripDate(cursor.getString(2));
                trip.setTripType(cursor.getString(3));
                trip.setTripDestination(cursor.getString(4));
                trip.setTripDuration(cursor.getString(5));

                trip.setTripComment(cursor.getString(6));
                trip.setTripPhoto(cursor.getBlob(7));
                // Adding contact to list
                tripList.add(trip);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        // return contact list
        return tripList;
    }
}



