package com.example.isvirin.weatherapp.view.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.FutureTarget;
import com.example.isvirin.weatherapp.R;
import com.example.isvirin.weatherapp.data.model.BriefWeather;
import com.example.isvirin.weatherapp.data.model.currentweather.CurrentWeather;
import com.example.isvirin.weatherapp.data.model.currentweather.Main;
import com.example.isvirin.weatherapp.service.RestInterface;
import com.example.isvirin.weatherapp.util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.isvirin.weatherapp.service.RequestService.WEATHER;

public class RequestFragment extends Fragment {
    @BindView(R.id.textViewResponse)
    TextView textViewResponse;
    @BindView(R.id.textViewTemp)
    TextView textViewTemp;
    @BindView(R.id.textViewPressure)
    TextView textViewPressure;
    @BindView(R.id.textViewHumidity)
    TextView textViewHumidity;
    @BindView(R.id.textViewTempMin)
    TextView textViewTempMin;
    @BindView(R.id.textViewTempMax)
    TextView textViewTempMax;
    @BindView(R.id.imageViewIcon)
    ImageView imageViewIcon;
    private String url = "http://openweathermap.org/data/2.5/weather?q=london&appid=b6907d289e10d714a6e88b30761fae22";
    private String response;
    public static final String CITY_NAME = "CITY_NAME";
    private String city;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response = getArguments().getString(WEATHER);
        city = getArguments().getString(CITY_NAME);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        new RequestTask().execute(url);

//        BriefWeather briefWeather = getWeather(response);
//        textViewTemp.setText(String.valueOf(briefWeather.getTemp()));
//        textViewPressure.setText(String.valueOf(briefWeather.getPressure()));
//        textViewHumidity.setText(String.valueOf(briefWeather.getHumidity()));
//        textViewTempMin.setText(String.valueOf(briefWeather.getTempMin()));
//        textViewTempMax.setText(String.valueOf(briefWeather.getTempMax()));

//        Picasso.get()
//                .load("http://openweathermap.org/img/w/10d.png")
//                .resize(200, 200)
//                .into(imageViewIcon);

//        Glide.with(getContext())
//                .load("http://openweathermap.org/img/w/10d.png")
////                .override(200, 200)
//                .into(imageViewIcon);

//        Bitmap bitmap = null;
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Drawable drawable = null;
//                FutureTarget<Drawable> futureTarget = Glide.with(getContext())
//                        .load("http://openweathermap.org/img/w/10d.png")
//                        .submit(200, 200);
//                try {
//                    drawable = futureTarget.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                int o = drawable.getMinimumWidth();
//
//            }
//        }).start();
//        int i = bitmap.getByteCount();
        callRetrofit();
    }


    private void callRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestInterface restInterface = retrofit.create(RestInterface.class);

        restInterface.getWeatherReport(city, Util.appId).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                Main main = response.body().getMain();
                textViewTemp.setText(String.valueOf(main.getTemp()));
                textViewPressure.setText(String.valueOf(main.getPressure()));
                textViewHumidity.setText(String.valueOf(main.getHumidity()));
                textViewTempMin.setText(String.valueOf(main.getTempMin()));
                textViewTempMax.setText(String.valueOf(main.getTempMax()));

                Glide.with(getContext())
                        .load("http://openweathermap.org/img/w/" + response.body().getWeather().get(0).getIcon() + ".png")
//                .override(200, 200)
                        .into(imageViewIcon);
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                t.getMessage();
            }
        });
    }


    class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                response = getResponse(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            textViewResponse.setText(s);
            BriefWeather briefWeather = getWeather(s);
            textViewTemp.setText(String.valueOf(briefWeather.getTemp()));
            textViewPressure.setText(String.valueOf(briefWeather.getPressure()));
            textViewHumidity.setText(String.valueOf(briefWeather.getHumidity()));
            textViewTempMin.setText(String.valueOf(briefWeather.getTempMin()));
            textViewTempMax.setText(String.valueOf(briefWeather.getTempMax()));
        }

        private String getResponse(String path) throws IOException {
            BufferedReader bf;
            StringBuilder stringBuilder = null;
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.connect();
                bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bf.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

    }

    private BriefWeather getWeather(String response) {
        BriefWeather briefWeather = new BriefWeather();
        String s = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            s = jsonObject.get("main").toString();
//            jsonObject = new JSONObject(s);
//            briefWeather.setHumidity(jsonObject.getLong("humidity"));
//            briefWeather.setPressure(jsonObject.getLong("pressure"));
//            briefWeather.setTemp(jsonObject.getDouble("temp"));
//            briefWeather.setTempMax(jsonObject.getLong("temp_max"));
//            briefWeather.setTempMin(jsonObject.getLong("temp_min"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        briefWeather = g.fromJson(s, BriefWeather.class);

        return briefWeather;
    }

}
