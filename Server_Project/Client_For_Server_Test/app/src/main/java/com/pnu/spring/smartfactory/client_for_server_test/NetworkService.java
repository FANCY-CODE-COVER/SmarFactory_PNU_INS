package com.pnu.spring.smartfactory.client_for_server_test;

import com.pnu.spring.smartfactory.client_for_server_test.DTO.DataDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.FacilityDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.TokenManager;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {
    @POST("getdatas")
    Call<List<DataDAO>> getDatas(@Body Map<String, Object> param);

    @GET("beforelogin")
    Call<Void> doLogin();

    @POST("sendmessagetome")
    Call<Void> sendMessageToMe(@Body Map<String, Object> param);

    @POST("getfriend")
    Call<Void> getFriend(@Body Map<String, Object> param);

    @POST("tokenavailable")
    Call<TokenManager> isTokenAvailable(@Body Map<String, Object> param);

    @POST("getnewtoken")
    Call<TokenManager> getNewToken(@Body Map<String, Object> param);

    @POST("getfacilitydetail")
    Call<List<FacilityDAO>> getFacilityDetail(@Body Map<String, Object> param);


}
