package com.johnwayodi.cyclelogger;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.johnwayodi.cyclelogger.database.CLDatabase;
import com.johnwayodi.cyclelogger.models.Trip;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowLogFrag extends ListFragment {

    private ArrayList<Trip> trips;
    private TripAdapter tripAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CLDatabase dbAdapter = new CLDatabase(getActivity().getBaseContext());
        dbAdapter.open();
        trips = dbAdapter.getAllTrips();
        dbAdapter.close();

        tripAdapter = new TripAdapter(getActivity(), trips);
        setListAdapter(tripAdapter);

        registerForContextMenu(getListView());
    }
}
