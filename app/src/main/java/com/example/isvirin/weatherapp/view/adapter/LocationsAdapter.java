package com.example.isvirin.weatherapp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isvirin.weatherapp.R;
import com.example.isvirin.weatherapp.data.model.Location;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Location> locations = new ArrayList<>();

    public LocationsAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LocationViewHolder holder1 = ((LocationViewHolder) holder);
        holder1.tvLocationName.setText(locations.get(position).getName());
        holder1.tvCountryName.setText(locations.get(position).getCountry());
        holder1.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationName;
        TextView tvCountryName;
        public LocationViewHolder(View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.text_view_location_name);
            tvCountryName = itemView.findViewById(R.id.text_view_coutry_name);
        }
    }
}
