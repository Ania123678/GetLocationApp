package android.example.com.getlocationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_FINE_LOCATION = 1;
    public static final int DEFAULT_UPDATE_VALUE = 30;
    public static final int FAST_UPDATE_VALUE = 50;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    DataBase myDB;

    private TextView textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy;
    String s1 = "Altitude information is not available";
    String s2 = "Speed information is not available";

    Button buttonPermission;
    Button buttonShow;
    Button buttonSave;
    Button buttonShowList;

    boolean updateOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


        Button buttonPermission  = findViewById(R.id.buttonPermission);
        buttonPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestLocationPermission();
                }
            }
        });

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //-----------------------------dane-----------------------------//
        String longitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        String altitude = String.valueOf(location.getAltitude());
        String time = String.valueOf(location.getTime());
        String speed = String.valueOf(location.getSpeed());
        String accuracy = String.valueOf(location.getAccuracy());

        //-------------------------------------------------------------//

        Button buttonShow  = findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dlugosc
                TextView textLongitude = (TextView) findViewById(R.id.longitude);
                textLongitude.setText(longitude);

                //szerokosc
                TextView textLatitude = (TextView) findViewById(R.id.latitude);
                textLatitude.setText(latitude);

                //wysokosc
                TextView textAltitude = (TextView) findViewById(R.id.altitude);
                if(location.hasAltitude()){
                    textAltitude.setText(altitude);
                }
                else{
                    textAltitude.setText(s1);
                }

                //czas
                TextView textTime = (TextView) findViewById(R.id.time);
                textTime.setText(time);

                //predkosc
                TextView textSpeed = (TextView) findViewById(R.id.speed);
                if(location.hasSpeed()){
                    textAltitude.setText(speed);
                }
                else{
                    textAltitude.setText(s2);
                }
                //dokladnosc
                TextView textAccuracy = (TextView) findViewById(R.id.accuracy);
                textAccuracy.setText(accuracy);

            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase myDB = new DataBase(MainActivity.this);
                myDB.addInfo(longitude, latitude, altitude, time, speed, accuracy);
            }
        });

        Button buttonShowList = findViewById(R.id.buttonShowList);
        buttonShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openListInfo();
            }
        });
    }

    public void openListInfo(){
        Intent intent = new Intent(this, ListInfo.class);
        startActivity(intent);
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for WIFI access")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_FINE_LOCATION ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch(requestCode){
//            case PERMISSION_FINE_LOCATION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    updateGPS();
//
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "This permission is needed for location access", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//        }
//    }

//    private void updateGPS(){
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>(){
//                @Override
//                public void onSuccess(Location location){
//                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        else{
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
//            }
//        }
//    }

//    private void updateLocationValues(Location location){
//
//        //dlugosc
//        textLongitude.setText(String.valueOf(location.getLongitude()));
//
//        //szerokosc
//        textLatitude.setText(String.valueOf(location.getLatitude()));
//
//        //wysokosc
//        if(location.hasAltitude()){
//            textAltitude.setText(String.valueOf(location.getAltitude()));
//        }
//        else{
//            textAltitude.setText(s1);
//        }
//
//        //czas
//        textTime.setText(String.valueOf(location.getTime()));
//
//        //predkosc
//        if(location.hasSpeed()){
//            textAltitude.setText(String.valueOf(location.getSpeed()));
//        }
//        else{
//            textAltitude.setText(s2);
//        }
//        //dokladnosc
//        textAccuracy.setText(String.valueOf(location.getAccuracy()));
//    }
}