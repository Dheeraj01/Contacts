package com.contacts;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class URLResponse {

    // required variables

    private static final String TAG = "URLResponse";

    public static String geturlresponse(String url){
        StringBuilder content = new StringBuilder();
        try{


            Log.e(TAG,"Exception is ");
            // construct data
            JSONObject urlParameters = new JSONObject();
            URL obj = new URL(url);
            Log.e(TAG,"Exception is ");
            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            /*httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("GET");
           */ /*DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(urlParameters.toString().getBytes());
           */ Log.e(TAG,"Exception is ");
            // get the response
            BufferedReader bufferedReader = null;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }catch(Exception ex){
            Log.e(TAG,"Exception is ",ex);
        }
        return content.toString();
    }

}
