package com.example.destination;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		FirebaseDatabase.getInstance().setPersistenceEnabled(true);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private String location;

}