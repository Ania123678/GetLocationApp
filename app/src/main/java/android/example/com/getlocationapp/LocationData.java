package android.example.com.getlocationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LocationData extends AppCompatActivity {

//    FloatingActionButton buttonShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_data);

        //  ----------------------- przycisk do wyswietlenia danych lokalizacji   ----------------------- //
        FloatingActionButton buttonShow = findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(this::onClick);
        //  ---------------------------------------------- //

    }

    public void onClick(View w){
        if(w.getId()==R.id.buttonShow){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new CurrentLocationInfo()).commit();
        }
    }

}
