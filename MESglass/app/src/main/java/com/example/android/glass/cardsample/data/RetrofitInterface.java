package com.example.android.glass.cardsample.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //로그인
    @POST("login")
    Call<LoginData> getLoginData(@Body Map<String,Object> loginparams);

    //설비 상세 조회
    @POST("getfacilitydetail")
    Call<List<FacilityDAO>> getFacilityDetail(@Body Map<String,Object> facilityparams);

    //설비의 요청 상세 조회
    @POST("getfrequestdetail")
    Call<List<FRequestDAO>> getFRequestDetail(@Body Map<String,Object> frequestparams);

    //설비의 점검 상세 조회
    @POST("getinspectdetail")
    Call<List<InspectDAO>> getInspectDetail(@Body Map<String,Object> inspectparams);

    //설비의 수리 상세 조회
    @POST("getrepairdetail")
    Call<List<RepairDAO>> getRepairDetail(@Body Map<String,Object> repairparams);

    //카카오톡 메세지 보내기
    @POST("sendmessagebyvoice")
    Call<Void> sendMessageByVoice(@Body Map<String, Object> msgparams);
}
