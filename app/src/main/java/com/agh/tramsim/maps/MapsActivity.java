package com.agh.tramsim.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.agh.tramsim.elements.Position;
import com.agh.tramsim.elements.Tram;
import com.agh.tramsim.utils.TTSSParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng CRACOW = new LatLng(50.063118, 19.944923);
    private static final long ZOOM = 12;
    private List<Marker> markers = new ArrayList<>();
    private volatile List<Tram> trams;
    private GoogleMap map;
    private final Handler handler = new Handler();
    private GeoJsonLayer tramLayer;
    private GeoJsonLayer stopsLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Runnable runnable = () -> {

            while (true) {
                trams = TTSSParser.getAllTrams();
                for (Tram t : trams) {
                    t.getPopulation().calculatePopulation();
                }


                handler.post(() -> {
                    removeMarkers();
                    addUpdatedMarkers();
                });
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    public class MyRunnable implements Runnable {
        public void run() {
            //Update the UI



        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.moveCamera(CameraUpdateFactory.newLatLng(CRACOW));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CRACOW, ZOOM));

        addLayers();
        setStopNames();
    }

    private void addUpdatedMarkers() {
        for (Tram tram : trams) {
            Position pos = tram.getCurrentPosition();
            float markerIcon = setColor(tram);
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(pos.getX().doubleValue(), pos.getY().doubleValue()))
                    .icon(BitmapDescriptorFactory.defaultMarker(markerIcon))
                    .title(tram.getName()));
            markers.add(marker);
        }
    }

    private float setColor(Tram tram) {
        switch (tram.getPopulation().getPopulationLevel()) {
            case RED:
                return BitmapDescriptorFactory.HUE_RED;

            case GREEN:
                return BitmapDescriptorFactory.HUE_GREEN;

            case ORANGE:
                return BitmapDescriptorFactory.HUE_ORANGE;

            case YELLOW:
                return BitmapDescriptorFactory.HUE_YELLOW;

        }
        return BitmapDescriptorFactory.HUE_MAGENTA;
    }

    private void removeMarkers() {
        if (!markers.isEmpty()) {
            for (Marker marker : markers)
                marker.remove();
        }
    }

    private void addLayers() {
        try {
            tramLayer = new GeoJsonLayer(map, R.raw.lines, getApplicationContext());
            stopsLayer = new GeoJsonLayer(map, R.raw.stops, getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adjustLayerStyle();
        tramLayer.addLayerToMap();
        stopsLayer.addLayerToMap();
    }

    private void adjustLayerStyle() {
        GeoJsonPointStyle pointStyle = tramLayer.getDefaultPointStyle();
        pointStyle.setVisible(false);
        GeoJsonPolygonStyle polygonStyle = tramLayer.getDefaultPolygonStyle();
        polygonStyle.setStrokeWidth(1);

    }

    private void setStopNames() {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.przystanek);
        Bitmap scaledIcon = icon.createScaledBitmap(icon, 20, 20, false);

        for (GeoJsonFeature feature : stopsLayer.getFeatures()) {
            if (feature.hasProperty("name")) {
                GeoJsonPointStyle stop = new GeoJsonPointStyle();
                stop.setIcon(BitmapDescriptorFactory.fromBitmap(scaledIcon));
                stop.setTitle(feature.getProperty("name"));
                feature.setPointStyle(stop);
            } else {
                GeoJsonPointStyle stop = new GeoJsonPointStyle();
                stop.setVisible(false);
                feature.setPointStyle(stop);
            }
        }
    }
}
