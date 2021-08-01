package com.example.destination.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Booking {
    public String uid;
    public String author;
    public String text;
    public  String Tname;
    public String contact;
    public String date;
    public String persons;

    public Booking(String uid, String text, String tname, String contact, String date, String persons) {
        this.uid = uid;
      //  this.author = author;
        this.text = text;
        this.Tname = tname;
        this.contact = contact;
        this.date = date;
        this.persons = persons;
    }


    public Booking() {
    }

   // public Booking(String uid, String authorName, String commentText, String tname, int tno, String tdate, String tcontact) {
        // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
    //}


}