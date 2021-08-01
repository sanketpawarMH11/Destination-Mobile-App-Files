package com.example.destination.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Bus {
    public String rate;
    public  String vehicalType;
    public  String company;
    public String busNo;
    public String seats;
    public String route;
    public String time;

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Bus() {
        // Default constructor required for calls to DataSnapshot.getValue(Bus.class)
    }

    public Bus(String uid, String author, String title, String body,
               String vehicalType,String rate,  String company, String busNo, String seats, String route, String time) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.vehicalType = vehicalType;
        this.rate = rate;
        this.company = company;
        this.busNo = busNo;
        this.seats = seats;
        this.route = route;
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("Vehical Type",vehicalType);
        result.put("Bus Number",busNo);
        result.put("Company Name",company);
        result.put("Seats",seats);
        result.put("Travelling Route",route);
        result.put("Departure Time",time);
        result.put("Ticket Rate",rate);
        return result;
    }
}