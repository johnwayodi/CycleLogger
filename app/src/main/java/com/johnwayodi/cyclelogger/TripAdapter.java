package com.johnwayodi.cyclelogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.johnwayodi.cyclelogger.models.Trip;
import com.johnwayodi.cyclelogger.viewholders.TripViewHolder;

import java.util.ArrayList;

/**
 * Created by johnwayodi on 11/20/16.
 */

public class TripAdapter extends ArrayAdapter<Trip> {
    public TripAdapter(Context context, ArrayList<Trip> trips) {
        super(context, 0, trips);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get data item for this position
        Trip trip = getItem(position);

        //create new RowViewHolder
        TripViewHolder tripViewHolder;

        //check if existing value is being reused, otherwise inflate new view
        if (convertView==null){
            tripViewHolder = new TripViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_row, parent, false);

            //set views to the viewholder
            tripViewHolder.distanceCovered = (TextView) convertView.findViewById(R.id.list_trip_distance);

            //set tg to remember our view holder references to widgets
            convertView.setTag(tripViewHolder);
        }else {
            //view already exists, go to view holder and take widgets it
            tripViewHolder = (TripViewHolder)convertView.getTag();
        }
        //fill each new referenced view with associated data
        //TODO: CHANGE CASTING TO DOUBLE IF ISSUES COME UP.
        tripViewHolder.distanceCovered.setText((int) trip.getDist());

        //return modified view  to show appropriate data
        return convertView;
    }
}
