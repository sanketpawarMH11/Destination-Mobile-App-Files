package com.example.destination.booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.destination.MyApplication;
import com.example.destination.R;
import com.google.firebase.auth.FirebaseAuth;

public class FromToActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String[] City={"Select City","Mumbai","Pune"};

    TextView login,signup;
    Button find;
   // EditText from,to;


    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_to);


        ///getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Destination");
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        login=(TextView)findViewById(R.id.Login);
        signup=(TextView)findViewById(R.id.signup);
        find=(Button)findViewById(R.id.find);

        Spinner s=(Spinner)findViewById(R.id.spinner);
        Spinner s1=(Spinner)findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(this);
        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,City);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        s1.setAdapter(aa);
      //  from=(EditText)findViewById(R.id.start);
     //   to=(EditText)findViewById(R.id.end);
       // final String start=from.getText().toString().trim();
       // final String end=to.getText().toString().trim();

      //  final MyApplication myApplication=(MyApplication)getApplicationContext();
       //  final String location =start;


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // final String start=from.getText().toString().trim();
               // final String end=to.getText().toString().trim();
               // String location=start+"-"+end;
               // myApplication.setLocation(location);

                Intent i=new Intent(FromToActivity.this, BusListActivity.class);
              // i.putExtra("Location",location);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(FromToActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(FromToActivity.this, SignUpActivity.class);

                startActivity(i);
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
        switch(item.getItemId()) {

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
