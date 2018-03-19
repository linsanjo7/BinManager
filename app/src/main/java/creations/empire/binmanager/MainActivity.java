package creations.empire.binmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    TextView name,place,number;
    Button task,areas,profile,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = findViewById(R.id.button2);
        areas = findViewById(R.id.button6);
        profile = findViewById(R.id.button4);
        history = findViewById(R.id.button7);
        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        ed = sp.edit();
        name = findViewById(R.id.textView);
        place = findViewById(R.id.textView2);
        number = findViewById(R.id.textView4);
        name.setText(sp.getString("name","Driver Name"));
        place.setText(sp.getString("place","Driver place"));
        number.setText(sp.getString("number","Driver number"));
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });
        areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this,MyAreas.class);
                startActivity(j);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this,ProfileView.class);
                startActivity(k);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(MainActivity.this,HistoryView.class);
                startActivity(l);
            }
        });
    }

}
