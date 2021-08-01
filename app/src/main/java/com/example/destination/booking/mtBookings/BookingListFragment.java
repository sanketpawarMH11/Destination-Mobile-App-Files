package com.example.destination.booking.mtBookings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.destination.BusDetailActivity;
import com.example.destination.R;
import com.example.destination.models.Bus;
import com.example.destination.viewholder.BusViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class BookingListFragment extends Fragment {
	private Activity mActivity;
	private DatabaseReference mDatabase;
	private FirebaseRecyclerAdapter<Bus, BusViewHolder> mAdapter;
	private RecyclerView mRecycler;

	public BookingListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_all_buses, container, false);
		mRecycler = rootView.findViewById(R.id.messages_list);
		mRecycler.setHasFixedSize(true);

		mDatabase = FirebaseDatabase.getInstance().getReference();
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();

		final Dialog mDialog = new Dialog(mActivity, R.style.NewDialog);
		mDialog.addContentView(
				new ProgressBar(mActivity),
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
		);
		mDialog.setCancelable(true);
		mDialog.show();

		// Set up Layout Manager, reverse layout
		LinearLayoutManager mManager = new LinearLayoutManager(mActivity);
		mManager.setReverseLayout(true);
		mManager.setStackFromEnd(true);
		mRecycler.setLayoutManager(mManager);

		// Set up FirebaseRecyclerAdapter with the Query
		Query postsQuery = getQuery(mDatabase);

		FirebaseRecyclerOptions<Bus> options = new FirebaseRecyclerOptions.Builder<Bus>()
				.setQuery(postsQuery, Bus.class)
				.build();

		mAdapter = new FirebaseRecyclerAdapter<Bus, BusViewHolder>(options) {
			@Override
			protected void onBindViewHolder(@NonNull BusViewHolder viewHolder, int position, @NonNull final Bus model) {
				final DatabaseReference postRef = getRef(position);

				/*if (model.stars.containsKey(getUid())) {
					viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
				} else {
					viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
				}*/

				// Bind Bus to ViewHolder, setting OnClickListener for the star button
				viewHolder.bindToBus(model);
				Log.d(model.busNo,"Return Text");
				Log.d(model.author,"Return Text");

				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mActivity, BusDetailActivity.class);
						intent.putExtra(BusDetailActivity.EXTRA_POST_KEY, postRef.getKey());
						startActivity(intent);
					}
				});
			}

			@NonNull
			@Override
			public BusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
				LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
				return new BusViewHolder(inflater.inflate(R.layout.item_post, viewGroup, false));
			}

			@Override
			public void onDataChanged() {
				super.onDataChanged();
				mDialog.dismiss();
			}
		};
		mRecycler.setAdapter(mAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mAdapter != null) {
			mAdapter.startListening();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAdapter != null) {
			mAdapter.stopListening();
		}
	}

	/*private void onStarClicked(DatabaseReference postRef) {
		postRef.runTransaction(new Transaction.Handler() {
			@NonNull
			@Override
			public Transaction.Result doTransaction(MutableData mutableData) {
				Bus p = mutableData.getValue(Bus.class);
				if (p == null) {
					return Transaction.success(mutableData);
				}

				if (p.stars.containsKey(getUid())) {
					// Unstar the post and remove self from stars
					p.starCount = p.starCount - 1;
					p.stars.remove(getUid());
				} else {
					// Star the post and add self to stars
					p.starCount = p.starCount + 1;
					p.stars.put(getUid(), true);
				}

				// Set value and report transaction success
				mutableData.setValue(p);
				return Transaction.success(mutableData);
			}

			@Override
			public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
				Log.d("postTransaction", "onComplete:" + dataSnapshot.getKey());
			}
		});
	}*/

	public String getUid() {
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

	public abstract Query getQuery(DatabaseReference databaseReference);
}