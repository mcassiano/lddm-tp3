package me.cassiano.lddm_tp3;

import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

import java.util.List;

import me.cassiano.lddm_tp3.adapters.ShakeListViewAdapter;
import me.cassiano.lddm_tp3.models.Shake;


public class MainActivity extends ActionBarActivity
        implements ShakeDetector.Listener, LocationListener {

    ShakeDetector sd;
    SensorManager sm;

    ShakeListViewAdapter shakeListViewAdapter;

    LocationManager locationManager;
    String provider;

    Double currentLatitude;
    Double currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Criteria criteria = new Criteria();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null)
            onLocationChanged(location);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);
        sd.start(sm);

        setContentView(R.layout.activity_main);

        shakeListViewAdapter = new ShakeListViewAdapter();

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(shakeListViewAdapter);

        List<Shake> shakes = Shake.listAll(Shake.class);
        shakeListViewAdapter.addItems(shakes);

    }

    public void hearShake() {
        Toast.makeText(this, R.string.txt_shaken, Toast.LENGTH_SHORT).show();
        Shake shake = new Shake(currentLatitude, currentLongitude);
        shakeListViewAdapter.addItem(shake);
        shake.save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        sd.start(sm);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        sd.stop();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }

}
