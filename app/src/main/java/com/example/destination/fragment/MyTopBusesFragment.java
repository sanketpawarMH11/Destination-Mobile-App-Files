package com.example.destination.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopBusesFragment extends BusListFragment {
    public MyTopBusesFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // My top posts by number of stars
        return databaseReference.child("BusList").child(getUid()).orderByChild("starCount");
    }
}