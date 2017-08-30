package me.tatocaster.bbtest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.tatocaster.bbtest.R;
import me.tatocaster.bbtest.model.Coordinate;

/**
 * Created by tatocaster on 8/30/17.
 */

public class MapFragment extends BaseFragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";
    private static final String BUNDLE_SERIALIZABLE_OBJECT_KEY = "BUNDLE_SERIALIZABLE_OBJECT_KEY";
    private GoogleMap mGoogleMap;
    private Coordinate mSelectedCoordinates;

    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            mSelectedCoordinates = (Coordinate) bundle.getSerializable(BUNDLE_SERIALIZABLE_OBJECT_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, null);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        mGoogleMap = googleMap;

        if (mSelectedCoordinates != null) {
            gotoLocation();
        }
    }

    private void gotoLocation() {
        if (mGoogleMap != null) {
            LatLng pos = new LatLng(mSelectedCoordinates.lat, mSelectedCoordinates.lon);
            mGoogleMap.addMarker(new MarkerOptions().position(pos));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f));
        }
    }

    public static MapFragment newInstance(Coordinate coordinates) {
        MapFragment f = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_SERIALIZABLE_OBJECT_KEY, coordinates);
        f.setArguments(args);
        return f;
    }
}