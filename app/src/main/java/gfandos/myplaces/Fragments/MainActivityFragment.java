package gfandos.myplaces.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import gfandos.myplaces.Activities.MainActivity;
import gfandos.myplaces.Activities.DetailActivity;
import gfandos.myplaces.Pojo.Picture;
import gfandos.myplaces.R;
import gfandos.myplaces.Utils.CameraManager;
import gfandos.myplaces.Utils.GPSTracker;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;
    public GPSTracker tracker;
    private CameraManager cameraManager;
    private FloatingActionButton floatingCameraButton;
    private FloatingActionButton floatingVideoButton;
    private RadiusMarkerClusterer markers;
    static final int REQUEST_TAKE_PHOTO = 100;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById(R.id.map);
        floatingCameraButton = (FloatingActionButton)  view.findViewById(R.id.onUseCamera);
        floatingVideoButton = (FloatingActionButton)  view.findViewById(R.id.onUseVideo);

        tracker = new GPSTracker(getContext());
        cameraManager = new CameraManager(((MainActivity)getActivity()));

        floatingCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraManager.takePhoto();
            }
        });

        floatingVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraManager.takeVideo();
            }
        });



        initializeMap();

        setZoom();

        return view;
    }


    public void putMarkers(Picture p) {

        setupMarkerOverlay();
        addMarker(p);

    }

    private void setupMarkerOverlay() {

        markers = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(markers);
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();

        markers.setIcon(clusterIcon);
        markers.setRadius(100);

    }

    private void addMarker(Picture p) {

        final String data = p.getPath() + "º" + p.getName() + "º" + p.getType() + "º" + p.getDescription() + "º" + p.getMedia();

        Marker marker = new Marker(map);
        GeoPoint pos = new GeoPoint(
                p.getLatitude(),
                p.getLongitude()
        );
        marker.setPosition(pos);

        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

//        marker = setIcon(marker, stations.get(i));

//            marker.setIcon(getResources().getDrawable(R.drawable.parking));


        marker.setTitle(p.getName());
//        marker.setImage();
        marker.setAlpha(0.6f);

        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker arg0, MapView arg1) {

                System.out.println("Marker clicked");

                Intent intent = new Intent(((MainActivity)getActivity()), DetailActivity.class);
                intent.putExtra("EXTRA_PICTURE", data);
                startActivity(intent);

                return false;
            }
        });

        markers.add(marker);


        markers.invalidate();
        map.invalidate();

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


