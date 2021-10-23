package com.nura.cumdyapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nura.cumdyapp.Model.Voting;
import com.nura.cumdyapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingSingleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    private String mIntent_name;

    private String mToolbarName;

    private Toolbar mVoting_single_toolbar;

    private FirebaseRecyclerAdapter<Voting, MyVotingHolder> adapter;

    private Query query;

    private DatabaseReference mVotesRef;

    private DatabaseReference mCheckVotes;

    private DatabaseReference mCountRef;

    private DatabaseReference mRootRef;

    private DatabaseReference mCheckLoginRef;

    private DatabaseReference mKeyRef;

    private TextView vote_name;

    private CardView vote_cancel, vote_vote;

    private MaterialDialog mDialog;

    private Map<String,String> mKeysMap=new HashMap<>();

    private List<String> mKeyslist=new ArrayList<>();

    private DatabaseReference mKeysAdd;

    private MaterialDialog mDia;


    private String url;

    private int pos;




    private IntentIntegrator qrScan;

    private   int drawables[]={R.drawable.thewholeking,R.drawable.thewholequeen,
            R.drawable.fresherking,R.drawable.fresherqueen
            ,R.drawable.fashionking,R.drawable.fashionqueen};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voting_single);

        mIntent_name = getIntent().getStringExtra("name");

        mToolbarName = getIntent().getStringExtra("names");

        url          =getIntent().getStringExtra("url");

        pos=Integer.parseInt(url);



        givingId();

        settingToolbar();

        workingRecyclerJob();

        checkVoteOrNot();

        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("SCAN YOUR QR CODE");
        qrScan.setCameraId(0);  // Use a specific camera of the device
        qrScan.setOrientationLocked(false);
        qrScan.setBarcodeImageEnabled(true);


    }

    private void checkVoteOrNot() {

        if (firstrdFile().equals("1")) {
            mCheckVotes.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(readFile())) {
                        buttonseFile("1");
                    } else {
                        buttonseFile("0");
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    JSONObject obj = new JSONObject(result.getContents());


                } catch (JSONException e) {
                    e.printStackTrace();

                    checkingInput(result.getContents());



                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void settingToolbar() {
        setSupportActionBar(mVoting_single_toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setTitle(mToolbarName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.custom_layout,null);
        ImageView imageview_custom=  v.findViewById(R.id.imageview_custom);
       TextView textview_custom_name=  v.findViewById(R.id.textview_custom_name);
        Picasso.with(VotingSingleActivity.this).load(drawables[pos]).into(imageview_custom);
        textview_custom_name.setText(mToolbarName);
        actionbar.setCustomView(v);



/*
        setSupportActionBar(mVoting_single_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(mToolbarName);*/
        AppBarLayout abl = findViewById(R.id.app_bar);
        ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());

    }

    private void workingRecyclerJob() {

        FirebaseRecyclerOptions<Voting> options =
                new FirebaseRecyclerOptions.Builder<Voting>()

                        .setQuery(query, Voting.class)

                        .build();

        adapter = new FirebaseRecyclerAdapter<Voting, MyVotingHolder>(options) {
            @Override
            protected void onBindViewHolder(final MyVotingHolder holder, final int position, final Voting model) {

                holder.textviewname.setText(model.getName());

                holder.textviewno.setText(model.getNo());

                holder.height.setText(model.getHeight());

                Picasso.with(getApplicationContext()).load(model.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.voting_imageview, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext()).load(model.getUrl()).into(holder.voting_imageview);
                    }
                });



                holder.voting_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(VotingSingleActivity.this, PhotoView_Activity.class);
                        intent.putExtra("url", model.getUrl());
                        intent.putExtra("pos",model.getId());
                        startActivity(intent);

                    }
                });

                if (buttonrdFile().equals("1")) {
                    holder.voting_cardview.setEnabled(false);
                    holder.voting_cardview.setVisibility(View.INVISIBLE);
                }
                else {
                    if(rdFile().equals("1")){
                        holder.voting_cardview.setBackgroundColor(Color.parseColor("#4caf50"));

                    }else{
                        holder.voting_cardview.setBackgroundColor(Color.parseColor("#4fc2f8"));

                    }

                }

                holder.voting_cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(haveNetworkConnection()){
                            DatabaseReference mdRef= FirebaseDatabase.getInstance().getReference().child("Controls");

                            mdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value=dataSnapshot.child("value").getValue(String.class);

                                    if(value.equals("arun")){

                                        if (rdFile().equals("1")) {

                                            workingForDialog(model, position);

                                        } else {
                                            qrScan.initiateScan();
                                        }
                                    }
                                    else{
                                        new MaterialDialog.Builder(VotingSingleActivity.this).title("Alert")
                                                .positiveText("Ok")
                                                .content("This is not available right now")
                                                .positiveColor(Color.parseColor("#0084ff"))
                                                .show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else{

                            showSnack("Please,check your internet connection !");

                        }




                     /* for(int i=0;i<1000;i++){
                          UUID idOne = UUID.randomUUID();
                          mKeysMap.put(String.valueOf(i),idOne.toString());

                      }

                        mKeysAdd.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mKeysAdd.setValue(mKeysMap);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/









                    }
                });


            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (mDia != null && mDia.isShowing()) {
                    mDia.dismiss();
                }
            }

            @Override
            public MyVotingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_single_recycler_layout, parent, false);

                return new MyVotingHolder(v);
            }
        };

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);


    }


    private void checkingInput(final String s) {
        mCheckLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    if (s.equals(snap.getValue())) {
                        firstseFile("1");
                        seFile("1");
                        firstseFile("1");
                        saveFile(snap.getValue().toString());
                        firstseFile("1");
                        firstseFile("1");
                        firstseFile("1");

                        showSnack("Congratulations.You can vote for 2018 Kings and Queens");

                        recreate();

                        break;
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void workingForDialog(final Voting model, final int position) {
        mDialog = new MaterialDialog.Builder(VotingSingleActivity.this)
                .customView(R.layout.my_custom_dialog_for_vote, false)
                .build();

        View view = mDialog.getView();

        gettingId(view, model, position);

        mDialog.show();


    }
    @Override
    public void onPause() {
        super.onPause();

        if ((mDialog != null) && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = null;
    }

    private void gettingId(View view, final Voting model, final int position) {

        vote_name = view.findViewById(R.id.vote_name);

        vote_name.setText(Html.fromHtml("Are you sure ? You want to vote for " + model.getName()));

        vote_cancel = view.findViewById(R.id.vote_cancel);

        vote_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        vote_vote = view.findViewById(R.id.vote_vote);

        vote_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workingForClick(position, model);
            }
        });


    }

    private void workingForClick(final int position, final Voting model) {
        final DatabaseReference mdRef = mVotesRef.child(readFile());

        mdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mdRef.child("data").setValue(readFile());
                mCountRef.child("pos" + position).child(readFile()).child("data").setValue(model.getName());
                mDialog.dismiss();
                recreate();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void givingId() {

        mDia=new MaterialDialog.Builder(this)
                .content("Loading data . . .")
                .progress(true,0)
                .canceledOnTouchOutside(false)
                .build();

        mDia.show();

        mRecyclerView = findViewById(R.id.voting_single_recycler);

        mVoting_single_toolbar = findViewById(R.id.voting_single_toolbar);

        query = FirebaseDatabase.getInstance().getReference().child(mIntent_name);


        mVotesRef = FirebaseDatabase.getInstance().getReference().child(mIntent_name + "votes");

        mVotesRef.keepSynced(true);

        mCheckVotes = FirebaseDatabase.getInstance().getReference().child(mIntent_name + "votes");

        mCheckVotes.keepSynced(true);

        mCountRef = FirebaseDatabase.getInstance().getReference().child(mIntent_name + "counts");

        mCountRef.keepSynced(true);

        mRootRef = FirebaseDatabase.getInstance().getReference().child(mIntent_name + "count");

        mRootRef.keepSynced(true);

        mCheckLoginRef = FirebaseDatabase.getInstance().getReference().child("Keys");

        mCheckLoginRef.keepSynced(true);

        mKeyRef = FirebaseDatabase.getInstance().getReference().child("Keys");

        mKeyRef.keepSynced(true);

        mKeysAdd=FirebaseDatabase.getInstance().getReference().child("Keys");

        mKeysAdd.keepSynced(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();

    }
    private  boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void showSnack(String message) {
        //String   message = "Please, check you internet connection !";
        int  color = Color.WHITE;
        CoordinatorLayout coor=findViewById(R.id.coordina);

        Snackbar snackbar = Snackbar.make(coor, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextSize(20);
        textView.setTextColor(color);
        snackbar.show();
    }


    public static class MyVotingHolder extends RecyclerView.ViewHolder {
        View itemView;

        TextView textviewname, textviewno,height;

        CardView voting_cardview;

        TextView voting_textview;

        ImageView voting_imageview;

        public MyVotingHolder(View itemView) {

            super(itemView);

            this.itemView = itemView;

            textviewname = itemView.findViewById(R.id.voting_name);

            textviewno = itemView.findViewById(R.id.voting_number);

            voting_cardview = itemView.findViewById(R.id.voting_cardview_votes);

            voting_textview = itemView.findViewById(R.id.voting_textview);

            voting_imageview = itemView.findViewById(R.id.voting_imageview);
            height           =itemView.findViewById(R.id.height);

        }

    }

    private String readFile() {
        SharedPreferences sharedPref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String default_data = "0";
        String value = sharedPref.getString("data", default_data);
        return value;

    }

    private void saveFile(String value) {
        SharedPreferences sharedPref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", value);
        editor.commit();
    }

    private String rdFile() {
        SharedPreferences sharedPref = getSharedPreferences("mySave", Context.MODE_PRIVATE);
        String default_data = "0";
        String value = sharedPref.getString("data", default_data);
        return value;

    }

    private void seFile(String value) {
        SharedPreferences sharedPref = getSharedPreferences("mySave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", value);
        editor.commit();
    }


    private String firstrdFile() {
        SharedPreferences sharedPref = getSharedPreferences("myFirst", Context.MODE_PRIVATE);
        String default_data = "0";
        String value = sharedPref.getString("data", default_data);
        return value;

    }

    private void firstseFile(String value) {
        SharedPreferences sharedPref = getSharedPreferences("myFirst", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", value);
        editor.commit();
    }

    private String buttonrdFile() {
        SharedPreferences sharedPref = getSharedPreferences("myButton", Context.MODE_PRIVATE);
        String default_data = "0";
        String value = sharedPref.getString("data", default_data);
        return value;

    }

    private void buttonseFile(String value) {
        SharedPreferences sharedPref = getSharedPreferences("myButton", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", value);
        editor.commit();
    }




}
