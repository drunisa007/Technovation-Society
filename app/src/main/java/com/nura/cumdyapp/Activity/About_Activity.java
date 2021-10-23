package com.nura.cumdyapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nura.cumdyapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nura.cumdyapp.Activity.PhotoView_Activity.newFacebookIntent;

public class About_Activity extends AppCompatActivity {


    private  CircleImageView email,facebook,device_link,create_link;

    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        givingId();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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

    private void givingId() {
        toolbar=findViewById(R.id.about_toolbar);

        email=findViewById(R.id.email);

        facebook=findViewById(R.id.facebook);

        device_link=findViewById(R.id.device_feedback);

        create_link=findViewById(R.id.creater_link);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","prognura@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello,Arun");
                startActivity(emailIntent);
            }
        });

        device_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveNetworkConnection()){
                    new MaterialDialog.Builder(About_Activity.this)
                            .title("Feedback")
                            .input("Enter here", "Hi Arun", false, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                    if("fuck".equals(input.toString().toLowerCase())){
                                        Toast.makeText(About_Activity.this, "you too", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(About_Activity.this, "Your feedback is sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .show();
                }
                else{
                    Toast.makeText(About_Activity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });

        create_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(newFacebookIntent(getPackageManager(),"https://www.facebook.com/broisback"));
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(newFacebookIntent(getPackageManager(),"https://www.facebook.com/broisback"));
            }
        });

    }
}
