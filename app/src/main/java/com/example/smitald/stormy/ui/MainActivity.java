package com.example.smitald.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smitald.stormy.R;
import com.example.smitald.stormy.Weather.CurrentWeather;
import com.example.smitald.stormy.Weather.Day;
import com.example.smitald.stormy.Weather.ForeCast;
import com.example.smitald.stormy.Weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DailyForeCast";
    public static final String HOURLY_FORECAST = "HourlyForeCast";
    private ForeCast mForeCast;

    @InjectView(R.id.TempView)TextView mTempLabel;
    @InjectView(R.id.degreeView)ImageView mDegreeImg;
    @InjectView(R.id.timeView)TextView mTimeLabel;
    @InjectView(R.id.locationlabel)TextView mLocation;
    @InjectView(R.id.weathericon)ImageView mWeatherIconImg;
    @InjectView(R.id.humiditylable)TextView mHumidLabel;
    @InjectView(R.id.humidityval)TextView mHumidVal;
    @InjectView(R.id.preciView)TextView mPrecipLabel;
    @InjectView(R.id.preciVal)TextView mPreciVal;
    @InjectView(R.id.summaryView)TextView mSummary;
    @InjectView(R.id.ibutton)ImageButton mRefreshButton;
    @InjectView(R.id.progbarspin)ProgressBar mProgressBar;
    @InjectView(R.id.hourbutton)Button mHourButton;
    @InjectView(R.id.dailybutton)Button mDailyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 37.8267;
        final double longitude = -122.423;

            mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForeCast(latitude, longitude);
            }
        });

        getForeCast(latitude, longitude);

    }

    private void getForeCast(double latitude, double longitude) {
        String apiKey = "caad470a6c38612cfc8131aca793ba41";


        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey
                + "/" + latitude +',' + longitude;

        if (checkIfNetworkPresent()) {
            ToggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToggleRefresh();
                        }
                    });

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToggleRefresh();
                        }
                    });
                    try {
                        String jsondata = response.body().string();
                        Log.v(TAG, jsondata);
                        if (response.isSuccessful()) {
                            mForeCast = getForecastDetails(jsondata);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Execption caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Execption caught", e);
                    }
                }
            });
        }
        else{
            Toast.makeText(this, getString(R.string.Network_unavailable), Toast.LENGTH_LONG).show();
        }
    }

    private void ToggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshButton.setVisibility(View.INVISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        CurrentWeather weather = mForeCast.getCurrentWeather();
        mTempLabel.setText(weather.getTemp() + "");
        mTimeLabel.setText("AT " + weather.getFormattedTime() + " it will be");
        mHumidVal.setText(weather.getHumid() + "");
        mPreciVal.setText(weather.getPrecip() + "%");
        mLocation.setText(weather.getTimeZone());
        mSummary.setText(weather.getSummary());
        Drawable drawable = getResources().getDrawable(weather.getIconId());
        mWeatherIconImg.setImageDrawable(drawable);
    }


    private ForeCast getForecastDetails(String jsondata) throws JSONException{
            ForeCast foreCast = new ForeCast();
            foreCast.setCurrentWeather(getCurrentweather(jsondata));
            foreCast.setHourlyForecast(getHourlyforeCast(jsondata));
            foreCast.setDailyForecast(getDailyForeCast(jsondata));
    return foreCast;
    }

    private Hour[] getHourlyforeCast(String jsondata) throws JSONException{
        JSONObject forecast = new JSONObject(jsondata);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];
        for (int i = 0 ; i < data.length() ; i++){
            JSONObject jsonhour = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(jsonhour.getString("summary"));
            hour.setIcon(jsonhour.getString("icon"));
            hour.setTemp(jsonhour.getDouble("temperature"));
            hour.setTime(jsonhour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }
        return hours;
    }

    private Day[]  getDailyForeCast(String jsondata)throws JSONException {
        JSONObject forecast = new JSONObject(jsondata);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];
        for (int i = 0 ; i < data.length() ; i++){
            JSONObject jsonday = data.getJSONObject(i);
            Day day = new Day();
            day.setSummary(jsonday.getString("summary"));
            day.setIcon(jsonday.getString("icon"));
            Log.e("smital", "While Setting " + jsonday.getDouble("temperatureMax"));
            day.setTempMax(jsonday.getDouble("temperatureMax"));
            day.setTime(jsonday.getInt("time"));
            day.setTimeZone(timezone);

            days[i] = day;
        }
        return days;


    }

    private CurrentWeather getCurrentweather(String jsondata) throws JSONException{
        JSONObject forecast = new JSONObject(jsondata);
        String timezone = forecast.getString("timezone");
        Log.e(TAG, "From JSON : " + timezone);
        JSONObject current = forecast.getJSONObject("currently");
        CurrentWeather weather = new CurrentWeather();
        weather.setTimeZone(timezone);
        weather.setIcon(current.getString("icon"));
        weather.setTime(current.getLong("time"));
        weather.setTemp(current.getDouble("temperature"));
        weather.setHumid(current.getDouble("humidity"));
        weather.setPrecip(current.getDouble("precipIntensity"));
        weather.setSummary(current.getString("summary"));

        Log.d(TAG, "Formatted Time " + weather.getFormattedTime());
        return weather;
    }

    private boolean checkIfNetworkPresent() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvialable = false;

        if (networkInfo != null){
            isAvialable = true;
        }
       return isAvialable;

    }

    private void alertUserAboutError() {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailybutton)
    public void startDailyActivity(View v){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForeCast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick(R.id.hourbutton)
    public void startHourlyActivity(View v){
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForeCast.getHourlyForecast());
        startActivity(intent);
    }


}
