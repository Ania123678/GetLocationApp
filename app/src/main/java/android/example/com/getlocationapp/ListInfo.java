package android.example.com.getlocationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBase mydb;

    ArrayList<String> id, longtitude, latitude, altitude, time, speed, accuracy;
    LocAdapter locAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_list);


        recyclerView = recyclerView.findViewById(R.id.recyclerView);

        mydb = new DataBase(ListInfo.this);
        id = new ArrayList<>();
        longtitude = new ArrayList<>();
        latitude = new ArrayList<>();
        altitude = new ArrayList<>();
        speed = new ArrayList<>();
        time = new ArrayList<>();
        speed = new ArrayList<>();
        accuracy = new ArrayList<>();


        informationInArrays();
        locAdapter = new LocAdapter(ListInfo.this, id, longtitude, latitude, altitude, time, speed, accuracy);
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
                longtitude.add(cursor.getString(1));
                latitude.add(cursor.getString(2));
                speed.add(cursor.getString(3));
                altitude.add(cursor.getString(4));
                speed.add(cursor.getString(5));
                time.add(cursor.getString(6));
                speed.add(cursor.getString(7));
                accuracy.add(cursor.getString(8));
            }
        }
    }
}
