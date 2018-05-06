package com.example.isvirin.weatherapp.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isvirin.weatherapp.R;
import com.example.isvirin.weatherapp.data.model.BriefWeather;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.isvirin.weatherapp.service.RequestService.WEATHER;

public class RequestFragment extends Fragment {
    private TextView textViewResponse;
    private TextView textViewTemp;
    private TextView textViewPressure;
    private TextView textViewHumidity;
    private TextView textViewTempMin;
    private TextView textViewTempMax;
    private String url = "http://openweathermap.org/data/2.5/weather?q=london&appid=b6907d289e10d714a6e88b30761fae22";
    private String response;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response = getArguments().getString(WEATHER);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewResponse = view.findViewById(R.id.textViewResponse);
        textViewTemp = view.findViewById(R.id.textViewTemp);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        textViewHumidity = view.findViewById(R.id.textViewHumidity);
        textViewTempMin = view.findViewById(R.id.textViewTempMin);
        textViewTempMax = view.findViewById(R.id.textViewTempMax);
//        new RequestTask().execute(url);

        BriefWeather briefWeather = getWeather(response);
        textViewTemp.setText(String.valueOf(briefWeather.getTemp()));
        textViewPressure.setText(String.valueOf(briefWeather.getPressure()));
        textViewHumidity.setText(String.valueOf(briefWeather.getHumidity()));
        textViewTempMin.setText(String.valueOf(briefWeather.getTempMin()));
        textViewTempMax.setText(String.valueOf(briefWeather.getTempMax()));

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
