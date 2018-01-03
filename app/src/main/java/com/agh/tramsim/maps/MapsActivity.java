package com.agh.tramsim.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng CRACOW = new LatLng(50.063118, 19.944923);
    private static final long ZOOM = 10;

//    private static final BitmapDescriptor ICON = BitmapDescriptorFactory.fromResource(R.drawable.przystanek);
    private GeoJsonLayer tramLayer = null;
    private GeoJsonLayer stopsLayer = null;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.moveCamera(CameraUpdateFactory.newLatLng(CRACOW));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CRACOW, ZOOM));

        addLayers();

        setStopNames();


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
        for (GeoJsonFeature feature : stopsLayer.getFeatures()) {

            if (feature.hasProperty("name")) {
                GeoJsonPointStyle stop = new GeoJsonPointStyle();
                Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.przystanek);
                Bitmap scaledIcon = icon.createScaledBitmap(icon,20,20,false);
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
