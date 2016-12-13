package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;
import java.lang.Math;
import java.lang.*;

import static android.R.attr.start;
import static android.R.attr.x;
import static android.R.id.primary;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.quakereport.R.id.mag;


public class infoAdapter extends ArrayAdapter<earthquakes> {

    private static final String LOCATIONSEPERATOR = "of";

    public String primaryLocation;
    public String secondaryLocation;


    public infoAdapter(Activity context, ArrayList<earthquakes> earthquake){

    super(context,0,earthquake);}



    public int getMagnitudeColor(double x) {

        int i = (int)Math.floor(x);

        switch (i) {
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);

            case 3:
                int magnitudeColor2 = ContextCompat.getColor(getContext(), R.color.magnitude3);
                return magnitudeColor2;
            case 4:
                int magnitudeColor3 = ContextCompat.getColor(getContext(), R.color.magnitude4);
                return magnitudeColor3;

            case 5:
                int magnitudeColor4 = ContextCompat.getColor(getContext(), R.color.magnitude5);
                return magnitudeColor4;
            case 6:
                int magnitudeColor5 = ContextCompat.getColor(getContext(), R.color.magnitude6);
                return magnitudeColor5;
            case 7:
                int magnitudeColor6 = ContextCompat.getColor(getContext(), R.color.magnitude7);
                return magnitudeColor6;
            case 8:
                int magnitudeColor7 = ContextCompat.getColor(getContext(), R.color.magnitude8);
                return magnitudeColor7;
            case 9:
                int magnitudeColor8 = ContextCompat.getColor(getContext(), R.color.magnitude9);
                return magnitudeColor8;
            case 10:
                int magnitudeColor9 = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                return magnitudeColor9;
            default:
                int magnitudeColor10 = ContextCompat.getColor(getContext(), R.color.magnitude1);
                return magnitudeColor10;
        }


    };

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }

        earthquakes newEarthquake = getItem(position);

        String City = newEarthquake.getCityName();
        if (City.contains(LOCATIONSEPERATOR)){
            String[] parts = City.split(LOCATIONSEPERATOR);
             primaryLocation = parts[0]+LOCATIONSEPERATOR;
             secondaryLocation = parts[1];
        }else {
            primaryLocation = getContext().getString(R.string.Near_the);
            secondaryLocation = City;

        }

        TextView magnitude = (TextView) listItemView.findViewById(mag);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(newEarthquake.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);




        magnitude.setText(String.valueOf(newEarthquake.getMag()));



            TextView cityName = (TextView)listItemView.findViewById(R.id.cityname);

        cityName.setText(primaryLocation);

        TextView dateIn = (TextView) listItemView.findViewById(R.id.date);
        dateIn.setText(newEarthquake.getDate());

        TextView timeIn = (TextView) listItemView.findViewById(R.id.time);
        timeIn.setText(newEarthquake.getTime());


        TextView firstHalfPart = (TextView) listItemView.findViewById(R.id.first_half);
        firstHalfPart.setText(secondaryLocation);







        return listItemView;

    }
}
