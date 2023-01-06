package ddwucom.mobile.finalproject.ma01_20201017.medicaldata;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.HospRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INearAllHospitalService {
    @GET("/B551182/hospInfoServicev2/getHospBasisList")
    Call<HospRoot> getAllHospBasisInfo(@Query("serviceKey")String serviceKey, @Query("xPos")String xPos, @Query("yPos")String yPos, @Query("radius")int radius);
}
