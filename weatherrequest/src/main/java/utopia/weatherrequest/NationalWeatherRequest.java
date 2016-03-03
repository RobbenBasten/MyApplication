package utopia.weatherrequest;

import android.location.Location;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/1.
 */
public class NationalWeatherRequest {
    public static final String NATIONAL_WEATHER_SERVICE = "http://forecast.weather.gov/MapClick.php?lat=%f&lon=%f&FcstType=dwml";

    public NationalWeatherRequest(Location location)
    {
        URL url;

        try {
            url = new URL(String.format(NATIONAL_WEATHER_SERVICE,location.getLatitude(),location.getLongitude()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL for National Weather Service: " +
                NATIONAL_WEATHER_SERVICE);
        }

        InputStream is;
        try {
            is = url.openStream();
        } catch (IOException e) {
            log("Exception opening Nat'l weather URL " + e);
            e.printStackTrace();
            return;
        }
        log("Dumping weather data...");
        BufferedReader weatherReader = new BufferedReader(new InputStreamReader(is));
        try {
            for(String eachLine = weatherReader.readLine();
                    eachLine != null;
                    eachLine = weatherReader.readLine())
            {
                log(eachLine);
            }
        } catch ( IOException e ) {
            log("Exception reading data from Nat'l weather site " + e);
        }
    }

    private int log(String eachLine)
    {
        return Log.d(getClass().getName(), eachLine);
    }
}
