package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;
public class MemoDetailActivity extends AppCompatActivity {
    final static String TAG = "MemoDetailActivity";

    Button btnSave;
    Button btnClose;

    TextView tvKind;
    TextView tvName;
    TextView tvPhone;
    TextView tvAddress;
    TextView tvWeb;
    TextView tvDistance;
    TextView tvMemo;

    Item item;
    ItemDB db;
    ItemDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        btnSave = findViewById(R.id.btnSaved);
        btnClose = findViewById(R.id.btnClose);

        tvKind = findViewById(R.id.etKind);
        tvName = findViewById(R.id.etName);
        tvPhone = findViewById(R.id.etPhone);
        tvAddress = findViewById(R.id.etAddress);
        tvWeb = findViewById(R.id.etWeb);
        tvDistance = findViewById(R.id.etDistance);
        tvMemo = findViewById(R.id.contentText);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("hospCheck");
        String kind = item.getClCdNm();
        String name = item.getYadmNm();
        String phone = item.getTelno();
        String address = item.getAddr();
        String web = item.getHospUrl();
        Double distance = item.getDistance();
        String memo = item.getMemo();
        Log.d(TAG, memo);
        if(memo == null || memo.equals("")) {
            memo = "";
        }

        tvKind.setText(kind);
        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvWeb.setText(web);
        tvDistance.setText(Double.toString(distance));
        tvMemo.setText(memo);

        db = ItemDB.getDatabase(this);
        dao = db.itemDao();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaved:
                //update
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        int updateCnt= dao.updateItem(tvMemo.getText().toString(), tvPhone.getText().toString());
                        Log.d(TAG, "수정");
                        Log.i(TAG, "update: " + updateCnt);
                    }
                }).start();
                break;
            case R.id.btnClose:
                break;
        }
        finish();
    }
}