package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.onClick;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String SAMPLE_JSON_RESPONSE = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&minmagnitude=2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        taskAsync firstTry = new taskAsync();
        firstTry.execute();


    }

    private class taskAsync extends AsyncTask<URL,Void,ArrayList<earthquakes>>{
        @Override
        protected ArrayList<earthquakes> doInBackground(URL... urls ) {

            ArrayList<earthquakes> quake1 = QueryUtils.fetchEarthquakeData(SAMPLE_JSON_RESPONSE);

            return quake1;

        }

        @Override
        protected void onPostExecute(ArrayList<earthquakes> quake1  ) {


            final infoAdapter adapter = new infoAdapter(EarthquakeActivity.this, quake1);
            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);



            // Create a new {@link ArrayAdapter} of earthquakes
            /**ArrayAdapter<String> adapter = new ArrayAdapter<String>(
             this, android.R.layout.simple_list_item_1, earthquakes);*/


            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);


            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    earthquakes newEarthquake = adapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(newEarthquake.getURL());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });




        }
    }

}
