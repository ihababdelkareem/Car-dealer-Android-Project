package com.example.ihab.labproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPManager {
    public static String getData(String URI) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(URI);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line +'\n');
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception ex) {
            Log.d("HttpURLConnection",ex.toString());
        }
        return null;
    }
}