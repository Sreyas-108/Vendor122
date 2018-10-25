package com.example.abhishek.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ItemAvailablity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_availablity);
        final List<User1Model> userModelList;
        final User1ListAdapter usersListAdapter;

        userModelList = new ArrayList<>();
        usersListAdapter = new User1ListAdapter(userModelList);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.bits-oasis.test.appData", Context.MODE_PRIVATE);
        String stall_name = "ItemClass #"+sharedPreferences.getInt("stall_id",0);

        RecyclerView mMainList;
        mMainList = (RecyclerView) findViewById(R.id.main1_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(usersListAdapter);
        FirebaseApp.initializeApp(this);
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        //Query query = mFirestore.collection()


        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
        mFirestore.setFirestoreSettings(settings);
        Log.d("stall_name", "name" + stall_name);

        if (stall_name != null) {
            mFirestore.collection(stall_name).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                    }
                    userModelList.clear();
//                   Log.d(TAG, "Error" + e.getMessage());
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        //          String customer_name;
                        //        String status;
                        {
                            if (doc.getId().contains("ItemClass")) {
                                //customer_name = doc.getString("name");
                                // status = doc.getString("status");
                                //// Log.d(TAG , "Name:"+ customer_name);
                                // Log.d(TAG , "Status"+status);*/
                                Log.d("f","item name"+doc.getString("name"));
                                User1Model userModel = doc.toObject(User1Model.class);
                                userModelList.add(userModel);
                                usersListAdapter.notifyDataSetChanged();
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
                }}
            );
        }


    }
}
