package creations.empire.binmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    Button button;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog pdialog;
    String ip,port,mailid;
    List<String> placeList;
    ListView list;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list = findViewById(R.id.listView);
        sp = PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);
        ed = sp.edit();
        ip = sp.getString("ip","0");
        port = sp.getString("port","0000");
        mailid = sp.getString("email_id","null");
        new Main2Activity.AsyncHttpTask().execute("http://"+ip+":"+port+"/smartbin/all-bins?driver-id="+mailid+"&status=0");
        placeList = new ArrayList<>();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Main2Activity.this,MapsActivity.class);
                i.putExtra("location",placeList.get(position));
                startActivity(i);
            }
        });
        button = findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Civil+Station,+Trivandrum+India");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {


            String result = "0";
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.e("d",url+"");
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(2 * 60 * 1000);

                int statusCode = urlConnection.getResponseCode();
                Log.e("d",statusCode+"");
                // 200 represents HTTP OK
                if (statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(
                                    urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line).append("\n");
                    }

                    Log.e("d",response.toString()+"");
                    String res = response.toString();
                    parseResult(res);
                    result = "1"; // Successful
                } else
                {
                    result = "0"; // "Failed to fetch data!";
                }
            }

            catch (Exception e) {

                e.printStackTrace();
                result = "3";
            }
            return result; // "Failed to fetch data!";
        }
        @Override
        protected void onPreExecute () {

            pdialog = new ProgressDialog(Main2Activity.this);
            pdialog.setMessage("Processing....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected void onPostExecute (String s){
            super.onPostExecute(s);
            pdialog.dismiss();
            adapter = new ArrayAdapter(Main2Activity.this,android.R.layout.simple_list_item_1,placeList);
            list.setAdapter((ListAdapter) adapter);
        }
    }
    private void parseResult(String response_string) {


        JSONObject root = null;
        try {
            root = new JSONObject(response_string);
            JSONArray jam = root.getJSONArray("returnList");
            int len = jam.length();
            Log.e("d",len+"");
            for(int i = 0;i<len;i++) {
                JSONObject ja = jam.getJSONObject(i);
                placeList.add(ja.getString("address"));
            }
            Log.e("d",placeList+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
