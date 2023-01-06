package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;
public class DetailActivity extends AppCompatActivity {
    final static String TAG = "DetailActivity";

    ImageButton starBtn;
    Button btnSave;
    Button btnClose;

    TextView tvKind;
    TextView tvName;
    TextView tvPhone;
    TextView tvAddress;
    TextView tvWeb;
    TextView tvDistance;

    Item item;

    ItemDB db;
    ItemDao dao;

    Item itemResult;
    Boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        starBtn = findViewById(R.id.starButton);
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
            }
        });
        btnSave = findViewById(R.id.btnSaved);
        btnClose = findViewById(R.id.btnClose);

        tvKind = findViewById(R.id.etKind);
        tvName = findViewById(R.id.etName);
        tvPhone = findViewById(R.id.etPhone);
        tvAddress = findViewById(R.id.etAddress);
        tvWeb = findViewById(R.id.etWeb);
        tvDistance = findViewById(R.id.etDistance);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("hosp");
        String kind = item.getClCdNm();
        String name = item.getYadmNm();
        String phone = item.getTelno();
        String address = item.getAddr();
        String web = item.getHospUrl();
        Double distance = item.getDistance();

        tvKind.setText(kind);
        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvWeb.setText(web);
        tvDistance.setText(Double.toString(distance));

        db = ItemDB.getDatabase(this);
        dao = db.itemDao();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                itemResult = dao.getItem(item.getTelno());
                if(itemResult != null) { isExist = true;}
                else { isExist = false; }

                Log.d(TAG, isExist.toString());

                if(isExist) {
                    starBtn.setVisibility(View.GONE);
                    btnSave.setVisibility(View.GONE);
                }
                else {
                    starBtn.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                }
            }
        }).start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaved:
                if(starBtn.isSelected()) {
                    //insert
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            long rowId = dao.insertItem(item);
                            Log.d(TAG, "삽입");
                            Log.i(TAG, "Insert: " + rowId);
                        }
                    }).start();
                }
                break;
            case R.id.btnClose:
                break;
        }
        finish();
    }
}