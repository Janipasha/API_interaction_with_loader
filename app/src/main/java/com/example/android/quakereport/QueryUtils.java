package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.string.no;
import static com.example.android.quakereport.R.id.mag;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Sample JSON response for a USGS query
     */
    public static final String LOG_M = EarthquakeActivity.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    public static ArrayList<earthquakes> fetchEarthquakeData(String requestURL) {

        URL url = createUrl(requestURL);
        String jsonResponse = makeHttprequest(url);

       ArrayList <earthquakes> earthquake = extractEarthquakes(jsonResponse);
        return  earthquake;
    }

    private static String makeHttprequest(URL url) {

        String need = "";

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                need = readFromStream(inputStream);


            } else {
                Log.e(LOG_M, "Wrong URL response code " + urlConnection.getResponseCode());
            }


        } catch (IOException e) {

            Log.e(LOG_M, "Error in retriving url connection ");
        }

        return need;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder() ;

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String Line = reader.readLine();
        while (Line != null) {
            output = output.append(Line);
            Line = reader.readLine();
        }


        return output.toString();
    }


    private static URL createUrl(String url) {

        URL newURL = null;

        try {

            newURL = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_M, "URL not valid", e);

        }

        return newURL;
    }


    /**
     * Return a list of {@link earthquakes} objects that has been built up from
     * parsing a JSON response.
     */


    public static ArrayList<earthquakes> extractEarthquakes(String jsonResponse) {


        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<earthquakes> earthquake1 = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            JSONObject dateQuake = new JSONObject(jsonResponse);
            JSONArray featureData = dateQuake.getJSONArray("features");
            Date DateObject;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM DD,yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            DecimalFormat magFormat = new DecimalFormat("0.0");

            for (int i = 0; i < featureData.length(); i++) {
                JSONObject tempEarthquake = featureData.getJSONObject(i);
                JSONObject properties = tempEarthquake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                DateObject = new Date(time);
                String dateToDisplay = dateFormat.format(DateObject);
                String timeToDisplay = timeFormat.format(DateObject);
                String magToDisplay = magFormat.format(mag);
                earthquake1.add(new earthquakes(place, Double.valueOf(magToDisplay), dateToDisplay, timeToDisplay, url));


            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquake1;
    }
}

