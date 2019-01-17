package br.com.xplorer.apiwifirtt;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttoHelper {


    public static void connectionToUrl(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.disconnect();

        } catch (IOException e) {
            Log.e("MALFORMED_URL_EX", e.getLocalizedMessage());
        }
    }

}
