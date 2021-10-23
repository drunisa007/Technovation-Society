package com.nura.cumdyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nura.cumdyapp.Activity.About_Activity;
import com.nura.cumdyapp.Activity.Main;
import com.nura.cumdyapp.Activity.VotingResultActivity;
import com.nura.cumdyapp.Activity.VotingSingleActivity;
import com.nura.cumdyapp.Model.King;
import com.nura.cumdyapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyMainRecyclerAdapter extends RecyclerView.Adapter<MyMainRecyclerAdapter.MyViewHolder> {
    private List<King> mList=new ArrayList<>();

    private DatabaseReference mdRef= FirebaseDatabase.getInstance().getReference().child("Controls");

    private CoordinatorLayout coor;

    private Activity activity;




    private Context context;

    private String names[]={"wholekings","wholequeens","fresherkings","fresherqueens","fashionkings","fashionqueens"};

    private String name[]={"The Whole Kings","The Whole Queens","Fresher Kings","Fresher Queens","Fashion Kings","Fashion Queens"};




    public MyMainRecyclerAdapter(Main main, List<King> mList, CoordinatorLayout coor, Main main1) {
        this.context=main;
        this.mList=mList;
        this.coor=coor;
        activity=main1;
        mdRef.keepSynced(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_king_queenlayout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.recycler_king_textview.setText(mList.get(holder.getAdapterPosition()).getName());

        Picasso.with(context).load(mList.get(holder.getAdapterPosition()).getUrl()).into(holder.recycler_king_imageview);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("arun","one");
                if(holder.getAdapterPosition()==4){
                   if(haveNetworkConnection()){
                       //checkingData(view);
                       view.getContext().startActivity(new Intent(context,VotingResultActivity.class));
                   }
                   else{
                       Snackbar snackbar = Snackbar.make(coor, "No Connection !", Snackbar.LENGTH_LONG);

                       View sbView = snackbar.getView();
                       TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                       textView.setTextSize(17);
                       textView.setTextColor(Color.RED);
                       snackbar.show();
                   }


                }
                else if(holder.getAdapterPosition()==5){
                    view.getContext().startActivity(new Intent(context,About_Activity.class));
                }
                else{

                    ActivityOptionsCompat compact=ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.recycler_king_imageview,"away");

                    Intent intent=new Intent(context,VotingSingleActivity.class);

                    intent.putExtra("name",names[holder.getAdapterPosition()]);

                    intent.putExtra("names",name[holder.getAdapterPosition()]);

                    intent.putExtra("url",String.valueOf(position));


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.getContext().startActivity(intent,compact.toBundle());
                    }
                    else{
                        view.getContext().startActivity(intent);
                    }
                }


            }
        });
    }

    private void checkingData(final View view) {
        mdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             String value=dataSnapshot.child("data").getValue(String.class);

                if(value.equals("arun")){
                    view.getContext().startActivity(new Intent(context,VotingResultActivity.class));
                }
                else{
                    new MaterialDialog.Builder(context).title("Alert")
                            .positiveText("Ok")
                            .content("This room is not available right now >3 ")
                            .positiveColor(context.getResources().getColor(R.color.colorPrimary))
                            .show();
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        ImageView recycler_king_imageview;

        TextView  recycler_king_textview;

        CardView cardview_main;

        MyViewHolder(View itemView) {
            super(itemView);

            this.itemView=itemView;

            recycler_king_imageview=itemView.findViewById(R.id.recycler_king_imageview);

            recycler_king_textview=itemView.findViewById(R.id.recycler_king_textview);

            cardview_main=itemView.findViewById(R.id.cardview_main);
        }
    }
    private  boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
