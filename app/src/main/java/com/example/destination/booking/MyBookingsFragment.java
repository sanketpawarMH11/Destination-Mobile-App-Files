package com.example.destination.booking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyBookingsFragment extends BookingFragment {
	public MyBookingsFragment() {
	}

	@Override
	public Query getQuery(DatabaseReference databaseReference) {
		// Last 100 posts, these are automatically the 100 most recent
		// due to sorting by push() keys

        //Bundle myApplication=getArguments();

       //String ss=getArguments().getString("Location","Wai-pune");

		return databaseReference.child("All Bus").orderByChild("Travelling Route").equalTo("Mumbai To Pune" ).limitToLast(2);


		//return databaseReference.child("posts").orderByKey().startAt("-KRN9eHLLMJbYmJNFz9U").limitToFirst(10);
	}


}