package gfandos.myplaces.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang3.ObjectUtils;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.io.IOException;

import gfandos.myplaces.R;
import gfandos.myplaces.Utils.CameraManager;
import gfandos.myplaces.Utils.GPSTracker;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;
    private GPSTracker tracker;
    private CameraManager cameraManager;
    private FloatingActionButton floatingCameraButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById(R.id.map);
        floatingCameraButton = (FloatingActionButton)  view.findViewById(R.id.onUseCamera);

        tracker = new GPSTracker(getContext());
        cameraManager = new CameraManager(this.getActivity());

        floatingCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraManager.takePhoto();
            }
        });
        initializeMap();

        setZoom();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initializeMap() {

        map.invalidate();

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

    }

    private void setZoom() {

        GeoPoint startPoint;
        IMapController mapController;

        mapController = map.getController();

        if(tracker.canGetLocation()) {
            startPoint = new GeoPoint(tracker.latitude, tracker.longitude);
            mapController.setZoom(15);
        } else {
            tracker.showSettingsAlert();
            startPoint = new GeoPoint(41.390205, 2.154007);
            mapController.setZoom(10);
        }

        mapController.setCenter(startPoint);

    }

}


