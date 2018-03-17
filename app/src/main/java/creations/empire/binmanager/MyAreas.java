package creations.empire.binmanager;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAreas extends AppCompatActivity {
    ListView my_area;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog pdialog;
    String ip,port,mailid;
    List<String> placeList;
    Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areas_layout);
        my_area = findViewById(R.id.my_areas_list);
        sp = PreferenceManager.getDefaultSharedPreferences(MyAreas.this);
        ed = sp.edit();
        placeList = new ArrayList<>();
        ip = sp.getString("ip", "0");
        port = sp.getString("port", "0000");
        mailid = sp.getString("email_id", "null");
        new MyAreas.AsyncHttpTask().execute("http://" + ip + ":" + port + "/smartbin/all-areas?driver-id=" + mailid + "&status=0");
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

            pdialog = new ProgressDialog(MyAreas.this);
            pdialog.setMessage("Processing....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
            adapter = new ArrayAdapter(MyAreas.this, android.R.layout.simple_list_item_1, placeList);
            my_area.setAdapter((ListAdapter) adapter);
        }
    }

    private void parseResult(String response_string) {


        JSONObject root = null;
        try {
            root = new JSONObject(response_string);
            JSONArray jam = root.getJSONArray("returnList");
            int len = jam.length();
            Log.e("d", len + "");
            for (int i = 0; i < len; i++) {
                JSONObject ja = jam.getJSONObject(i);
                placeList.add(ja.getString("areaName"));
            }
            Log.e("d", placeList + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
