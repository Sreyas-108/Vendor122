package com.example.abhishek.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by abhishek on 22/10/18.
 */



public class User1ListAdapter extends RecyclerView.Adapter<User1ListAdapter.ViewHolder1> {

    boolean is_available;
    int id ;
    JSONObject request,response;
    String requestresponse;
    String jwt;
    int NO_INTERNET = -1;



    public List<User1Model> itemModelList;
    public User1ListAdapter(List<User1Model> itemModelList){
        this.itemModelList = itemModelList;

    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.availablity_row , parent , false);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder1 holder, final int position) {
        holder.availablity_row_name.setText(itemModelList.get(position).getName());
        holder.availablity_row_id.setText(itemModelList.get(position).getId());
        holder.availablity_switch.setChecked(itemModelList.get(position).is_available);
        id = itemModelList.get(position).getId();
        is_available= itemModelList.get(position).is_available;



        try {
            request.put("item_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.availablity_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    request.put("available",b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendPost sendPost= new sendPost();
                sendPost.execute();
                itemModelList.get(position).is_available=b;
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }


    static class ViewHolder1 extends RecyclerView.ViewHolder {

        Switch availablity_switch;
        View mView1;
        TextView availablity_row_name ;TextView availablity_row_id;
        ViewHolder1(View view) {
            super(view);
            mView1= view;
            availablity_row_name= (TextView)mView1.findViewById(R.id.availablity_row_name);
            availablity_switch = (Switch)mView1.findViewById(R.id.availablity_switch);

        }
    }


    class sendPost extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //loginPanel.setVisibility(View.INVISIBLE);
            //progressBar.setVisibility(View.VISIBLE);
            //  retry.setVisibility(View.GONE);

        }

        @Override
        protected Integer doInBackground(Void... voids) {

            int responseCode = NO_INTERNET;
            URL url;
            Log.v("json-body", request.toString());

            try {
                url = new URL("http://test.bits-oasis.org/2018/shop/stalls/client/switch/item");
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

            /*if (responseCode == NO_INTERNET) {
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

                //successful();

                else {

                }*/




        }
    }

}