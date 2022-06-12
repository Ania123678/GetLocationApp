package android.example.com.getlocationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_FINE_LOCATION = 1;
    public static final int DEFAULT_UPDATE_VALUE = 30;
    public static final int FAST_UPDATE_VALUE = 50;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    private TextView textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy;
    String s1 = "Altitude information is not available";
    String s2 = "Speed information is not available";

    boolean updateOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        TextView textLongitude = (TextView) findViewById(R.id.longtitude);
        TextView textLatitude = (TextView) findViewById(R.id.latitude);
        TextView textAltitude = (TextView) findViewById(R.id.altitude);
        TextView textTime = (TextView) findViewById(R.id.time);
        TextView textSpeed = (TextView) findViewById(R.id.speed);
        TextView textAccuracy = (TextView) findViewById(R.id.accuracy);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_VALUE);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_VALUE);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }
                else{
                    Toast.makeText(MainActivity.this, "This permission is needed for location access", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void updateGPS(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>(){
                @Override
                public void onSuccess(Location location){
                    updateLocationValues(location);
                }
            });
        }

        else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void updateLocationValues(Location location){

        //dlugosc
        double longtitude = location.getLongitude();
        textAccuracy.setText(String.valueOf(longtitude));

        //szerokosc
        double latitude = location.getLatitude();
        textLatitude.setText(String.valueOf(latitude));

        //wysokosc
        if(location.hasAltitude()){
            double altitude = location.getAltitude();
            textAltitude.setText(String.valueOf(altitude));
        }
        else{
            textAltitude.setText(s1);
        }

        //czas
        double time = location.getTime();
        textTime.setText(String.valueOf(time));

        //predkosc
        if(location.hasSpeed()){
            double speed = location.getSpeed();
            textAltitude.setText(String.valueOf(speed));
        }
        else{
            textAltitude.setText(s2);
        }
        //dokladnosc
        double accuracy = location.getAccuracy();
        textAccuracy.setText(String.valueOf(accuracy));
    }
}