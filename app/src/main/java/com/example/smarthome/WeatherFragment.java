package com.example.smarthome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {


    TextView textViewTemperature;
    TextView textViewFeelLike;
    TextView textViewTempMin;
    TextView textViewTempMax;
    TextView textViewMain;
    TextView textViewDescription;
    TextView textViewPressure;
    TextView textViewHumidity;
    TextView textViewWindSpeed;
    TextView textViewWindRotation;
    TextView textViewSunrise;
    TextView textViewSunset;
    ProgressBar progressBar;


    String name;
    String mainStr;
    String description;
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    int pressure;
    int humidity;
    double speed;
    int deg;
    long sunrise;
    long sunset;

    double lat = 0, lon = 0;

    boolean hasDataLoaded = false;
    int fiveTimes = 5;
    private FusedLocationProviderClient fusedLocationClient;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getWeatherData();
    }


    void getWeatherData() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=76f5a39d4f2ad0b3982fae450c3ab0cb")
                        .get()
                        .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "76f5a39d4f2ad0b3982fae450c3ab0cb")
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if(response != null) {

                    try {
                        ResponseBody responseBody = response.body();

                        InputStream in = responseBody.byteStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(in);

                        StringBuilder data = new StringBuilder();
                        int inputStreamData = 0;
                        try {
                            inputStreamData = inputStreamReader.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        while (inputStreamData != -1) {
                            char current = (char) inputStreamData;
                            try {
                                inputStreamData = inputStreamReader.read();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            data.append(current);
                        }

                        JSONObject body = new JSONObject(data.toString());

                        name = body.getString("name");

                        JSONArray weather = body.getJSONArray("weather");

                        JSONObject jsonObject1 = weather.getJSONObject(0);

                        mainStr = jsonObject1.getString("main");
                        description = jsonObject1.getString("description");

                        JSONObject main = body.getJSONObject("main");

                        temp = main.getDouble("temp");
                        feels_like = main.getDouble("feels_like");
                        temp_min = main.getDouble("temp_min");
                        temp_max = main.getDouble("temp_max");
                        pressure = main.getInt("pressure");
                        humidity = main.getInt("humidity");

                        JSONObject wind = body.getJSONObject("wind");

                        speed = wind.getDouble("speed");
                        deg = wind.getInt("deg");

                        JSONObject sys = body.getJSONObject("sys");

                        sunrise = sys.getLong("sunrise");
                        sunset = sys.getLong("sunset");

                        Log.i("abcde", "" + temp + " " + sunrise);


                        if (fiveTimes > 0) {
                            // Refresh the fragment
                            Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.detach(frg).attach(frg).commit();
                            fiveTimes--;
                        }
                        Log.i("abcde", data.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        thread.start();
    }

    String kelvinToCelsius(double kelvin) {

        DecimalFormat df = new DecimalFormat("#.0");
        return df.format((kelvin - 273.15));
    }

    String EpochTimeToLocalTime(long epoch){
        Date date = new Date(epoch);
        String time = date.getHours()+":"+date.getMinutes();
        return time;
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }  else {
                    Toast.makeText(getContext(),"Access Denied - Cannot access location", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            getLocation();

            textViewTemperature = view.findViewById(R.id.todayTemperature);
            textViewFeelLike = view.findViewById(R.id.todayTemperatureFeelLike);
            textViewTempMin = view.findViewById(R.id.todayTemperatureMin);
            textViewTempMax = view.findViewById(R.id.todayTemperatureMax);
            textViewMain = view.findViewById(R.id.todayMain);
            textViewDescription = view.findViewById(R.id.todayDescription);
            textViewPressure = view.findViewById(R.id.todayPressure);
            textViewHumidity = view.findViewById(R.id.todayHumidity);
            textViewWindSpeed = view.findViewById(R.id.todayWindSpeed);
            textViewWindRotation = view.findViewById(R.id.todayWindRotation);
            textViewSunrise = view.findViewById(R.id.todaySunrise);
            textViewSunset = view.findViewById(R.id.todaySunset);
            progressBar = view.findViewById(R.id.progressBarWeather);

            getWeatherData();

            if(fiveTimes == 0){
                progressBar.setVisibility(View.GONE);
            }

            textViewTemperature.setText(kelvinToCelsius(temp) + "°C");
            textViewFeelLike.setText("Feel like: " + kelvinToCelsius(feels_like) + "°C");
            textViewTempMin.setText("Temp Min: " + kelvinToCelsius(temp_min) + "°C");
            textViewTempMax.setText("Temp Max: " + kelvinToCelsius(temp_max) + "°C");
            textViewMain.setText("Main: " + mainStr);
            textViewDescription.setText("Description: " + description);
            textViewPressure.setText("Pressure: " + pressure + " hpa");
            textViewHumidity.setText("Humidity: " + humidity + " %");
            textViewWindSpeed.setText("Wind speed: " + speed + " m/s");
            textViewWindRotation.setText("Wind rotation: " + deg + "°");
            textViewSunrise.setText("Sunrise: " + EpochTimeToLocalTime(sunrise));
            textViewSunset.setText("Sunset: " + EpochTimeToLocalTime(sunset));

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }




        // Inflate the layout for this fragment
        return view;
    }
}