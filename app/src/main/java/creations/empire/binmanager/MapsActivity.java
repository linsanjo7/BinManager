package creations.empire.binmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button navigate,mark;
    Bundle bundle;
    TextView placeName,binId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        navigate = findViewById(R.id.button8);
        bundle = getIntent().getExtras();
        mark = findViewById(R.id.button9);
        placeName = findViewById(R.id.textView5);
        binId = findViewById(R.id.textView7);
        placeName.setText(bundle.getString("location"));
        binId.setText(bundle.getString("binId"));
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+bundle.getDouble("latitude")+","+bundle.getDouble("longitude"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng tvm = new LatLng(bundle.getDouble("latitude"),bundle.getDouble("longitude"));
        mMap.addMarker(new MarkerOptions().position(tvm).title("Full Bin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tvm));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tvm, (float) 10.0));
    }

}
