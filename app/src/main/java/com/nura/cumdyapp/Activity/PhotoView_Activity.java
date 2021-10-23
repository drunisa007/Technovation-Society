package com.nura.cumdyapp.Activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.nura.cumdyapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PhotoView_Activity extends AppCompatActivity {

    private PhotoView photo_view;

    private String url;

    private String pos;


    private TextView cardview_morepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_);

        givingId();


        url=getIntent().getStringExtra("url");

        pos=getIntent().getStringExtra("pos");



        Picasso.with(getApplicationContext()).load(url).networkPolicy(NetworkPolicy.OFFLINE).into(photo_view, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(url).into(photo_view);
            }
        });



        cardview_morepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos.equals("null")||pos.equals("")){
                    Toast.makeText(PhotoView_Activity.this, "There is no information", Toast.LENGTH_LONG).show();
                }
                else{
                    startActivity(newFacebookIntent(getPackageManager(),"https://www.facebook.com/"+pos));
                }

            }
        });




    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private void givingId() {

        photo_view=findViewById(R.id.photo_view);

        cardview_morepic=findViewById(R.id.cardview_morepic);


    }
}
