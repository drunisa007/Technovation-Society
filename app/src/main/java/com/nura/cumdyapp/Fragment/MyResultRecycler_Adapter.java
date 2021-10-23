package com.nura.cumdyapp.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nura.cumdyapp.Model.Voting_Result;
import com.nura.cumdyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 12/17/2017.
 */

public class MyResultRecycler_Adapter extends RecyclerView.Adapter<MyResultRecycler_Adapter.MyViewHolder> {

    List<Voting_Result> mList=new ArrayList<>();

    DatabaseReference mCountRef;

    public MyResultRecycler_Adapter(List<Voting_Result> list,String name){
        mList=list;
        mCountRef=FirebaseDatabase.getInstance().getReference().child(name);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_result_design,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textview_no.setText(mList.get(position).getNo());
        holder.textview_name.setText(mList.get(position).getName());
        settingData(position,holder);



    }

    private void settingData(final int position, final MyViewHolder holder) {
        mCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                  Log.e("haha",snap.getKey()+" "+snap.getChildrenCount());

                    if(String.valueOf(snap.getKey()).equals("pos"+position)){
                        holder.textview_vote.setText(String.valueOf(snap.getChildrenCount()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textview_no;
        TextView textview_name;
        TextView textview_vote;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            textview_no=itemView.findViewById(R.id.result_no);
            textview_name=itemView.findViewById(R.id.result_name);
            textview_vote=itemView.findViewById(R.id.result_count);

        }
    }
}
