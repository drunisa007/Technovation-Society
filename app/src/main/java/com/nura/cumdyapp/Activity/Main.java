package com.nura.cumdyapp.Activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nura.cumdyapp.Adapter.MyMainRecyclerAdapter;
import com.nura.cumdyapp.Adapter.MyViewpagerAdapter;
import com.nura.cumdyapp.Model.King;
import com.nura.cumdyapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Main extends AppCompatActivity {


    private RecyclerView recyclerviewMain;

    private List<King> mList = new ArrayList<>();

    private Toolbar toolbar;

    private ViewPager viewpager;

    private List<String> wholekingsList=new ArrayList<>();

    private List<String> wholequeensList=new ArrayList<>();

    private List<String> fresherkingsList=new ArrayList<>();

    private List<String> fresherqueensList=new ArrayList<>();

    private List[] ListArray= {wholekingsList, wholequeensList, fresherkingsList, fresherqueensList};


    private CircleIndicator dot_indicator;


    private int index=0;

    private int pos=0;

    private int positions=0;

  private   CoordinatorLayout coor;

  private DatabaseReference mdRef1= FirebaseDatabase.getInstance().getReference().child("wholekings");
    private DatabaseReference mdRef2= FirebaseDatabase.getInstance().getReference().child("wholequeens");
    private DatabaseReference mdRef3= FirebaseDatabase.getInstance().getReference().child("fresherkings");
    private DatabaseReference mdRef4= FirebaseDatabase.getInstance().getReference().child("fresherqueens");

    private  DatabaseReference[] listRef={mdRef1,mdRef2,mdRef3,mdRef4};







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        givingId();

        settingToolbar();

        initCollapsingToolbar();

        workingForSlideImages();

        workingforRecyclerView();



    }


    private void workingForSlideImages() {
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new Main.MyTimeTask(),3000,3000);
        MyViewpagerAdapter adapter = new MyViewpagerAdapter(ListArray[positions], Main.this);
        viewpager.setAdapter(adapter);
        dot_indicator.setViewPager(viewpager);
    }








    public class MyTimeTask extends TimerTask {

        @Override
        public void run() {

            Main.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    changingPages(ListArray[positions].size(),pos);

                }
            });
        }

    }

    private void changingPages(int size,int position) {
        index=1;
        if(viewpager.getCurrentItem()+1==size){

            //viewpager.setCurrentItem(0);
            if(positions==0){
                positions=1;
            }
            else if(positions==1){
                positions=0;
            }

            MyViewpagerAdapter adapter=new MyViewpagerAdapter(ListArray[positions],Main.this);
            adapter.notifyDataSetChanged();
            viewpager.setCurrentItem(0,true);
            viewpager.setAdapter(adapter);
            dot_indicator.setViewPager(viewpager);

        }
        else{
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
        }


    }


    private void settingToolbar() {
        setSupportActionBar(toolbar);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void givingId() {

        coor=findViewById(R.id.testingcoordinatorlayout);

        recyclerviewMain=findViewById(R.id.recyclerviewMain);

        toolbar=findViewById(R.id.toolbar);

        viewpager=findViewById(R.id.slide_image);

        dot_indicator=findViewById(R.id.dot_indicator);

        AppBarLayout abl = findViewById(R.id.appbar);
        ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());


settingSliderData();

    }

    private void settingSliderData() {



        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Fone.jpg?alt=media&token=75c700b4-d1d5-4475-9c06-0fef30dd3142");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Fthree.jpg?alt=media&token=11d709cc-5118-41c9-be08-20bbde579f67");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Ftwo.png?alt=media&token=0a2b6751-e3f0-47cf-ab5f-8445cdbfd749");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Ffour.jpg?alt=media&token=85ea2dd7-e3cb-40b8-bf73-047cc9c207d8");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Ffive.jpg?alt=media&token=5eca7cc0-9e2e-4578-96e4-c5ac00cf7c98");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Fsix.JPG?alt=media&token=860ab8f6-9675-438b-91e4-5f1ff4b46540");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Fseven.jpg?alt=media&token=8bd0c9ef-87cb-439c-821c-7b0fd4769b71");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Feight.jpg?alt=media&token=1706d30f-b35c-4c5b-9ede-4e9363656ca9");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Fnine.jpg?alt=media&token=1971a28b-4194-44c6-b52f-430b221a23c9");
        wholekingsList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actress%2Ften.jpg?alt=media&token=37d1d116-df60-4b9c-997d-36b3f2f1a2fc");



        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Fone.jpg?alt=media&token=8be8afe5-ec2f-408e-aaa0-cbd327378bbe");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Ftwo.jpg?alt=media&token=ecbbf680-4a50-42e1-a9fe-2f98b152b4f7");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Fthree.jpg?alt=media&token=573b46ed-118b-4769-9746-dc7a39b62cb4");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Ffour.jpg?alt=media&token=1d882628-4525-4195-8921-2e2ce9d36740");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Ffive.jpg?alt=media&token=aad87973-0e5a-4f4e-99fd-baf9a12c9655");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Fsix.jpg?alt=media&token=c2717ada-0f0f-4699-b294-cb2327ac409c");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Fseven.jpg?alt=media&token=41625e91-b514-4cb5-a5a9-19fbfcf1373b");
        wholequeensList.add("https://firebasestorage.googleapis.com/v0/b/voting-e3cdd.appspot.com/o/actor%2Feight.jpg?alt=media&token=cbabfded-ae9b-42a7-8a3e-6be6c62cd76f");


    }

    private void workingforRecyclerView() {


        int drawables[]={R.drawable.thewholeking,R.drawable.thewholequeen,
                          R.drawable.fresherking,R.drawable.fresherqueen
                         ,R.drawable.fashionking,R.drawable.fashionqueen};

        String names[]={"The Whole King","The Whole Queen","Fresher King"
                        ,"Fresher Queen","Voting Results","About"};


        for(int i=0;i<6;i++){
            mList.add(new King(names[i],drawables[i]));
        }


        recyclerviewMain.setLayoutManager(new GridLayoutManager(Main.this,2));

        recyclerviewMain.setHasFixedSize(true);

        recyclerviewMain.setAdapter(new MyMainRecyclerAdapter(Main.this,mList,coor,Main.this));
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!haveNetworkConnection()){
            showSnack();
        }


        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

            CollapsingToolbarLayout collapsing_toolbar_layout = findViewById(R.id.collapsing_toolbar);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
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
    private void showSnack() {
         String   message = "No Connection !";
         int  color = Color.RED;
            CoordinatorLayout coor=findViewById(R.id.testingcoordinatorlayout);

            Snackbar snackbar = Snackbar.make(coor, message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextSize(17);
            textView.setTextColor(Color.parseColor("#0084ff"));
            snackbar.show();
        }

    }



