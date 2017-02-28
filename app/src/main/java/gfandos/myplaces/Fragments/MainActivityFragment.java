package gfandos.myplaces.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById(R.id.map);

        initializeMap();
        getLocation();

        setZoom();

//      putMarkers();

        return view;
    }

    private void getLocation() {
        Context c = getContext();
        LocationManager locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);

        //Check GPS is enabled
        // Getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {

        } else {
            LocationListener locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            currentLocation = loc;

//        /*------- To get city name from coordinates -------- */
//            String cityName = null;
//            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
//            List<Address> addresses;
//            try {
//                addresses = gcd.getFromLocation(loc.getLatitude(),
//                        loc.getLongitude(), 1);
//                if (addresses.size() > 0) {
//                    System.out.println(addresses.get(0).getLocality());
//                    cityName = addresses.get(0).getLocality();
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
//                    + cityName;
//            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
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

        if(isGPSEnabled) {
            startPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
            mapController = map.getController();
            mapController.setZoom(15);
        } else {
            startPoint = new GeoPoint(41.390205, 2.154007);
            mapController = map.getController();
            mapController.setZoom(6);
        }


        mapController.setCenter(startPoint);

    }

}


