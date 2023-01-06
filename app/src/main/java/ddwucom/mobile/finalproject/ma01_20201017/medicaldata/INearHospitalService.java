package ddwucom.mobile.finalproject.ma01_20201017.medicaldata;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.HospRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INearHospitalService {
    //dgsbjtCd -> 진료병원유형 //xPos: 경도, yPos: 위도
    @GET("/B551182/hospInfoServicev2/getHospBasisList")
    Call<HospRoot> getHospBasisInfo(@Query("serviceKey")String serviceKey,
                                    @Query("xPos")String xPos, @Query("yPos")String yPos,
                                    @Query("radius")int radius, @Query("dgsbjtCd")String dgsbjtCd);
}
