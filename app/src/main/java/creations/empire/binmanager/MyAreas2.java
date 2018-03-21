package creations.empire.binmanager;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyAreas2 extends AppCompatActivity {


    Bundle bundle;
    RecyclerView my_area;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog pdialog;
    String ip, port, mailid;
    List<AreaInfo> placeDetail;
    RecyclerViewAdapter2 adapter;
    int i;
    String binid,simno,gaurdno,address,clrtime,status;
    Long simnos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_areas2);
        my_area = findViewById(R.id.view_recycler);
        my_area.setLayoutManager(new GridLayoutManager(this, 1));
        bundle = getIntent().getExtras();
        sp = PreferenceManager.getDefaultSharedPreferences(MyAreas2.this);
        ed = sp.edit();
        placeDetail = new ArrayList<>();
        ip = sp.getString("ip", "0");
        port = sp.getString("port", "0000");
        mailid = sp.getString("username", "null");
        i = bundle.getInt("position");
        new MyAreas2.AsyncHttpTask().execute("http://" + ip + ":" + port + "/smartbin/all-areas?driver-id=" + mailid + "&status=0");

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

            pdialog = new ProgressDialog(MyAreas2.this);
            pdialog.setMessage("Processing....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
            adapter = new RecyclerViewAdapter2(getApplicationContext(),placeDetail);
            if(placeDetail.isEmpty()) Toast.makeText(getApplicationContext(), "No Bins", Toast.LENGTH_SHORT).show();
            else
            my_area.setAdapter(adapter);
        }
    }

    private void parseResult(String response_string) {


        JSONObject root = null;
        try {
            root = new JSONObject(response_string);
            JSONArray jam = root.getJSONArray("returnList");
            int len2;
                JSONObject ja = jam.getJSONObject(i);
                JSONArray bin = ja.getJSONArray("tsbinInfos");
                len2 = bin.length();
                Log.e("d", len2 + "ee");
                for (int j = 0; j < len2; j++) {
                    JSONObject lam = bin.getJSONObject(j);
                    binid = lam.getString("sbinId");
                    simno = (lam.getString("simNo"));
                    gaurdno = lam.getString("guardianNo");
                    address = lam.getString("address");
                    clrtime = lam.getString("lastClearTime");
                    status = lam.getString("sbinStatus");
                    AreaInfo detail = new AreaInfo(binid, simno, gaurdno, address, clrtime, status);
                    placeDetail.add(detail);
                    Log.e("d",detail.toString());
                }

            Log.e("d", placeDetail + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}