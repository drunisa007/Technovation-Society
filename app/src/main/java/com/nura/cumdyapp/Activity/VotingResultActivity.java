package com.nura.cumdyapp.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nura.cumdyapp.Fragment.Fragment_Position0;
import com.nura.cumdyapp.Fragment.Fragment_Position1;
import com.nura.cumdyapp.Fragment.Fragment_Position2;
import com.nura.cumdyapp.Fragment.Fragment_Position3;
import com.nura.cumdyapp.Model.Result;
import com.nura.cumdyapp.R;

import java.util.ArrayList;
import java.util.List;

public class VotingResultActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;

    private TabLayout mTablayout;

    private List<Result> mList=new ArrayList<>();

    private int[] icons={R.drawable.thewholeking,R.drawable.thewholequeen,R.drawable.fresherking,R.drawable.fresherqueen};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_result);

        givingId();

        settingToolbar();

        addingFragment();

        workingForViewPager();

        workingForTabLayout();

        settingToolbarTitle();

    }

    private void settingToolbarTitle() {

    }

    private void addingFragment() {


        mList.add(new Result(new Fragment_Position0(),"Wl Kings"));
        mList.add(new Result(new Fragment_Position1(),"Wl Queens"));
        mList.add(new Result(new Fragment_Position2(),"Fs Kings"));
        mList.add(new Result(new Fragment_Position3(),"Fs Queens"));

    }

    private void workingForTabLayout() {


        mTablayout.setupWithViewPager(mViewPager);

        settingIcon();

    }
    private void settingIcon() {
        for(int i=0;i<icons.length;i++){
            mTablayout.getTabAt(i).setIcon(icons[i]);

        }

    }

    private void workingForViewPager() {
       mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),mList));
    }

    private void settingToolbar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Voting Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void givingId() {

        mToolbar=findViewById(R.id.voting_result_toolbar);

        mViewPager=findViewById(R.id.voting_result_viewpager);

        mTablayout=findViewById(R.id.voting_result_tablayout);

        DatabaseReference mdRef= FirebaseDatabase.getInstance().getReference().child("Controls");
      /*  mdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("datas").getValue(String.class).equals("arun")){
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        List<Result> list=new ArrayList<>();


        MyViewPagerAdapter(FragmentManager fm, List<Result> mList) {
            super(fm);

            list=mList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}