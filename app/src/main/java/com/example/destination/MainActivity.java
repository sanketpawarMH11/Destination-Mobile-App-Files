package com.example.destination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.destination.booking.FromToActivity;

public class MainActivity extends AppCompatActivity {

	Button operator,traveller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);


		///getting the toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		//setting the title
		toolbar.setTitle("Destination");


		//placing toolbar in place of actionbar
		setSupportActionBar(toolbar);



		operator=(Button)findViewById(R.id.operatorr);
		traveller=(Button)findViewById(R.id.traveller);
		operator.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(MainActivity.this, LoginOperator.class);
				startActivity(i);
			}
		});

		traveller.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(MainActivity.this, FromToActivity.class);
				startActivity(i);
			}
		});
	}
}
