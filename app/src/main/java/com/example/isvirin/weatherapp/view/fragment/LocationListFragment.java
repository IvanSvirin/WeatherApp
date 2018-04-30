package com.example.isvirin.weatherapp.view.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isvirin.weatherapp.data.model.Location;
import com.example.isvirin.weatherapp.view.adapter.LocationsAdapter;
import com.example.isvirin.weatherapp.R;

import java.util.ArrayList;

import static com.example.isvirin.weatherapp.data.cache.DataBaseSQLite.COLUMN_COUNTRY;
import static com.example.isvirin.weatherapp.data.cache.DataBaseSQLite.COLUMN_NAME;

public class LocationListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Location> locations = new ArrayList<>();
    private LocationsAdapter locationsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list_view_locations);
//        init();
        readDataWithCP();

        locationsAdapter = new LocationsAdapter(locations);
        recyclerView.setAdapter(locationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void init() {
        Location location;
        for (int i = 0; i < 20; i++) {
            location = new Location("name" + i, "country" + i);
            locations.add(location);
        }
    }

    private void readDataWithCP() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.example.isvirin.weatherapp.data.DBContentProvider/items"), null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                locations.add(new Location(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
