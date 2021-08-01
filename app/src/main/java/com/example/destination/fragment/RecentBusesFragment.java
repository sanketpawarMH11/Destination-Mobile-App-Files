package com.example.destination.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentBusesFragment extends BusListFragment {
	public RecentBusesFragment() {
	}

	@Override
	public Query getQuery(DatabaseReference databaseReference) {
		// Last 100 posts, these are automatically the 100 most recent
		// due to sorting by push() keys
		//return databaseReference.child("All Bus").limitToFirst(5);
		return databaseReference.child("All Bus").orderByChild("Travelling Route").equalTo("Wai-satara").limitToLast(2);


		//return databaseReference.child("posts").orderByKey().startAt("-KRN9eHLLMJbYmJNFz9U").limitToFirst(10);
	}
}