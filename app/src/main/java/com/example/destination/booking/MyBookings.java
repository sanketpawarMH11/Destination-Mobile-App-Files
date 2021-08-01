package com.example.destination.booking;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.destination.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyBookings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        Intent i=getIntent();
        String location=i.getStringExtra("Location");
        Log.d(location,"Location");
        ///getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Destination");
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);


        Bundle bundle=new Bundle();
        bundle.putString("Location",location);
        // BookingFragment recentPostsFragment=new MyBookingsFragment();
        //recentPostsFragment.setArguments(bundle);

        /*final FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final Fragment[] mFragments = new Fragment[] {




                   // new MyBookingsFragment(),
                    new MyPostsFragment(),

                    //new MyTopPostsFragment()
            };


            private final String[] mFragmentNames = new String[] {
                    getString(R.string.my_bookings)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
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
            case R.id.busList:
                if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    startActivity(new Intent(this, MyBookings.class));
                    return true;
                }
                else
                {
                    startActivity(new Intent(this, LoginActivity.class));
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }
}