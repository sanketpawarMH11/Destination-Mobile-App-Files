package com.example.destination.booking.mtBookings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopBusesFragment extends BookingListFragment {
    public MyTopBusesFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // My top posts by number of stars
        return databaseReference.child("Booking List").child(getUid()).orderByChild("starCount");
    }
}