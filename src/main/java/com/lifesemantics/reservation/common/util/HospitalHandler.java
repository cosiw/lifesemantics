package com.lifesemantics.reservation.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.XML;

public class HospitalHandler {
    public String findResult(String urlstr) throws IOException {
        StringBuffer result = new StringBuffer();
        URL url = new URL(urlstr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
        String returnLine;
        while((returnLine = br.readLine()) != null){
            result.append(returnLine + "\n\r");
        }
        urlConnection.disconnect();
        JSONObject jsonObject = XML.toJSONObject(result.toString());
        return jsonObject.toString();
    }

}
