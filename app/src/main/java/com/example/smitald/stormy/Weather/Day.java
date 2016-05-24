package com.example.smitald.stormy.Weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by smitald on 5/4/2015.
 */
public class Day implements Parcelable{
    private long mTime;
    private String mSummary;
    private double mTempMax;
    private String mIcon;
    private String mTimeZone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTempMax() {
        Log.e("smital", "The TempMax original is  " + (mTempMax));
        return (int) Math.round(mTempMax);
    }

    public void setTempMax(double tempMax) {

        mTempMax = tempMax;
        Log.e("smital", "original setter is  " + (mTempMax));
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getIconId(){
        return ForeCast.getIconId(mIcon);
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date datetime = new Date(mTime * 1000);
        return formatter.format(datetime);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTempMax);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);

    }

    public Day(){

    }

    private Day(Parcel in){
        mTime = in.readLong();
        mSummary = in.readString();
        mTempMax = in.readDouble();
        mIcon = in.readString();
        mTimeZone= in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

}






















