package com.example.smitald.stormy.Weather;

import com.example.smitald.stormy.R;

/**
 * Created by smitald on 5/4/2015.
 */
public class ForeCast {
    private CurrentWeather mCurrentWeather;
    private Hour[] mHour;
    private Day[] mDay;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public Hour[] getHour() {
        return mHour;
    }

    public void setHour(Hour[] hour) {
        mHour = hour;
    }

    public Day[] getDay() {
        return mDay;
    }

    public void setDay(Day[] day) {
        mDay = day;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {

        mHourlyForecast = hourlyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public Day[] getDailyForecast() {

        return mDailyForecast;
    }

    public static int getIconId(String iconString){
            /*
        clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
         */
        int id = R.drawable.clear_day;
        if (iconString.equals("clear-day")) id = R.drawable.clear_day;
        else if (iconString.equals("clear-night")) id = R.drawable.clear_night;
        else if (iconString.equals("rain")) id = R.drawable.rain;
        else if (iconString.equals("snow")) id = R.drawable.snow;
        else if (iconString.equals("sleet")) id = R.drawable.sleet;
        else if (iconString.equals("wind")) id = R.drawable.wind;
        else if (iconString.equals("fog")) id = R.drawable.fog;
        else if (iconString.equals("cloudy")) id = R.drawable.cloudy;
        else if (iconString.equals("partly-cloudy-day")) id = R.drawable.partly_cloudy;
        else if (iconString.equals("partly-cloudy-night")) id = R.drawable.partly_cloudy;

        return id;
    }
}
