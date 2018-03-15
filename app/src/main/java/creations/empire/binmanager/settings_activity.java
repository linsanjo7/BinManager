package creations.empire.binmanager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class settings_activity extends AppCompatActivity{
    private Button button;
    EditText ipaddress,portnumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        button = findViewById(R.id.button3);
        ipaddress = findViewById(R.id.editText3);
        portnumber = findViewById(R.id.editText4);
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        if(connectivityManager.getActiveNetworkInfo().isConnected())
        {
            Toast.makeText(this, "Connection Active", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings_activity.this, LoginActivity.class);
                if(ipaddress.getText().toString().equals("") || portnumber.getText().toString().equals("") )
                    Toast.makeText(settings_activity.this, "Enter IP and Port Number", Toast.LENGTH_SHORT).show();
                else {
                    i.putExtra("ip", ipaddress.getText().toString());
                    i.putExtra("port", portnumber.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
