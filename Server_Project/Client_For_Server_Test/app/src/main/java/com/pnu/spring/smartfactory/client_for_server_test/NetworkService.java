package com.pnu.spring.smartfactory.client_for_server_test;

import com.pnu.spring.smartfactory.client_for_server_test.DTO.Category;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.DataDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.Message;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {
    @POST("getdatas")
    Call<List<DataDAO>> getDatas(@Body Category category);

    @GET("beforelogin")
    Call<Void> doLogin();

    @POST("sendmessage")
    Call<Void> sendMessage(@Body Message message);

    @POST("getfriend")
    Call<Void> getFriend(@Body Message message);

    @POST("tokenavailable")
    Call<TokenManager> isTokenAvailable(@Body TokenManager tm);

    @POST("getnewtoken")
    Call<TokenManager> getNewToken(@Body TokenManager tm);
}
