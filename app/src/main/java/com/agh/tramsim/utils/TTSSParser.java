package com.agh.tramsim.utils;

import com.agh.tramsim.elements.Stop;
import com.agh.tramsim.elements.Tram;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TTSSParser {

    private static final String TTSS = "http://www.ttss.krakow.pl/internetservice";
    private static final String TRAMS = "/geoserviceDispatcher/services/vehicleinfo/vehicles?positionType=CORRECTED&colorType=ROUTE_BASED";
    private static final String ROUTE = "/services/tripInfo/tripPassages?tripId=";

    public static List<Tram> getAllTrams() {
        String jsonArrayAsString =  getJSONArrayAsObject(getPageContent(TTSS + TRAMS), "vehicles").toString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Tram> trams;
        try {
            trams = objectMapper.readValue(jsonArrayAsString, new TypeReference<List<Tram>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        trams = trams.stream().filter(tram -> tram.getName() != null).collect(Collectors.toList());
        for (Tram tram : trams) {
            tram.calculateCurrentPosition();
            tram.readNumberFromName();
        }
        return trams;
    }

    public static List<Stop> getCurrentRouteForTram(String tripId) {
        JSONArray jsonArray = (JSONArray) getJSONArrayAsObject(getPageContent(TTSS + ROUTE + tripId), "actual");
        List<Stop> stops = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String name = ((JSONObject) ((JSONObject) jsonArray.get(i)).get("stop")).get("name").toString();
            int sequentialNumber = Integer.parseInt(((JSONObject) jsonArray.get(i)).get("stop_seq_num").toString());
            stops.add(new Stop(name, sequentialNumber));
        }
        return stops;
    }

    private static Object getJSONArrayAsObject(String pageContent, String arrayName) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(pageContent);
            return jsonObject.get(arrayName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPageContent(String urlString) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setAllowUserInteraction(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() ==  HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
