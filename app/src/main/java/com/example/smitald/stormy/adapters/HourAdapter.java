package com.example.smitald.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smitald.stormy.R;
import com.example.smitald.stormy.Weather.Hour;

/**
 * Created by smitald on 5/6/2015.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Context mContext;
    private Hour[] mHours;

    public HourAdapter(Context context, Hour[] hours){
          mHours = hours;
          mContext = context;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder hourViewHolder = new HourViewHolder(view);
        return hourViewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {
       hourViewHolder.bindHour(mHours[i]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTimeLabel;
        public ImageView mImage;
        public TextView mSummary;
        public TextView mTemp;



        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView)itemView.findViewById(R.id.timeLabel);
            mImage = (ImageView) itemView.findViewById(R.id.iconhour);
            mSummary = (TextView) itemView.findViewById(R.id.summaryhour);
            mTemp = (TextView) itemView.findViewById(R.id.temphour);

            itemView.setOnClickListener(this);
        }


        public void bindHour(Hour hour){
            mTimeLabel.setText(hour.getHour());
            mSummary.setText(hour.getSummary());
            mTemp.setText(hour.getTemperature() + "");
            mImage.setImageResource(hour.getIconId());

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Welcome", Toast.LENGTH_LONG).show();
        }
    }

}
