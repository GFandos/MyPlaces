package gfandos.myplaces.Fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import gfandos.myplaces.R;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;
    public Location currentLocation;
    LocationManager locationManager;
    String locationProvider;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById(R.id.map);

        initializeMap();

        setZoom();

//      putMarkers();

        return view;
    }

//    private Location getLocation() {
//        LocationClient locClient = new LocationClient (this, this, this);
//        locClient.Connect();
//    }

    private void initializeMap() {

        map.invalidate();

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

    }

    private void setZoom() {

        GeoPoint startPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());

        IMapController mapController = map.getController();
        mapController.setZoom(13);
        mapController.setCenter(startPoint);

    }

}
