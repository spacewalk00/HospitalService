package ddwucom.mobile.finalproject.ma01_20201017.medicaldata;

import android.util.Log;
import android.widget.Toast;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.util.List;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Body;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.HospRoot;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Items;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchHospitalManager {
    final static String TAG = "SearchHospitalManager";

    private String dataKey;

    private Retrofit retrofit;
    private Retrofit retrofitAll;

    private INearHospitalService iNearHospitalService;
    private INearAllHospitalService iNearAllHospitalService;

    private OnSearchHospitalResult onSearchHospitalResult;

    public SearchHospitalManager(String key) {
        this.dataKey = key;
        if(retrofit == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://apis.data.go.kr/")
                        .addConverterFactory(TikXmlConverterFactory.create(new TikXml.Builder().exceptionOnUnreadXml(false).build()))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(retrofitAll == null) {
            try {
                retrofitAll= new Retrofit.Builder()
                        .baseUrl("http://apis.data.go.kr/")
                        .addConverterFactory(TikXmlConverterFactory.create(new TikXml.Builder().exceptionOnUnreadXml(false).build()))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        iNearHospitalService = retrofit.create(INearHospitalService.class);
        iNearAllHospitalService = retrofitAll.create(INearAllHospitalService.class);
    }

    public void searchHospBasic(Double latitude, Double longitude, int radius, String departNum) {
        String la = latitude.toString();
        String lo = longitude.toString();

        Call<HospRoot> apiCall;
        if(departNum.equals("100")) {
            apiCall = iNearAllHospitalService.getAllHospBasisInfo(dataKey, lo, la, radius);
        }
        else {
            apiCall = iNearHospitalService.getHospBasisInfo(dataKey, lo, la, radius, departNum);
        }

        apiCall.enqueue(hospbasicInfoCallback);
        Log.d(TAG, String.format("Search hospitals at %s %s with %d in %s", la, lo, radius, departNum));
    }

    Callback<HospRoot> hospbasicInfoCallback = new Callback<HospRoot>() {
        @Override
        public void onResponse(Call<HospRoot> call, Response<HospRoot> response) {
            if(response.isSuccessful()) {
                HospRoot hospitals = response.body();
                Body body = hospitals.getBody();
                Log.d(TAG, "병원 수"+body.getTotalCount());
                Items items = body.getItems();
                List<Item> hospitalList = items.getItem();
                Log.d(TAG, "주변 병원 정보"+hospitalList);
                onSearchHospitalResult.onSearchHospitalResult(hospitalList);
                if(hospitalList.size() == 0) {
                    Log.e(TAG, "주변에 해당 병원이 없습니다.");
                }
            }
        }

        @Override
        public void onFailure(Call<HospRoot> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public void setOnSearchHospitalResult(OnSearchHospitalResult onSearchHospitalResult) {
        this.onSearchHospitalResult = onSearchHospitalResult;
    }

}
