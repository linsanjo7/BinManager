package creations.empire.binmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings_activity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
