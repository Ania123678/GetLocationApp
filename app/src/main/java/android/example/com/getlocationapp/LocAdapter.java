package android.example.com.getlocationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class LocAdapter extends RecyclerView.Adapter<LocAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> id, longitude, latitude, altitude, time, speed, accuracy;

    LocAdapter(Context context,
                  ArrayList longitude,
                  ArrayList latitude,
                  ArrayList altitude,
                  ArrayList time,
                  ArrayList speed,
                  ArrayList accuracy) {
        this.context = context;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.time = time;
        this.speed = speed;
        this.accuracy = accuracy;

    }

    @NonNull
    @Override
    public LocAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocAdapter.MyViewHolder holder, int position) {
        holder.textid.setText(String.valueOf(id.get(position)));
        holder.textLongitude.setText("Connection: " + String.valueOf(longitude.get(position)));
        holder.textLatitude.setText("IP: " + String.valueOf(latitude.get(position)));
        holder.textAltitude.setText("Speed: " +String.valueOf(altitude.get(position)));
        holder.textTime.setText("RSSI: " + String.valueOf(time.get(position)));
        holder.textSpeed.setText("MAC: " + String.valueOf(speed.get(position)));
        holder.textAccuracy.setText("SSID: " + String.valueOf(accuracy.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textID, textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textLongitude = itemView.findViewById(R.id.textLongitude);
            textLatitude = itemView.findViewById(R.id.textLatitude);
            textAltitude = itemView.findViewById(R.id.textAltitude);
            textTime = itemView.findViewById(R.id.textTime);
            textSpeed = itemView.findViewById(R.id.textSpeed);
            textAccuracy= itemView.findViewById(R.id.textAccuracy);
        }
    }
}
