package com.agh.tramsim.utils;

import com.agh.tramsim.elements.Position;
import com.agh.tramsim.maps.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JSONReader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONReader.class);

    private static final String lineFactorJSON = "{\n" +
            "  \"values\" : [\n" +
            "    {\n" +
            "      \"lineNumber\": 1,\n" +
            "      \"value\": 130\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 2,\n" +
            "      \"value\": 74\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 3,\n" +
            "      \"value\": 54\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 4,\n" +
            "      \"value\": 74\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 5,\n" +
            "      \"value\": 73\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 6,\n" +
            "      \"value\": 60\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 8,\n" +
            "      \"value\": 131\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 9,\n" +
            "      \"value\": 56\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 10,\n" +
            "      \"value\": 73\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 11,\n" +
            "      \"value\": 72\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 13,\n" +
            "      \"value\": 54\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 14,\n" +
            "      \"value\": 72\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 16,\n" +
            "      \"value\": 66\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 17,\n" +
            "      \"value\": 28\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 18,\n" +
            "      \"value\": 131\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 19,\n" +
            "      \"value\": 73\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 20,\n" +
            "      \"value\": 129\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 21,\n" +
            "      \"value\": 73\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 22,\n" +
            "      \"value\": 73\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 24,\n" +
            "      \"value\": 74\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 44,\n" +
            "      \"value\": 22\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 50,\n" +
            "      \"value\": 161\n" +
            "    },\n" +
            "    {\n" +
            "      \"lineNumber\": 52,\n" +
            "      \"value\": 161\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private final Class<T> classType;

    public JSONReader(Class<T> classType) {
        this.classType = classType;
    }

    /**
     *
     * @param filePath  path to file in resource (e.g. 'routes/4.json')
     * @return  list of objects
     */
    public List<T> readJSONFromFile(String filePath) {
        ClassLoader classLoader = JSONReader.class.getClassLoader();
        Gson gson = new Gson();
        List<T> objects = new ArrayList<>();
//        try (Stream<String> stream = Files.lines(Paths.get(classLoader.getResource(filePath).getPath()))) {
//            stream.forEach(line -> objects.add(gson.fromJson(line, classType)));
//        } catch (IOException ex) {
//            LOGGER.warn("initStopsFromJSON(): " +  ex.getLocalizedMessage());
//            ex.printStackTrace();
//        }
        return  objects;
    }

    public static List<List<Position>> readCoordinatesFromFile(String coordinatesPath) {
        List<String> lineIds = Arrays.asList("233646661","233646659","40716891","234565950","233730605","233636402","233684784","233684791","459837594","233673945","234098807","233673929","233673928","233678923","178281419","233738205","165267406","165267407","216311907","158090846","164771723","234461523","164771717","192495229","77355441","61581321","62249344","206331501","140579095","20195470","26198757","165557663","26197704","459893232","117064186","117064187","459893234","26197702","165557661");
        List<List<Position>> positions = new ArrayList<>();
        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/export.json"));
            JSONArray features = (JSONArray) jsonObject.get("features");
            for (int i = 0; i < features.size(); i++) {
                JSONObject element = (JSONObject) features.get(i);
                if (lineIds.contains(element.get("id").toString().substring(4))) {
                    positions.add(new ArrayList<>());
                    JSONObject geometry = (JSONObject) element.get("geometry");
                    JSONArray coordinates = (JSONArray) geometry.get("coordinates");
                    for (int j = 0; j < coordinates.size(); j++) {
                        JSONArray positionArray = (JSONArray) coordinates.get(j);
                        double x = Double.parseDouble(positionArray.get(0).toString());
                        double y = Double.parseDouble(positionArray.get(1).toString());
                        positions.get(positions.size() - 1).add(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)));
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("readCoordinatesFromFile(): ", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            LOGGER.error("readCoordinatesFromFile(): ", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return positions;
    }

    public static Map<Integer, BigDecimal> initLineFactorJSON() {
        Map<Integer, BigDecimal> map = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(lineFactorJSON);
//            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/res/raw/linefactor.json"));
            JSONArray values = (JSONArray) jsonObject.get("values");
            for (int i = 0; i < values.size(); i++) {
                JSONObject tram = (JSONObject) values.get(i);
                Integer lineNumber = Integer.valueOf(tram.get("lineNumber").toString());
                BigDecimal value = new BigDecimal(tram.get("value").toString());
                map.put(lineNumber, value);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

}
