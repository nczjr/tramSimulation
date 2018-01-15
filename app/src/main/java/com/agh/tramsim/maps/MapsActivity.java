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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

        Runnable runnable = new Runnable() {
            public void run() {
                while(true){
                    trams = TTSSParser.getAllTrams();
                    List<Tram> tram1 = trams.subList(0, trams.size()/8);
                    List<Tram> tram2 = trams.subList(trams.size()/8, trams.size()/4);
                    List<Tram> tram3 = trams.subList(trams.size()/4, 3*trams.size()/8);
                    List<Tram> tram4 = trams.subList(3*trams.size()/8, trams.size()/2);
                    List<Tram> tram5 = trams.subList(trams.size()/2, 5*trams.size()/8);
                    List<Tram> tram6 = trams.subList(5*trams.size()/8, 3*trams.size()/4);
                    List<Tram> tram7 = trams.subList(3*trams.size()/4, 7*trams.size()/8);
                    List<Tram> tram8 = trams.subList(7*trams.size()/8, trams.size());
                    Thread t1 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram1) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t1.start();
                    Thread t2 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram2) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t2.start();
                    Thread t3 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram3) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t3.start();
                    Thread t4 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram4) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t4.start();
                    Thread t5 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram5) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t5.start();
                    Thread t6 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram6) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t6.start();
                    Thread t7 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram7) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t7.start();
                    Thread t8 = new Thread(new Runnable() {
                        public void run() {
                            for (Tram tram : tram8) {
                                tram.getPopulation().calculatePopulation();
                            }
                        }
                    });
                    t8.start();
                    try {
                        t1.join();
                        t2.join();
                        t3.join();
                        t4.join();
                        t5.join();
                        t6.join();
                        t7.join();
                        t8.join();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        handler.post(new Runnable() {
                        @Override
                        public void run() {
                            removeMarkers();
                            addUpdatedMarkers();
                        }
                    });

                }
            }
        };
        new Thread(runnable).start();
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
