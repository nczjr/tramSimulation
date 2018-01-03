package com.agh.tramsim.elements;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class TramRoute {

    private static final Logger LOGGER = LoggerFactory.getLogger(TramRoute.class);

    private int tramNumber;

    private List<Stop> stops;

    public TramRoute(List<Stop> stops, int tramNumber) {
        this.stops = stops;
        this.tramNumber = tramNumber;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void initStopsFromJSON() {
        String path = "routes/" + tramNumber + ".json";
        ClassLoader classLoader = getClass().getClassLoader();
        Gson gson = new Gson();
        try (Stream<String> stream = Files.lines(Paths.get(classLoader.getResource(path).getPath()))) {
            stream.forEach(line -> stops.add(gson.fromJson(line, Stop.class)));
        } catch (IOException e) {
            LOGGER.warn("initStopsFromJSON() - error");
            e.printStackTrace();
        }
    }

}
