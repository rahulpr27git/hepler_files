package com.jbmatrix.utils.helper.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jbmm0007 on 11/6/2016.
 */

public class Connection {

    public static final String FILE_KEY_NAME = "file_key_name";
	private static final String TAG = "Connection";

    public static boolean checkConnection(Context ctx)
    {
        if (ctx == null)
            return false;

        ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;

        return true;
    }
    
    public static String performGetCall(String uri){
        String response = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String result;

            StringBuilder sb = new StringBuilder();

            while((result = bufferedReader.readLine())!=null){
                sb.append(result);
            }

            response =  sb.toString();
        } catch (Exception e) {
            response = "{\"status\":\"<b>failed<b> \"" +
                    ",\"error\":\"<b>failed<b> \"" +
                    ",\"msg\":\"<b>Something went wrong .Please try again later !<b> \"}";
        }finally {
            return response;
        }
    }

    public static String  performPostCall(String requestURL,
                                          HashMap<String, String> postDataParams, Context context) {

        Log.e(TAG,">>>>>>requestURL>> " + requestURL);
        if (!postDataParams.isEmpty())
            Log.e(TAG,">>>>>>requestData>> " + postDataParams.toString());

        URL url;
        String response = "";

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "{\"status\":\"failed\"" +
                        ",\"error\":\"failed\"" +
                        ",\"msg\":\"<b>Something went wrong .Please try again later !<b> \"}";

            }
        }catch (Exception e) {
            e.printStackTrace();

            response = "{\"status\":\"failed\"" +
                    ",\"error\":\"failed\"" +
                    ",\"msg\":\"<b>Something went wrong .Please try again later !<b> \"}";
        }finally {
            return response;
        }

       // Log.e(">>>>>response ",response);

       // return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public static String uploadFileToServer(String UPLOAD_URL,
                                            HashMap<String, String> postDataParams){

        String response ="";
        Log.e("+++++++++++",postDataParams.toString());
        Log.e("+++++++++++",UPLOAD_URL);

        try {

            String charset = "UTF-8";

            MultipartUtility multipart = new MultipartUtility(UPLOAD_URL, charset);

            String fileKeyName = "myFile";
            if (postDataParams.containsKey("upload_key")){
                fileKeyName = postDataParams.get("upload_key");
            }
            for(Map.Entry<String, String> entry : postDataParams.entrySet()){

                if (entry.getKey().equals(fileKeyName)){

                    File uploadFile1 = new File(entry.getValue());
                    multipart.addFilePart(entry.getKey(), uploadFile1);

                }else{
                    multipart.addFormField(entry.getKey(),entry.getValue());
                }
            }
            //multipart.addHeaderField(Constants.X_DOLLY_KEY,Constants.X_DOLLY_KEY_VALUE);

            response = multipart.finish();

        } catch (Exception e) {
            response = "{\"status\":\"failed\"}";
            e.printStackTrace();
        }finally {
            Log.e(">>>>>>response ", response);
            return response.toString();
        }
    }
}
