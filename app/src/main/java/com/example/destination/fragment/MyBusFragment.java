package com.example.destination.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyBusFragment extends BusListFragment {
    public MyBusFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("BusList").child(getUid());
    }
}