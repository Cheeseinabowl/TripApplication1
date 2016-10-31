package com.ict311.task2.tripapplication;

public class Trip {
    private int TripID = 0;
    private String TripTitle = "";
    private String TripDate = "";
    private String TripType = "";
    private String TripDestination = "";
    private String TripDuration = "";
    private String TripComment = "";
    private byte[] TripPhoto;
    private double tripLongitude;
    private double tripLatitude;

    Trip(int TripID, String TripTitle, String TripDate, String TripType, String TripDestination, String TripDuration, String TripComment, byte[] TripPhoto) {
        this.TripID = TripID;
        this.TripTitle = TripTitle;
        this.TripDate = TripDate;
        this.TripType = TripType;
        this.TripDestination = TripDestination;
        this.TripDuration = TripDuration;
        this.TripComment = TripComment;
        this.TripPhoto = TripPhoto;
    }

    Trip(String TripTitle, String TripDate, String TripType, String TripDestination, String TripDuration, String TripComment, byte[] TripPhoto) {

        this.TripTitle = TripTitle;
        this.TripDate = TripDate;
        this.TripType = TripType;
        this.TripDestination = TripDestination;
        this.TripDuration = TripDuration;
        this.TripComment = TripComment;
        this.TripPhoto = TripPhoto;
    }

    public Trip() {

    }

    public Trip(String TripTitle, String TripDate, String TripType, String TripDestination, String TripDuration, String TripComment, byte[] TripPhoto, double latitude, double longitude) {
        this.TripTitle = TripTitle;
        this.TripDate = TripDate;
        this.TripType = TripType;
        this.TripDestination = TripDestination;
        this.TripDuration = TripDuration;
        this.TripComment = TripComment;
        this.TripPhoto = TripPhoto;
        this.tripLatitude = latitude;
        this.tripLongitude = longitude;
    }

    /***********
     * Set Methods
     ******************/
    public void setTripID(int TripID) {
        this.TripID = TripID;
    }

    public void setTripTitle(String TripTitle) {
        this.TripTitle = TripTitle;
    }

    public void setTripDate(String TripDate) {
        this.TripDate = TripDate;
    }

    public void setTripType(String TripType) {
        this.TripType = TripType;
    }

    public void setTripDestination(String TripDestination) {
        this.TripDestination = TripDestination;
    }

    public void setTripDuration(String TripDuration) {
        this.TripDuration = TripDuration;
    }

    public void setTripComment(String TripComment) {
        this.TripComment = TripComment;
    }

    public void setTripPhoto(byte[] TripPhoto) {
        this.TripPhoto = TripPhoto;
    }

    public void setTripLatitude(double tripLatitude) {
        this.tripLatitude = tripLatitude;
    }

    public void setTripLongitude(double tripLongitude) {
        this.tripLongitude = tripLongitude;
    }

    /***********
     * Get Methods
     ****************/
    public int getTripID() {
        return this.TripID;
    }


    public String getTripTitle() {
        return this.TripTitle;
    }

    public String getTripDate() {
        return this.TripDate;
    }

    public String getTripType() {
        return this.TripType;
    }

    public String getTripDestination() {
        return this.TripDestination;
    }

    public String getTripComment() {
        return this.TripComment;
    }

    public String getTripDuration() {
        return this.TripDuration;
    }

    public byte[] getTripPhoto() {
        return this.TripPhoto;
    }

    public double getTripLatitude() {
        return tripLatitude;
    }

    public double getTripLongitude() {
        return tripLongitude;
    }
}
