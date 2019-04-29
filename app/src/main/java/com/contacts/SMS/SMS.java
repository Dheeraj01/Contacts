package com.contacts.SMS;



// required imports
import android.util.Log;

import java.io.BufferedReader;
        import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SMS {
    // required variables
    static String url = "https://www.way2sms.com";
    public static String apiKey = "O0KS1TOFC4OA298RMJ7201VWAM84A1YH";
    public static String secretKey = "Y0LNACTJPO5MPL8R";
    public static String useType = "prod";
    private static final String TAG = "SMS";

    /**
     *
     * @param
     * @param phone 10 digit mobile number
     * @param message
     * @param senderId
     */
    public static String sendCampaign(String apiKey,String secretKey,String useType, String phone, String message, String senderId){
        StringBuilder content = new StringBuilder();
        try{


            Log.e(TAG,"Exception is ");
            // construct data
            JSONObject urlParameters = new JSONObject();
            urlParameters.put("apikey",apiKey);
            urlParameters.put("secret",secretKey);
            urlParameters.put("usetype",useType);
            urlParameters.put("phone", phone);
            urlParameters.put("message", URLEncoder.encode(message,"UTF-8"));
            urlParameters.put("senderid", senderId);
            URL obj = new URL(url + "/api/v1/sendCampaign");
            Log.e(TAG,"Exception is ");
            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(urlParameters.toString().getBytes());
            Log.e(TAG,"Exception is ");
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