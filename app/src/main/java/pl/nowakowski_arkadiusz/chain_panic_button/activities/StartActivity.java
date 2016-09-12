package pl.nowakowski_arkadiusz.chain_panic_button.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.R;
import pl.nowakowski_arkadiusz.chain_panic_button.dao.ChainLinkDAO;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;

/**
 * StartActivity is the main activity, it shows the timer, which starts the sending.
 */
public class StartActivity extends AppCompatActivity implements LocationListener {

    private final int MY_PERMISSIONS_REQUEST = 1;

    private List<ChainLink> chainLinks;
    private LocationManager locationManager;
    private String provider;
    private double longitude;
    private double latitude;
    private TextView locationTextView;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        locationTextView = (TextView) findViewById(R.id.start_location);
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE
            }, MY_PERMISSIONS_REQUEST);
        } else {
            setLocationManager(false);

        }
    }

    private void setLocationManager(boolean startUpdate) {
        if (
            Build.VERSION.SDK_INT >= 23
            && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return  ;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }
        if (startUpdate) {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0) {
                    setLocationManager(true);
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChainLinkDAO dao = new ChainLinkDAO(this);
        chainLinks = dao.findAll();
        dao.close();
        if (locationManager != null) {
            if (
                Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                return  ;
            }
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            if (
                Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                return  ;
            }
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manage_chain:
                // Start the chain list activity
                startActivity(new Intent(this, AlarmChainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        locationTextView.setText(getResources().getString(R.string.location) + " (" + provider.toUpperCase() + "): "
            + (Math.round(latitude * 100.0) / 100.0) + ", " + (Math.round(longitude * 100.0) / 100.0));
        Toast.makeText(this, R.string.location_founded, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void runChainLinks(View view) {
        if (chainLinks.size() == 0) {
            Toast.makeText(this, R.string.no_chain_links, Toast.LENGTH_SHORT).show();
        } else {
            for (ChainLink chainLink : chainLinks) {
                try {
                    chainLink.run(this);
                } catch (Exception e) {
                }
            }
        }
    }
}
