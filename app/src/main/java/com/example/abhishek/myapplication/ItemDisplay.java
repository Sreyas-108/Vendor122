package com.example.abhishek.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static com.example.abhishek.myapplication.FinDecFragment.findecList;
import static com.example.abhishek.myapplication.MainActivity.page_status;
import static com.example.abhishek.myapplication.MainActivity.viewPager;
import static com.example.abhishek.myapplication.PendAccFragment.pendaccList;
import static com.example.abhishek.myapplication.ReadyFragment.readyList;

public class ItemDisplay extends AppCompatActivity {


    SharedPreferences sharedPref;
    String requestresponse = "";
    boolean show_otp = false;
    JSONObject request;
    //ListView item_display;
    RecyclerView mRecyclerview;
    String stall_name = null;
    String jwt, order_status = null;
    final int NO_INTERNET = -1;
    JSONObject response;
    TextView idquantity;
    Button accept, decline, ready, complete;
    boolean accepted = false;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getSharedPreferences("com.bits-oasis.test.appData", Context.MODE_PRIVATE);
        stall_name = sharedPref.getString("username", null);
        jwt = "JWT " + sharedPref.getString("token", null);
        setContentView(R.layout.activity_item_display);
        progressbar=findViewById(R.id.pb_status);
        accept = (Button)findViewById(R.id.accept);
        decline = (Button)findViewById(R.id.decline);
        complete = (Button)findViewById(R.id.complete);
        ready = (Button)findViewById(R.id.ready);
        //idquantity = (TextView)findViewById(R.id.item_display);

        int id = getIntent().getIntExtra("ID", 0);
        order_status = getIntent().getStringExtra("order_status");
        Log.d("status", order_status);

        request = new JSONObject();

        try {
            request.put("order_fragment", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String order_fragment = "OrderFragment #" + id;
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();


        mRecyclerview = findViewById(R.id.mRecyclerview);

        final List<ItemModel> itemList = new ArrayList<>();

        final ItemAdapter itemAdapter = new ItemAdapter(itemList);

        mRecyclerview.setAdapter(itemAdapter);





        mFirestore.collection(stall_name).document(order_fragment).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    show_otp = document.getBoolean("show_otp");
                    ArrayList<Map<String, Object>> itemMapList = (ArrayList<Map<String, Object>>) document.get("items_list");
                    String acc = "";
                    itemList.clear();
                    if (itemMapList != null) {
                        for (Map<String, Object> item : itemMapList) {
                            acc = item.get("name").toString() + "   X   " + item.get("qty").toString() + "\n\n";
                            Log.d("Array", acc);
                            itemList.add(new ItemModel(item.get("name").toString(), "X"+item.get("qty").toString()));

                        }
                        itemAdapter.notifyDataSetChanged();
                    }


                }

            }
        });






        if(order_status.equals("Pending")) {
            accept.setVisibility(View.VISIBLE);
            decline.setVisibility(View.VISIBLE);
        }

        if(order_status.equals("Accepted"))
         ready.setVisibility(View.VISIBLE);

        if(order_status.equals("Ready"))
            complete.setVisibility(View.VISIBLE);



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    request.put("order_status","accepted");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                accepted = true;
                sendPost sendPost = new sendPost();
                sendPost.execute();

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accepted = false;
                try {
                    request.put("order_status","declined");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendPost sendPost = new sendPost();
                sendPost.execute();
            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    request.put("order_status","ready");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendPost sendPost = new sendPost();
                sendPost.execute();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(show_otp){
                try {
                    request.put("order_status","finished");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendPost sendPost = new sendPost();
                sendPost.execute();}
                else
                {
                    Toast.makeText(ItemDisplay.this,
                            "Please press show otp on user end", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    class sendPost extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //loginPanel.setVisibility(View.INVISIBLE);
            //progressBar.setVisibility(View.VISIBLE);
            //  retry.setVisibility(View.GONE);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.VISIBLE);
                }
            });

        }

        @Override
        protected Integer doInBackground(Void... voids) {

            int responseCode = NO_INTERNET;
            URL url;
            Log.v("json-body", request.toString());

            try {
                url = new URL("http://test.bits-oasis.org/2018/shop/stalls/client/order-response/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Wallet-Token", "asdf");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", jwt);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                os.write(request.toString().getBytes("UTF-8"));
                os.close();

                responseCode = httpURLConnection.getResponseCode();

                Log.v("response code", "" + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK)
                    Log.v("success", "url connected ");

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);


                }

                response = new JSONObject(result.toString());
                Log.v("json-response", response.toString());
                requestresponse = response.getString("message");
                Log.d("request response", requestresponse);

            } catch (MalformedURLException e) {

                Log.v("mal", e.toString());
            } catch (IOException e) {

                Log.v("io", e.toString());

                if (e instanceof UnknownHostException)
                    return NO_INTERNET;

                return responseCode;
            } catch (JSONException e) {

            }


            return responseCode;
        }



        @Override
        protected void onPostExecute(Integer responseCode) {
            super.onPostExecute(responseCode);

            if (responseCode == NO_INTERNET) {
                Toast.makeText(ItemDisplay.this,
                        "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
                //retry.setVisibility(View.VISIBLE);
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Toast.makeText(ItemDisplay.this,
                        "AUTHORIZATION FAILURE. Check credentials",
                        Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
                //loginPanel.setVisibility(View.VISIBLE);
            } else if (responseCode == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(ItemDisplay.this, "successful", Toast.LENGTH_SHORT).show();
                    if (order_status.equals("Pending") && accepted)
                    {order_status = "Accepted";
                        accept.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                        ready.setVisibility(View.VISIBLE);}
                    else if (order_status.equals("Pending") && !accepted) {
                        order_status = "Declined";
                        accept.setVisibility(View.GONE);
                        decline.setVisibility(View.GONE);
                        page_status=1;
                    }
                    else if (order_status.equals("Accepted"))
                    {    order_status = "Ready";
                       ready.setVisibility(View.GONE);
                        complete.setVisibility(View.VISIBLE);
                    page_status=3;}


                    else if(order_status.equals("Ready")) {
                        order_status = "Finished";
                        complete.setVisibility(View.GONE);
                        page_status=1;
                    }
                    //successful();

                 else {

                }
                if(page_status==1){viewPager.setCurrentItem(0);page_status=0;}
                if(page_status==2){viewPager.setCurrentItem(1);page_status=0;}
                if(page_status==3){viewPager.setCurrentItem(2);page_status=0;}


            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.INVISIBLE);
                    findecList.clear();
                    pendaccList.clear();
                    readyList.clear();
                    finish();
                }
            });

        }
    }

}

