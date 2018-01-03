package com.agh.tramsim.utils;

import com.agh.tramsim.elements.Position;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class JSONReader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONReader.class);

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
        try (Stream<String> stream = Files.lines(Paths.get(classLoader.getResource(filePath).getPath()))) {
            stream.forEach(line -> objects.add(gson.fromJson(line, classType)));
        } catch (IOException ex) {
            LOGGER.warn("initStopsFromJSON(): " +  ex.getLocalizedMessage());
            ex.printStackTrace();
        }
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
                        positions.get(positions.size() - 1).add(new Position(x, y));
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

}
