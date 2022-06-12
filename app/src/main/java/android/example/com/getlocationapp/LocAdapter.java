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

    private Context context;
    private ArrayList id, longitude, latitude, altitude, time, speed, accuracy;

    LocAdapter(Context context,
                  ArrayList id,
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
        holder.textID.setText(String.valueOf(id.get(position)));
        holder.textLongitude.setText("Longitude: " + String.valueOf(longitude.get(position)));
        holder.textLatitude.setText("Latitude: " + String.valueOf(latitude.get(position)));
        holder.textAltitude.setText("Altitude: " +String.valueOf(altitude.get(position)));
        holder.textTime.setText("Time: " + String.valueOf(time.get(position)));
        holder.textSpeed.setText("Speed: " + String.valueOf(speed.get(position)));
        holder.textAccuracy.setText("Accuracy: " + String.valueOf(accuracy.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textID, textLongitude, textLatitude, textAltitude, textTime, textSpeed, textAccuracy;

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
