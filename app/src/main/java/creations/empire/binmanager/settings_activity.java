package creations.empire.binmanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class settings_activity extends AppCompatActivity{
    private Button button;
    EditText ipaddress,portnumber;
    String temp_ip,temp_port;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        button = findViewById(R.id.button3);
        ipaddress = findViewById(R.id.editText3);
        portnumber = findViewById(R.id.editText4);
        if(ipaddress.getText().toString().equals("") || portnumber.getText().toString().equals("") ) {
            try {
                FileInputStream fin1 = openFileInput("ip_file");
                int c;
                temp_ip="";
                while( (c = fin1.read()) != -1){
                    temp_ip = temp_ip + Character.toString((char)c);
                }
                fin1.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ipaddress.setText(temp_ip);
            try {
                FileInputStream fin2 = openFileInput("port_file");
                int c;
                temp_port="";
                while( (c = fin2.read()) != -1){
                    temp_port = temp_port + Character.toString((char)c);
                }
                fin2.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            portnumber.setText(temp_port);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings_activity.this, LoginActivity.class);
                if(ipaddress.getText().toString().equals("") || portnumber.getText().toString().equals("") ) {
                    Toast.makeText(settings_activity.this, "Enter IP and Port Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        FileOutputStream fOut1 = openFileOutput("ip_file", Context.MODE_PRIVATE);
                        String str1 = ipaddress.getText().toString();
                        fOut1.write(str1.getBytes());
                        fOut1.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileOutputStream fOut2 = openFileOutput("port_file", Context.MODE_PRIVATE);
                        String str2 = portnumber.getText().toString();
                        fOut2.write(str2.getBytes());
                        fOut2.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i.putExtra("ip", ipaddress.getText().toString());
                    i.putExtra("port", portnumber.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
