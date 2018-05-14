package com.example.isvirin.weatherapp.view.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isvirin.weatherapp.R;
import com.example.isvirin.weatherapp.data.cache.DataBaseSQLite;
import com.example.isvirin.weatherapp.data.cache.FileManager;
import com.example.isvirin.weatherapp.data.cache.SharedPreferencesManager;
import com.example.isvirin.weatherapp.data.model.Location;
import com.example.isvirin.weatherapp.service.RequestService;
import com.example.isvirin.weatherapp.util.Util;
import com.example.isvirin.weatherapp.view.fragment.ForecastListFragment;
import com.example.isvirin.weatherapp.view.fragment.LocationListFragment;
import com.example.isvirin.weatherapp.service.LocationService;
import com.example.isvirin.weatherapp.view.fragment.RequestFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.isvirin.weatherapp.data.cache.DataBaseSQLite.COLUMN_COUNTRY;
import static com.example.isvirin.weatherapp.data.cache.DataBaseSQLite.COLUMN_NAME;
import static com.example.isvirin.weatherapp.data.cache.SharedPreferencesManager.USER_LOCATION;
import static com.example.isvirin.weatherapp.service.LocationService.GEO_INFO;
import static com.example.isvirin.weatherapp.service.LocationService.LOCATION;
import static com.example.isvirin.weatherapp.service.RequestService.WEATHER;
import static com.example.isvirin.weatherapp.service.RequestService.WEATHER_REQUEST;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imageView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Util.showOurDialog(this);

//        CustomDialog customDialog = new CustomDialog(this);
//        customDialog.show();

        imageView = findViewById(R.id.image_view);

//        showNotification();
//        testFile();

//        testDB();
//        readDataWithCP();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        defineLocation();
    }

    private void readDataWithCP() {
        ArrayList<Location> locations = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.example.isvirin.weatherapp.data.DBContentProvider/items"), null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                locations.add(new Location(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void testDB() {
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(this);
        dataBaseSQLite.open();
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataBaseSQLite.write(new Location("name" + i, "country" + i));
        }

//        List<Location> locations = Collections.emptyList();
//        locations = dataBaseSQLite.get();
        dataBaseSQLite.close();
    }

    private void testFile() {
        FileManager fm = new FileManager(this);
        fm.saveString("test string");
        String s = fm.readString("file.txt");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GEO_INFO));
        registerReceiver(broadcastReceiverWeather, new IntentFilter(WEATHER_REQUEST));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverWeather);
    }

    private void defineLocation() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
//
//        Intent intent2 = new Intent(this, StartActivity.class);
//        startActivity(intent2);
//        finish();
//
//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent1, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.locations) {
            Fragment f = new LocationListFragment();
            Bundle b = new Bundle();
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new LocationListFragment()).commit();
        } else if (id == R.id.forecast) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ForecastListFragment()).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            System.out.println();
        } else if (id == R.id.nav_send) {
            System.out.println();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String location = intent.getStringExtra(LOCATION);
            SharedPreferencesManager preferencesManager = new SharedPreferencesManager(MainActivity.this);
            preferencesManager.saveString(USER_LOCATION, location);

            Util.showOurDialog(context, "Ваш город - " + location + "?");
            }
    };

    BroadcastReceiver broadcastReceiverWeather = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String string = intent.getStringExtra(WEATHER);
            RequestFragment requestFragment = new RequestFragment();
            Bundle bundle = new Bundle();
            bundle.putString(WEATHER, string);
            requestFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, requestFragment).commit();
        }
    };
}
