package com.nura.cumdyapp.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nura.cumdyapp.Model.Voting_Result;
import com.nura.cumdyapp.R;

import java.util.ArrayList;
import java.util.List;



public class Fragment_Position3 extends Fragment {

    private RecyclerView mRecyclerview;
    private  List<Voting_Result> mList=new ArrayList<>();


    private DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("fresherqueens");



    public Fragment_Position3() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_fragment__position3, container, false);

        givingId(v);

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerview.setHasFixedSize(true);

        gettingData();


    }

    private void gettingData() {

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                savingData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void savingData(DataSnapshot dataSnapshot) {

        for(DataSnapshot snap:dataSnapshot.getChildren()){
            mList.add(new Voting_Result(snap.child("no").getValue(String.class),snap.child("name").getValue(String.class)));
        }
        mRecyclerview.setAdapter(new MyResultRecycler_Adapter(mList,"fresherqueenscounts"));
    }



    private void givingId(View v) {

        mRecyclerview=v.findViewById(R.id.result_recyclerviewthree);


    }


}
