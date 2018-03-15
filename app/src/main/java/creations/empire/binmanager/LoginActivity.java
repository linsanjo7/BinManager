package creations.empire.binmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {


    private static final int REQUEST_READ_CONTACTS = 0;


    private static final String[] DUMMY_CREDENTIALS = new String[]{"foo@example.com:hello", "bar@example.com:world"};

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    ProgressDialog pdialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Bundle bundle;
    String ip,port,web_response,username,password,resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        ed = sp.edit();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            ip = bundle.getString("ip");
            port = bundle.getString("port");
        }
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmailView.getText().toString().equals("") ||mPasswordView.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
                else {
                    username = mEmailView.getText().toString();
                    password = mPasswordView.getText().toString();
                    if (isNetworkAvailable()) {
                        String req = "http://"+ip+":"+port+"/smartbin/login?user="+username+"&passwd="+password;
                        Toast.makeText(LoginActivity.this, req, Toast.LENGTH_SHORT).show();
                        new AsyncHttpTask().execute("http://"+ip+":"+port+"/smartbin/login?user="+username+"&passwd="+password);
                    } else {
                        Toast.makeText(LoginActivity.this, "Not Available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
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

            pdialog = new ProgressDialog(LoginActivity.this);
            pdialog.setMessage("Processing....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected void onPostExecute (String s){
            super.onPostExecute(s);
            pdialog.dismiss();
            //if(resp.equals("sucess")){
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                Log.e("d","post execute successful");
                startActivity(i);
          //  }
          //  else
            //    Toast.makeText(LoginActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();

        }
    }
    private void parseResult(String response_string) {


        JSONObject root = null;
        try {
            root = new JSONObject(response_string);
            JSONObject ja = root.getJSONObject("payLoad");
            ed.putString("name",ja.getString("userName"));
            ed.putString("place",ja.getString("address"));
            ed.putString("number",ja.getString("mobileNo"));
            ed.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

