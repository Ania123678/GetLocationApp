package android.example.com.getlocationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListInfo {

    RecyclerView recyclerView;
    DataBase mydb;
    ArrayList<String> id, connection, ip, speed, rssi, mac, ssid, bssid, frequency, distance;
    LocAdapter locAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recyclerView = recyclerView.findViewById(recyclerView);

        mydb = new DataBase(ListInfo.this);
        id = new ArrayList<>();
        connection = new ArrayList<>();
        ip = new ArrayList<>();
        speed = new ArrayList<>();
        rssi = new ArrayList<>();
        mac = new ArrayList<>();
        ssid = new ArrayList<>();
        bssid = new ArrayList<>();
        frequency = new ArrayList<>();
        distance =  new ArrayList<>();

        informationInArrays();
        LocAdapter = new LocAdapter(ListInfo.this, id, connection, ip, speed, rssi, mac, ssid, bssid, frequency, distance);
        recyclerView.setAdapter(locAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((ListInfo.this)));


    }

    void informationInArrays(){
        Cursor cursor = mydb.listData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Brak Skanów", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                connection.add(cursor.getString(1));
                ip.add(cursor.getString(2));
                speed.add(cursor.getString(3));
                rssi.add(cursor.getString(4));
                mac.add(cursor.getString(5));
                ssid.add(cursor.getString(6));
                bssid.add(cursor.getString(7));
                frequency.add(cursor.getString(8));
                distance.add(cursor.getString(9));
            }
        }
    }
}
