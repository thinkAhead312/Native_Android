package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dna on 8/25/16.
 */
public  class ForeCastFragment extends Fragment {

    ArrayAdapter<String> mForeCastAdapter;


    public ForeCastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add this line in order for this fragment to handle menu events;
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        FetchWeatherTask weatherTask = new FetchWeatherTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location  = prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

         mForeCastAdapter = new ArrayAdapter<String>(
                getActivity(), //The Current context
                R.layout.list_item_forecast, //id of List item layout
                R.id.list_item_forecast_textview, //id of textview to populate
                new ArrayList<String>()); //Forecast Data

        //Get Reference to the listview, and attache to the listview
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForeCastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = mForeCastAdapter.getItem(position);
                Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);

            }
        });


        return rootView;
    }
    //attributes that we care about
    // max, main, 
    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        /* The date/time conversion code is going to be moved outside the asynctask later,
       * so for convenience we're breaking it out into its own method now.
       */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }


        @Override
        protected String[] doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                //Will contain the raw JSON response as a string
                String forecastJsonStr = null;

                String format = "json";
                String units = "metric";
                String app_id = "3291ffc55f571085fe9208b83e061b1b";
                int numDays = 7;

                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are available at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast
                    final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                    final String QUERY_PARAM = "q";
                    final String FORMAT_PARAM = "mode";
                    final String UNIT_PARAM = "units";
                    final String DAYS_PARAM = "cnt";
                    final String APP_ID = "APPID";

                    Uri builtUri  = Uri.parse(FORECAST_BASE_URL).buildUpon()
                            .appendQueryParameter(QUERY_PARAM, params[0])
                            .appendQueryParameter(FORMAT_PARAM, format)
                            .appendQueryParameter(UNIT_PARAM, units)
                            .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                            .appendQueryParameter(APP_ID, app_id)
                            .build();

                    URL url = new URL(builtUri.toString());
                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    Log.v(LOG_TAG, String.valueOf(builtUri));



                    //Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        //nothing to do;
                       return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                       return null;
                    }
                    forecastJsonStr = buffer.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attempting
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if(reader != null) {
                        try{
                            reader.close();
                        }catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
             }

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String[] result) {
            if(result != null) {
                mForeCastAdapter.clear(); //clear the forecast adapter
                for(String dayForecastStr: result) {
                    mForeCastAdapter.add(dayForecastStr);
                }
            }
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low, String unitType) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            if(unitType.equals(getString(R.string.pref_units_imperial))) {
                high = (high * 1.8) + 32;
                low = (low * 1.8) + 32;
            } else if(!unitType.equals(getString(R.string.pref_units_metric))) {
                Log.d(LOG_TAG, "Unit type not found: " + unitType);
            }


            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }


        /**
         *
         * take the String representing the complete forecast in Json Format and
         * pull out the data we need to  construc the strings needed for the wireframes
         *
         * Fortunately parsing is easy: constructor takes the JSON string and converts it into
         * an Object hierarchy for us
         *
         */

        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays) throws JSONException {

            //These are the names of the JSON objects that need to be extracted
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject foreCastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = foreCastJson.getJSONArray(OWM_LIST);
            /**
             *  OWM returns daily forecast based upon the local time of the city and that is being
             *  asked for, which means that we need to know the GMT offset to translate this data
             *  properly
             *
             *  Since this data is also sent in order and the first day is always the
             *  current day, we're going to take advantage of that to get a nice
             *  normalized UTC date for all of our weather
             */

            Time dayTime = new Time();
            dayTime.setToNow();
            Log.v(LOG_TAG + ": dayTime", String.valueOf(dayTime));
            //we start at the day returned by local time. Otherwise this is a mess;
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(),dayTime.gmtoff);
            Log.v(LOG_TAG + ": julianStartDay", String.valueOf(julianStartDay));
            //now we work exclusivlely in UTC
            dayTime = new Time();
            String[] resultStrs = new String[numDays];

            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
            String unitType = sharedPrefs.getString(
                    getString(R.string.pref_units_key),
                    getString(R.string.pref_units_metric));

            for(int i = 0; i < weatherArray.length(); i++) {
                //for now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                //Get the Json object representing the day
                JSONObject dayForeCast = weatherArray.getJSONObject(i);

                //the date/time is returned as a long. we need to convert that
                // into someting human-readable since most people wont read "14000356800" as
                // "this saturday"

                long dateTime;
                // Cheating to convert this to UTC time, which we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay + i);
                Log.v(LOG_TAG + ": dateTime", String.valueOf(dateTime));
                day = getReadableDateString(dateTime);
                Log.v(LOG_TAG + ": day", String.valueOf(day));

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForeCast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForeCast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low, unitType);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;

            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }

            return resultStrs;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }
}