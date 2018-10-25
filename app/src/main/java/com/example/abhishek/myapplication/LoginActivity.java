package com.example.abhishek.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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


public class LoginActivity extends AppCompatActivity {

    final int NO_INTERNET = -1;

    boolean login = false;
    TextView username;
    TextView password;
    ProgressBar progressBar;
    Button retry;
    LinearLayout loginPanel;
    JSONObject requestBody;
    JSONObject response;
  //  String id = username.getText().toString();
    //String passwd = password.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        retry = (Button) findViewById(R.id.retry);
        loginPanel = (LinearLayout) findViewById(R.id.login_panel);




    }

    public void successful() {

        login = true;
        SharedPreferences sharedPref = this.getSharedPreferences("com.bits-oasis.test.appData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
      //  editor.putInt("stall_id",response.getInt("id"));
        editor.putBoolean("Auth", true);


        try {
            editor.putString("token",response.getString("token"));
            editor.putInt("stall_id",response.getInt("user_id"));

        }
        catch (JSONException e) {

        }

        editor.commit();

        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }


    public void login(View view) {

        String id = username.getText().toString();
        String passwd = password.getText().toString();
        SharedPreferences sharedPref = this.getSharedPreferences("com.bits-oasis.test.appData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username",id);
        editor.commit();


        if(id == "" || passwd == "") {
            Toast.makeText(LoginActivity.this,
                    "Username and password cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }

        requestBody = new JSONObject();

        try {
            requestBody.put("username", username.getText().toString());
            requestBody.put("password", password.getText().toString());
            requestBody.put("is_bitsian",false);
        }
        catch (JSONException e) {

        }

        ApiCall apiCall = new ApiCall();
        apiCall.execute();

//return id;
    }

    public void retry(View view) {
        retry.setVisibility(View.GONE);
        ApiCall apiCall = new ApiCall();
        apiCall.execute();

    }

    @Override
    public void onBackPressed() {
        if(login)
            super.onBackPressed();
        else
            Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();

    }

    class ApiCall extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loginPanel.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);

        }

        @Override
        protected Integer doInBackground(Void... voids) {

            int responseCode = NO_INTERNET;
            URL url;
            Log.v("json-body", requestBody.toString());

            try {
                url = new URL("http://test.bits-oasis.org/2018/shop/auth/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Wallet-Token", "asdf");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                os.write(requestBody.toString().getBytes("UTF-8"));
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

            } catch (MalformedURLException e) {

                Log.v("mal", e.toString());
            } catch (IOException e) {

                Log.v("io", e.toString());

                if(e instanceof UnknownHostException)
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
                Toast.makeText(LoginActivity.this,
                        "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                retry.setVisibility(View.VISIBLE);
            } else if (responseCode == 404) {
                Toast.makeText(LoginActivity.this,
                        "AUTHORIZATION FAILURE. Check credentials",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                loginPanel.setVisibility(View.VISIBLE);
            }
            else {

                progressBar.setVisibility(View.GONE);


                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    successful();

                }
                else {

                }


            }

        }
    }
}
