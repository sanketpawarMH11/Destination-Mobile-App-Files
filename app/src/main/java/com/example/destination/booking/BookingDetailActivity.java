package com.example.destination.booking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.destination.R;
import com.google.firebase.auth.FirebaseAuth;

public class BookingDetailActivity extends AppCompatActivity {

    TextView name,person,date,contact,total;
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        name=(TextView)findViewById(R.id.name);
        person=(TextView)findViewById(R.id.persons);
        date=(TextView)findViewById(R.id.date);
        contact=(TextView)findViewById(R.id.contact);
        total=(TextView)findViewById(R.id.total);
        exit=(Button)findViewById(R.id.exit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Your Ticket");
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        String Tname=i.getStringExtra("name");
        String Tperson=i.getStringExtra("people");
        String Tcontact=i.getStringExtra("contact");
        String Tdate=i.getStringExtra("date");
        String Ticket=i.getStringExtra("Ticket");

        name.setText("Name :\t" +Tname);
        person.setText("Travellers :\t" +Tperson);
        date.setText("Travelling on date:\t" +Tdate);
        contact.setText("Contact No. :\t" +Tcontact);
        total.setText("Total Fare :\t" +Ticket);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingDetailActivity.this,Payment.class);
                startActivity(intent);
            }
        });
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
                startActivity(new Intent(this, LoginActivity.class));
                finish();

             default:
                return super.onOptionsItemSelected(item);

        }

    }
}
