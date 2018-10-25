package com.example.abhishek.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.example.abhishek.myapplication.PendAccFragment.pendaccList;
import static com.example.abhishek.myapplication.PendAccFragment.usersListAdapter2;
import static com.example.abhishek.myapplication.FinDecFragment.findecList;
import static com.example.abhishek.myapplication.FinDecFragment.usersListAdapter1;
import static com.example.abhishek.myapplication.ReadyFragment.readyList;
import static com.example.abhishek.myapplication.ReadyFragment.usersListAdapter3;

public class MainActivity extends AppCompatActivity {

    static List<UserModel> userModelList;
    static int page_status;
    static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        



        final SharedPreferences sharedPref = this.getSharedPreferences("com.bits-oasis.test.appData", Context.MODE_PRIVATE);
        String test = sharedPref.getString("token", null);
        String stall_name = sharedPref.getString("username", null);
        if (test == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }




       // Button button = (Button)findViewById(R.id.button);
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemAvailablity.class);
                startActivity(intent);
            }
        });*/






        final UsersListAdapter usersListAdapter;

        userModelList = new ArrayList<>();
        findecList=new ArrayList<>();
        usersListAdapter1 = new UsersListAdapter(findecList);
        pendaccList=new ArrayList<>();
        usersListAdapter2 = new UsersListAdapter(pendaccList);
        readyList=new ArrayList<>();
        usersListAdapter3 = new UsersListAdapter(readyList);
        usersListAdapter = new UsersListAdapter(userModelList);//Pending n accepted    Ready    Finished n Declined

        final String TAG = "Firelog ";
        /*final Button log_out = (Button) findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor1 = sharedPref.edit();

                editor1.putString("token", null);
                editor1.commit();
                String tok = sharedPref.getString("token", null);
                Toast.makeText(getApplicationContext(), "Button" + tok, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);

            }
        });*/


        //String jwt = "JWT" + " " + test;
        //RecyclerView mMainList;
        //mMainList = (RecyclerView) findViewById(R.id.main_list);
        //mMainList.setHasFixedSize(true);
        //mMainList.setLayoutManager(new LinearLayoutManager(this));
        //mMainList.setAdapter(usersListAdapter);
        FirebaseApp.initializeApp(this);
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        //Query query = mFirestore.collection()


        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
        mFirestore.setFirestoreSettings(settings);
        Log.d(TAG , "name"+stall_name);

       if(stall_name != null){
        mFirestore.collection(stall_name).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {}


                userModelList.clear();
//                    Log.d(TAG, "Error" + e.getMessage());
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
          //          String customer_name;
            //        String status;
                    {
                        if (doc.getId().contains("OrderFragment")) {
                            //customer_name = doc.getString("name");
                           // status = doc.getString("status");
                           //// Log.d(TAG , "Name:"+ customer_name);
                           // Log.d(TAG , "Status"+status);*/

                            UserModel userModel = doc.toObject(UserModel.class);
                            userModelList.add(userModel);
                            usersListAdapter.notifyDataSetChanged();

                            if(userModel.status.equals("Finished")||userModel.status.equals("Declined"))
                                {findecList.add(0,userModel);usersListAdapter1.notifyDataSetChanged();}


                                if(userModel.status.equals("Pending")||userModel.status.equals("Accepted"))
                                {pendaccList.add(0,userModel);usersListAdapter2.notifyDataSetChanged();}


                                if(userModel.status.equals("Ready"))
                                {readyList.add(0,userModel);usersListAdapter3.notifyDataSetChanged();}
                        }

                    /*if(doc.getId().contains("OrderFragment"))
                    {
                        customer_name = doc.getString("name");
                        status = doc.getString("status");
                        Log.d(TAG , "Name:"+ customer_name);
                        Log.d(TAG , "Status"+status);
                    }*/


                    }

                }
            }

        }
        );}

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        //Add fragments
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FinDecFragment());
        adapter.addFragment(new PendAccFragment());
        adapter.addFragment(new ReadyFragment());

        //Setting adapter
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

}
