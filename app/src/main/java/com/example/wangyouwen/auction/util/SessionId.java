package com.example.wangyouwen.auction.util;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Max on 2015/11/20.
 */
public class SessionId {
    private static String sessionId = "";

    public static String getSessionId() {
        return sessionId;
    }

    public Void setSessionId(String urlPath){
        try{
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String session_value = connection.getHeaderField("Set-Cookie");
            String[] tempSessionId = session_value.split(";");
            Log.i("Test",tempSessionId[0]);
            sessionId = tempSessionId[0];
            //return  sessionId;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
