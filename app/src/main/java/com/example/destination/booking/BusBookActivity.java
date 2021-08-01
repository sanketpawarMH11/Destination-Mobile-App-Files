package com.example.destination.booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destination.BaseActivity;
import com.example.destination.R;
import com.example.destination.models.Booking;
import com.example.destination.models.Bus;
import com.example.destination.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusBookActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = "BusBookActivity";
	public static final String EXTRA_POST_KEY = "post_key";
	private DatabaseReference mPostReference, mCommentsReference;
	private ValueEventListener mPostListener;
	private CommentAdapter mAdapter;
	private TextView mAuthorView, mTitleView, mBodyView;
	private EditText name,persons,contact,date;
    DatePickerDialog picker;
	private RecyclerView mCommentsRecycler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_book);
		mAuthorView = findViewById(R.id.post_author);
		mTitleView = findViewById(R.id.post_title);
		mBodyView = findViewById(R.id.post_body);
		name=findViewById(R.id.name);
		persons=findViewById(R.id.seats);
		date=findViewById(R.id.datetext);
		contact=findViewById(R.id.contact);
		//mCommentField = findViewById(R.id.field_comment_text);

		mCommentsRecycler = findViewById(R.id.recycler_comments);
		mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));

		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Calendar cldr = Calendar.getInstance();
				int day = cldr.get(Calendar.DAY_OF_MONTH);
				int month = cldr.get(Calendar.MONTH);
				int year = cldr.get(Calendar.YEAR);
				// date picker dialog
				picker = new DatePickerDialog(BusBookActivity.this,
						new DatePickerDialog.OnDateSetListener()
						{
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
							{
								date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
							}
						}, year, month, day);
				picker.show();
			}
		});
		Button mCommentButton = findViewById(R.id.button_post_comment);
		mCommentButton.setOnClickListener(this);

		// Get post key from intent
		String mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
		if (mPostKey == null) {
			throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
		}

		// Initialize Database
		mPostReference = FirebaseDatabase.getInstance().getReference().child("All Bus").child(mPostKey);
		mCommentsReference = FirebaseDatabase.getInstance().getReference().child("Booking List").child(mPostKey);
	}

	@Override
	public void onStart() {
		super.onStart();


		// Add value event listener to the post
		ValueEventListener postListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				// Get Bus object and use the values to update the UI
				Bus bus = dataSnapshot.getValue(Bus.class);

				//mAuthorView.setText(bus.author);
				mTitleView.setText(bus.title);
				mBodyView.setText(bus.body);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Getting Bus failed, log a message
				Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
				Toast.makeText(BusBookActivity.this, "Failed to load bus.", Toast.LENGTH_SHORT).show();
			}
		};
		mPostReference.addValueEventListener(postListener);

		// Keep copy of post listener so we can remove it when app stops
		mPostListener = postListener;

		// Listen for comments
		mAdapter = new CommentAdapter(this, mCommentsReference);
		mCommentsRecycler.setAdapter(mAdapter);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mPostListener != null) {
			mPostReference.removeEventListener(mPostListener);
		}
		mAdapter.cleanupListener();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button_post_comment) {
			if (FirebaseAuth.getInstance().getCurrentUser()!=null) {

				postComment();
			}
			else
			{
				startActivity(new Intent(this,LoginActivity.class));
			}
		}
	}


	private void postComment() {

        final String uid = getUid();

            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user information
                    User user = dataSnapshot.getValue(User.class);
                    //String authorName = user.username;
					String rate=mTitleView.getText().toString().trim();
					String Tname = name.getText().toString().trim();
					String Tno = persons.getText().toString().trim();
					String Tcontact = contact.getText().toString().trim();
					String Tdate =date.getText().toString().trim();
					if (validateform(Tname,Tno,Tdate,Tcontact)) {
						// Create new booking object
						//mCommentField.getText().toString().trim();

						Double persons = Double.parseDouble(Tno);
						Double ticket = Double.parseDouble(rate);
					Double total = persons * ticket;
						String count = String.valueOf(total);


						String commentText = count;

						Booking booking = new Booking(uid, commentText, Tname, Tcontact, Tdate, Tno);

						// Push the booking, it will appear in the list
						mCommentsReference.child(uid).push().setValue(booking);



					Intent i=new Intent(BusBookActivity.this, BookingDetailActivity.class);
					//i.putExtra("Ticket",rate);
					i.putExtra("name",Tname);
					i.putExtra("people",Tno);
					i.putExtra("date",Tdate);
					i.putExtra("contact",Tcontact);
					i.putExtra("Ticket",count);

					Toast.makeText(BusBookActivity.this,"info added",Toast.LENGTH_LONG).show();
					startActivity(i);
					}
                    // Clear the field
                    //mCommentField.setText(null);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(BusBookActivity.this, "onCancelled: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


    }

	private boolean validateform(String tname, String tno, String tdate, String tcontact) {
		if (TextUtils.isEmpty(tname)) {
			name.setError(getString(R.string.required));
			Toast.makeText(this,"Enter your name",Toast.LENGTH_LONG).show();
			return false;
		}
		else if (TextUtils.isEmpty(tno)) {
			persons.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Number of Persons",Toast.LENGTH_LONG).show();
			return false;
		}
		else if (TextUtils.isEmpty(tcontact)) {
			contact.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Contact Number",Toast.LENGTH_LONG).show();
			return false;
		}

		else if (tcontact.length()>10&&tcontact.length()<10) {
			contact.setError(getString(R.string.required));
			Toast.makeText(this," Number should be 10 digit long",Toast.LENGTH_LONG).show();
			return false;
		}
		else {
			//mTitleField.setError(null);
			//mBodyField.setError(null);
			name.setError(null);
			persons.setError(null);
			contact.setError(null);


			return true;
		}
	}

	private static class CommentViewHolder extends RecyclerView.ViewHolder {
		TextView authorView;
		TextView bodyView;
		CommentViewHolder(View itemView) {
			super(itemView);
			authorView = itemView.findViewById(R.id.comment_author);
			bodyView = itemView.findViewById(R.id.comment_body);
		}
	}

	private static class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
		private Context mContext;
		private DatabaseReference mDatabaseReference;
		private ChildEventListener mChildEventListener;
		private List<String> mCommentIds = new ArrayList<>();
		private List<Booking> mBookings = new ArrayList<>();

		CommentAdapter(final Context context, DatabaseReference ref) {
			mContext = context;
			mDatabaseReference = ref;

			// Create child event listener
			ChildEventListener childEventListener = new ChildEventListener() {
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
					Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

					// A new booking has been added, add it to the displayed list
					Booking booking = dataSnapshot.getValue(Booking.class);

					// Update RecyclerView
					mCommentIds.add(dataSnapshot.getKey());
					mBookings.add(booking);
					notifyItemInserted(mBookings.size() - 1);
				}

				@Override
				public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
					Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

					// A comment has changed, use the key to determine if we are displaying this
					// comment and if so displayed the changed comment.
					Booking newBooking = dataSnapshot.getValue(Booking.class);
					String commentKey = dataSnapshot.getKey();

					int commentIndex = mCommentIds.indexOf(commentKey);
					if (commentIndex > -1) {
						// Replace with the new data
						mBookings.set(commentIndex, newBooking);

						// Update the RecyclerView
						notifyItemChanged(commentIndex);
					} else {
						Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
					}
				}

				@Override
				public void onChildRemoved(DataSnapshot dataSnapshot) {
					Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

					// A comment has changed, use the key to determine if we are displaying this
					// comment and if so remove it.
					String commentKey = dataSnapshot.getKey();

					int commentIndex = mCommentIds.indexOf(commentKey);
					if (commentIndex > -1) {
						// Remove data from the list
						mCommentIds.remove(commentIndex);
						mBookings.remove(commentIndex);

						// Update the RecyclerView
						notifyItemRemoved(commentIndex);
					} else {
						Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
					}
				}

				@Override
				public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
					Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

					// A comment has changed position, use the key to determine if we are
					// displaying this comment and if so move it.
					//Booking movedComment = dataSnapshot.getValue(Booking.class);
					//String commentKey = dataSnapshot.getKey();
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					Log.w(TAG, "postComments:onCancelled", databaseError.toException());
					Toast.makeText(mContext, "Failed to load comments.", Toast.LENGTH_SHORT).show();
				}
			};
			ref.addChildEventListener(childEventListener);

			// Store reference to listener so it can be removed on app stop
			mChildEventListener = childEventListener;
		}

		@Override
		public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(R.layout.item_comment, parent, false);
			return new CommentViewHolder(view);
		}

		@Override
		public void onBindViewHolder(CommentViewHolder holder, int position) {
			Booking booking = mBookings.get(position);
			holder.authorView.setText(booking.author);
			holder.bodyView.setText(booking.text);
		}

		@Override
		public int getItemCount() {
			return mBookings.size();
		}

		void cleanupListener() {
			if (mChildEventListener != null) {
				mDatabaseReference.removeEventListener(mChildEventListener);
			}
		}
	}

}