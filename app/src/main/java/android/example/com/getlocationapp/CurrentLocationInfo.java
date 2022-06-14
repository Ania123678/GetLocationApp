package android.example.com.getlocationapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;


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

    private TextView textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy, mean1, mean2;

    Button buttonSave;
    boolean updateOn = false;

    String longitude;
    String latitude;
    String altitude;
    String time;
    String speed;
    String accuracy;

    int counter = 0;
    ArrayList<Double> array1 = new ArrayList<>(5);
    ArrayList<Double> array2 = new ArrayList<>(5);


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.location_info, container, false);

       // ----------------------- przycisk do zapisywania danych w bazie danych  ----------------------- //


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


            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            String altitude = String.valueOf(location.getAltitude());
            String time = String.valueOf(location.getTime());
            String speed = String.valueOf(location.getSpeed());
            String accuracy = String.valueOf(location.getAccuracy());
            double mean1 = 0;
            double mean2 = 0;

            textLongitude.setText(String.valueOf(longitude));
            textLatitude.setText(String.valueOf(latitude));
            textAltitude.setText(altitude);
            textTime.setText(time);
            textSpeed.setText(speed);
            textAccuracy.setText(accuracy);

            Button buttonSave = myView.findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String longitude = String.valueOf(location.getLongitude());
                    String latitude = String.valueOf(location.getLatitude());
                    myDB = DataBase.getInstance(getActivity());
                    myDB.addInfo(longitude, latitude, altitude, time, speed, accuracy);
                }
            });


            if(counter <= 4){
                array1.add(longitude);
                array2.add(latitude);
                Log.d("tag1","dodawanie do listy");
            }
            else{
                Log.d("tag2","liczenie sumy");
                double sum1 = 0;
                double sum2 = 0;
                if(!array1.isEmpty() && !array2.isEmpty()) {
                    for (double l1 : array1) {
                        sum1 += l1;
                    }
                    TextView textmean1 = myView.findViewById(R.id.mean1);
                    mean1  = sum1 / array1.size();
                    textmean1.setText(String.valueOf(mean1));

                    for (double l2 : array2) {
                        sum2 += l2 ;
                    }
                    TextView textmean2 = myView.findViewById(R.id.mean2);
                    mean2  = sum2 / array2.size();
                    textmean2.setText(String.valueOf(mean2));
                }
                counter = 0;
                array1 = new ArrayList<>();
                array2 = new ArrayList<>();
                Log.d("tag2","koniec liczenia sumy");
            }
            counter++;
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
