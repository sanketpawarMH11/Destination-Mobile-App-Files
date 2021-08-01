package com.example.destination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.destination.models.Bus;
import com.example.destination.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewBusActivity extends BaseActivity {
	private DatabaseReference mDatabase;
	private EditText mTitleField, mBodyField,vehical,operator,busno,routef,routet,time,rate,seats;
	private FloatingActionButton mSubmitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_bus);

		///getting the toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		//setting the title
		toolbar.setTitle("Add Bus");
		//toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

		//placing toolbar in place of actionbar
		setSupportActionBar(toolbar);

		//mTitleField = findViewById(R.id.field_title);
		//mBodyField = findViewById(R.id.field_body);
		vehical=(EditText)findViewById(R.id.vehical);
		operator=(EditText)findViewById(R.id.provi);
		busno=(EditText)findViewById(R.id.busno);
		routef=(EditText)findViewById(R.id.routef);
		routet=(EditText)findViewById(R.id.routet);
		time=(EditText)findViewById(R.id.ti);
		rate=(EditText)findViewById(R.id.ra);
		seats=(EditText)findViewById(R.id.se);
		mSubmitButton = findViewById(R.id.fab_submit_post);

		mDatabase = FirebaseDatabase.getInstance().getReference();

		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submitPost();
			}
		});
	}

	private boolean validateForm(String title, String body, String vehicalType, String operatorCmp,String busnum, String seate, String routesf,String routest, String times,  String ticketrate) {
		/*if (TextUtils.isEmpty(title)) {
			mTitleField.setError(getString(R.string.required));
			return false;
		} else if (TextUtils.isEmpty(body)) {
			mBodyField.setError(getString(R.string.required));
			return false;
		}*/
		if (TextUtils.isEmpty(vehicalType)) {
			vehical.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Vehical type",Toast.LENGTH_LONG).show();
			return false;
		}
		if (TextUtils.isEmpty(operatorCmp)) {
			operator.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Company Name",Toast.LENGTH_LONG).show();
			return false;
		} if (TextUtils.isEmpty(busnum)) {
			busno.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Bus Number",Toast.LENGTH_LONG).show();
			return false;
		}
		 if (TextUtils.isEmpty(seate)) {
			seats.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Number of Seats",Toast.LENGTH_LONG).show();
			return false;
		}

		 if (TextUtils.isEmpty(routesf)) {
			routef.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Travel From",Toast.LENGTH_LONG).show();
			return false;
		}
		 if (TextUtils.isEmpty(routest)) {
			routet.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Travel To",Toast.LENGTH_LONG).show();
			return false;
		}
		 if (TextUtils.isEmpty(times)) {
			time.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Departure Time",Toast.LENGTH_LONG).show();
			return false;
		} if (TextUtils.isEmpty(ticketrate)) {
			rate.setError(getString(R.string.required));
			Toast.makeText(this,"Enter Ticket Rate",Toast.LENGTH_LONG).show();
			return false;
		}
		 else {
			//mTitleField.setError(null);
			//mBodyField.setError(null);
			vehical.setError(null);
			busno.setError(null);
			operator.setError(null);
			seats.setError(null);
			time.setError(null);
			routef.setError(null);
			routet.setError(null);
			rate.setError(null);

			return true;
		}

	}

	private void submitPost() {
		//final String title = mTitleField.getText().toString().trim();
		//final String body = mBodyField.getText().toString().trim();
		final String vehicalType=vehical.getText().toString().trim();
		final  String operatorCmp=operator.getText().toString().trim();
		final String busnum=busno.getText().toString().trim();
		final String routesf=routef.getText().toString().trim();
		final String routest=routet.getText().toString().trim();
		final String ticketrate= rate.getText().toString().trim();
		final String seate= seats.getText().toString().trim();
		final String times=time.getText().toString().trim();
		final String Troute=routesf+" To "+routest;
		final String userId = getUid();
		final String body = "Vehical Type:\t"+vehicalType+"\n"+"Company Name:\t"+operatorCmp+"\n"+"Bus Number:\t"+busnum+"\n"+"Travelling Route:\t"+Troute+"\n"+"Departure Time:\t"+times;
		final String title = ticketrate;


		if (validateForm(title, body,vehicalType,operatorCmp,busnum,routesf,routest,ticketrate,seate,times)) {
			// Disable button so there are no multi-posts
		//	setEditingEnabled(false);
			mDatabase.child("users");
			mDatabase.child(userId);
			mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					User user = dataSnapshot.getValue(User.class);
					if (user == null) {
						Toast.makeText(NewBusActivity.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
					} else {
						writeNewPost(userId, user.username, title, body, vehicalType, busnum, operatorCmp, ticketrate, times, seate, Troute);
					}
					setEditingEnabled(true);
					finish();
				}

				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					setEditingEnabled(true);
					Toast.makeText(NewBusActivity.this, "onCancelled: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	@SuppressLint("RestrictedApi")
	private void setEditingEnabled(boolean enabled) {
		//mTitleField.setEnabled(enabled);
		//mBodyField.setEnabled(enabled);
		if (enabled) {
			mSubmitButton.setVisibility(View.VISIBLE);
		} else {
			mSubmitButton.setVisibility(View.GONE);
		}
	}

	private void writeNewPost(String userId, String username, String title,
							  String body, String vehicalType, String busnum,
							  String operatorCmp, String ticketrate, String times, String seate, String routes) {
		// Create new bus at /user-posts/$userid/$postid
		// and at /posts/$postid simultaneously
		String key = mDatabase.child("All Bus").push().getKey();
		Bus bus = new Bus(userId, username, title, body,ticketrate,vehicalType,operatorCmp,busnum,seate,routes,times);
		Map<String, Object> postValues = bus.toMap();

		Map<String, Object> childUpdates = new HashMap<>();
		childUpdates.put("/All Bus/" + busnum, postValues);
		childUpdates.put("/BusList/" + userId + "/" + busnum, postValues);

		mDatabase.updateChildren(childUpdates);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()){

			case R.id.action_logout:
				FirebaseAuth.getInstance().signOut();
				startActivity(new Intent(this, LoginOperator.class));
				finish();

			case R.drawable.ic_arrow_back_black_24dp:
				return super.onOptionsItemSelected(item);

		}
		return true;
	}
}