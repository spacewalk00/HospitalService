package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    HospitalInMapFragment hospitalInMapFragment;
    FavoriteFragment favoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hospitalInMapFragment = new HospitalInMapFragment();
        favoriteFragment = new FavoriteFragment();

        Intent intent = getIntent();
        String departNum = intent.getStringExtra("medicalDepart");

        Bundle bundle = new Bundle();
        bundle.putString("departNum", departNum);
        hospitalInMapFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, hospitalInMapFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.nav_bottom);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.search_hospital:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, hospitalInMapFragment).commit();
                        return true;
                    case R.id.scrap:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, favoriteFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}