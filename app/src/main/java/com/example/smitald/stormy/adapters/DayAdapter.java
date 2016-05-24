package com.example.smitald.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smitald.stormy.R;
import com.example.smitald.stormy.Weather.Day;

/**
 * Created by smitald on 5/5/2015.
 */
public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days){
          mContext = context;
          mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // We are not going to use this , can be used to tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconimg = (ImageView) convertView.findViewById(R.id.iconimg);
            holder.temp = (TextView) convertView.findViewById(R.id.tempView);
            holder.dayofweek = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Day day = mDays[position];

        holder.iconimg.setImageResource(day.getIconId());
        holder.temp.setText(day.getTempMax() + "");
        holder.dayofweek.setText(day.getDayOfTheWeek());

        return convertView;
    }

    private static class ViewHolder{
        ImageView iconimg;   //Public by default
        TextView  dayofweek;
        TextView  temp;
    }
}
