package creations.empire.binmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    TextView name,place,number;
    Button task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = findViewById(R.id.button2);
        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        ed = sp.edit();
        name = findViewById(R.id.textView);
        place = findViewById(R.id.textView2);
        number = findViewById(R.id.textView4);
        name.setText(sp.getString("name","Driver Name"));
        place.setText(sp.getString("place","Driver place"));
        number.setText(sp.getString("number","Driver number"));
        Toast.makeText(this, ""+sp.getString("email_id","null"), Toast.LENGTH_SHORT).show();
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }
        });
    }
}
