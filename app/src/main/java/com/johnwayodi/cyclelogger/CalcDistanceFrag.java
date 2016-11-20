package com.johnwayodi.cyclelogger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnwayodi.cyclelogger.database.CLDatabase;
import com.johnwayodi.cyclelogger.models.MyLocation;
import com.johnwayodi.cyclelogger.utils.DistanceCalculator;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalcDistanceFrag extends Fragment {

    private ArrayList<MyLocation> coordinates;
    private DistanceCalculator calc;
    private int i = 0;
    private String unit = "K";
    private double totalDistance = 0, distance = 0, finalDistance = 0;
    private TextView dist;


    public CalcDistanceFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_calc_distance, container, false);
        //TODO: find a way of calling the showDistanceCovered method here...

        dist = (TextView) v.findViewById(R.id.final_distance_covered);

        dist.append( showDistanceCovered() + "Km");
        return v;
    }


    private double showDistanceCovered(){
        calc = new DistanceCalculator();
        CLDatabase dbAdapter = new CLDatabase(getActivity().getBaseContext());
        dbAdapter.open();
        coordinates = dbAdapter.getAllCoordinates();
        dbAdapter.close();

        for (i = 1; i <= coordinates.size(); i ++){
            // calculate distance between two consecutive coordinates and add to the distance
            distance = calc.calculateDistance(
                    coordinates.get(i - 1).getLat(),
                    coordinates.get(i - 1).getLng(),
                    coordinates.get(i).getLat(),
                    coordinates.get(i).getLng(),
                    unit);
            totalDistance = totalDistance + distance;
        }

        saveToDatabase(totalDistance);
        return totalDistance;

    }

    //save the trip distance to database
    private void saveToDatabase(double value){

        CLDatabase dbAdapter = new CLDatabase(getActivity().getBaseContext());
        dbAdapter.open();
        dbAdapter.addTrip(value);
        dbAdapter.close();
    }

}
