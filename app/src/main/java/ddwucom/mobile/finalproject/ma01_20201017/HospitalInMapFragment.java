package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.OnSearchHospitalResult;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.SearchHospitalManager;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;

public class HospitalInMapFragment extends Fragment {
    String DATA_API_KEY = BuildConfig.DATA_API_KEY;
    final String TAG = "HospitalInMapFragment";
    private String departNum;
    FusedLocationProviderClient flpClient;
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private Button btn;
    private SearchHospitalManager searchHospitalManager;

    private String myLatitude = "37.604094";
    private String myLongtitude = "127.042463";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hospital_in_map, container, false);

        Bundle bundle = getArguments();
        departNum = bundle.getString("departNum", "100");

        flpClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        mapView = view.findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapReadyCallback);

        btn = view.findViewById(R.id.nearHosp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.nearHosp:
                        searchHospitalManager.searchHospBasic(Double.parseDouble(myLatitude), Double.parseDouble(myLongtitude), 1000, departNum);
                        break;
                }
            }
        });

        searchHospitalManager = new SearchHospitalManager(DATA_API_KEY);
        searchHospitalManager.setOnSearchHospitalResult(new OnSearchHospitalResult() {
            @Override
            public void onSearchHospitalResult(List<Item> hospitals) {
                if(hospitals.size() == 0) {
                    Toast.makeText(getActivity(), "주변에 해당 병원이 없습니다!", Toast.LENGTH_SHORT).show();
                }
                for(int i=0; i<hospitals.size(); i++) {
                    MarkerOptions options = new MarkerOptions()
                            .title(hospitals.get(i).getYadmNm())
                            .position(new LatLng(hospitals.get(i).getyPos(), hospitals.get(i).getxPos()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                    Marker marker = mGoogleMap.addMarker(options);
                    marker.setTag(hospitals.get(i));
                }
            }
        });
        return view;
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap;

            mGoogleMap.setMyLocationEnabled(true);

            getLastLocation();
            LatLng latLng = new LatLng(Double.parseDouble(myLatitude), Double.parseDouble(myLongtitude));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    Item item = (Item) marker.getTag();
                    callDetailActivity(item);
                }
            });
        }
    };

    LocationCallback mLocCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location loc : locationResult.getLocations()) {
                double lat = loc.getLatitude();
                double lng = loc.getLongitude();

//                LatLng currentLoc = new LatLng (lat, lng);

                myLatitude = Double.toString(lat);
                myLongtitude = Double.toString(lng);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        flpClient.requestLocationUpdates(
                getLocationRequest(),
                mLocCallback,
                Looper.getMainLooper()
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        flpClient.removeLocationUpdates(mLocCallback);
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void callDetailActivity(Item item) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
        intent.putExtra("hosp", item);
        startActivity(intent);
    }

    private void getLastLocation() {
        flpClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // 최종 위치정보 수신 시 수행할 동작 지정
                        if(location != null) {
                            double latitude = location.getLatitude();
                            double longtitude = location.getLongitude();
                            myLatitude = Double.toString(latitude);
                            myLongtitude = Double.toString(longtitude);
                        } else {
                            Toast.makeText(getActivity(), "No location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        flpClient.getLastLocation().addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unknown");
                    }
                }
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}