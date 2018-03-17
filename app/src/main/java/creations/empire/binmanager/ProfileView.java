package creations.empire.binmanager;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfileView extends AppCompatActivity {
    TextView name, address, phone, email;
    Bundle bundle;
    String ip, port, username, password, sname, saddress, sphone, semail;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog pdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        name = findViewById(R.id.name_field);
        address = findViewById(R.id.address_field);
        phone = findViewById(R.id.no_field);
        email = findViewById(R.id.email_field);
        bundle = getIntent().getExtras();
        sp = PreferenceManager.getDefaultSharedPreferences(ProfileView.this);
        ed = sp.edit();
            ip = sp.getString("ip","");
            port = sp.getString("port","");
            username = sp.getString("username", "");
            password = sp.getString("password", "");
        if (isNetworkAvailable()) {
            new ProfileView.AsyncHttpTask().execute("http://" + ip + ":" + port + "/smartbin/login?user=" + username + "&passwd=" + password);
        } else {
            Toast.makeText(ProfileView.this, "Not Available", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {


            String result = "0";
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.e("d", url + "");
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(2 * 60 * 1000);

                int statusCode = urlConnection.getResponseCode();
                Log.e("d", statusCode + "");
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line).append("\n");
                    }

                    Log.e("d", response.toString() + "");
                    String res = response.toString();
                    parseResult(res);
                    result = "1"; // Successful
                } else {
                    result = "0"; // "Failed to fetch data!";
                }
            } catch (Exception e) {

                e.printStackTrace();
                result = "3";
            }
            return result; // "Failed to fetch data!";
        }

        @Override
        protected void onPreExecute() {

            pdialog = new ProgressDialog(ProfileView.this);
            pdialog.setMessage("Processing....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            name.setText(sname);
            address.setText(saddress);
            phone.setText(sphone);
            email.setText(semail);
            pdialog.dismiss();
        }
    }

    private void parseResult(String response_string) {


        JSONObject root = null;
        try {
            root = new JSONObject(response_string);
            JSONObject ja = root.getJSONObject("payLoad");
            sname = ja.getString("userName");
            saddress = ja.getString("address");
            sphone = ja.getString("mobileNo");
            semail = ja.getString("emailId");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
