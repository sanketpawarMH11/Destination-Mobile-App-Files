package com.example.destination.booking.mtBookings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyBookingFragment extends BookingListFragment {
    public MyBookingFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("Booking List").child(getUid());
    }
}