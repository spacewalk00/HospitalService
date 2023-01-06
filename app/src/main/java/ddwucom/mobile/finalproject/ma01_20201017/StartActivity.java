package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    final String TAG = "StartActivity";
    final int REQ_PERMISSION_CODE = 100;
    Map<String, String> medicalDepart;
    String selectedDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        medicalDepart = new HashMap<>();
        medicalDepart.put("일반의", "00");
        medicalDepart.put("내과", "01");
        medicalDepart.put("신경과", "02");
        medicalDepart.put("정신건강의학과", "03");
        medicalDepart.put("외과", "04");
        medicalDepart.put("정형외과", "05");
        medicalDepart.put("신경외과", "06");
        medicalDepart.put("심장혈관흉부외과", "07");
        medicalDepart.put("성형외과", "08");
        medicalDepart.put("마취통증의학과", "09");
        medicalDepart.put("산부인과", "10");
        medicalDepart.put("소아청소년과", "11");
        medicalDepart.put("안과", "12");
        medicalDepart.put("이비인후과", "13");
        medicalDepart.put("피부과", "14");
        medicalDepart.put("비뇨의학과", "15");
        medicalDepart.put("영상의학과", "16");
        //...
        medicalDepart.put("전체", "100");
        medicalDepart.put(null, "100");

        final Spinner spinner_field = findViewById(R.id.spinner_field);
        String[] str = getResources().getStringArray(R.array.spinnerArray);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, str);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_field.setAdapter(adapter);

        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(spinner_field.getSelectedItemPosition() > 0) {
                    selectedDepart = spinner_field.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDepart = null;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        selectedDepart = null;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, MainActivity.class);
                Log.d(TAG, selectedDepart + "");
                String category = medicalDepart.get(selectedDepart);
                intent.putExtra("medicalDepart", category);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "위치권한 획득 완료", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext().getApplicationContext(), "위치권한 미획득", Toast.LENGTH_SHORT).show();
                }
        }
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // 권한이 있을 경우 수행할 동작
           // Toast.makeText(getApplicationContext(),"Permissions Granted", Toast.LENGTH_SHORT).show();
        } else {
            // 권한 요청
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION_CODE);
            }
        }
    }
}