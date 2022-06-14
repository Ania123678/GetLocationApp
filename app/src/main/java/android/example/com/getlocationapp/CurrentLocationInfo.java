package android.example.com.getlocationapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

public class CurrentLocationInfo extends Fragment implements LocationListener {

    private String mParam1;
    private String mParam2;

    public CurrentLocationInfo() {
        // Required empty public constructor
    }

    private View myView = null;

    public static final int PERMISSION_FINE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    LocationManager lm;

    Context context;
    DataBase myDB;

    private TextView textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy;

    Button buttonSave;
    boolean updateOn = false;

    ArrayList<Double> longitude_array = new ArrayList<>();
    ArrayList<Double> latitude_array = new ArrayList<>();
    int count = 0;

    String longitude;
    String latitude;
    String altitude;
    String time;
    String speed;
    String accuracy;


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.location_info, container, false);





        //----------------------- przycisk do zapisywania danych w bazie danych  ----------------------- //
//        Button buttonSave = myView.findViewById(R.id.buttonSave);
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DataBase myDB = new DataBase(CurrentLocationInfo.this);
//                myDB.addInfo(longitude, latitude, altitude, time, speed, accuracy);
//            }
//        });


        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return myView;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (null != myView) {
            TextView textLongitude = myView.findViewById(R.id.longitude);
            TextView textLatitude = myView.findViewById(R.id.latitude);
            TextView textAltitude = myView.findViewById(R.id.altitude);
            TextView textTime = myView.findViewById(R.id.time);
            TextView textSpeed = myView.findViewById(R.id.speed);
            TextView textAccuracy = myView.findViewById(R.id.accuracy);


            String longitude = String.valueOf(location.getLongitude());
            String latitude = String.valueOf(location.getLatitude());
            String altitude = String.valueOf(location.getAltitude());
            String time = String.valueOf(location.getTime());
            String speed = String.valueOf(location.getSpeed());
            String accuracy = String.valueOf(location.getAccuracy());


            textLongitude.setText(longitude);
            textLatitude.setText(latitude);
            textAltitude.setText(altitude);
            textTime.setText(time);
            textSpeed.setText(speed);
            textAccuracy.setText(accuracy);
        }
    }


    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
