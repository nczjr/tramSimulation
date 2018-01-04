package com.agh.tramsim.utils;

import com.agh.tramsim.elements.Tram;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TTSSParser {

    private static final String TTSS = "http://www.ttss.krakow.pl/internetservice";
    private static final String TRAMS = "/geoserviceDispatcher/services/vehicleinfo/vehicles?positionType=CORRECTED&colorType=ROUTE_BASED";

    public static List<Tram> getAllTrams() {
        String pageContent = getPageContent(TTSS + TRAMS);
        String[] json = pageContent.split(":", 3);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Tram> trams;
        try {
            trams = objectMapper.readValue(json[2], new TypeReference<List<Tram>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return trams.stream().filter(tram -> tram.getName() != null).collect(Collectors.toList());
    }

    private static String getPageContent(String urlString) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
