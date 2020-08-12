package com.pnu.spring.smartfactory.client_for_server_test;

import com.pnu.spring.smartfactory.client_for_server_test.DAO.Category;
import com.pnu.spring.smartfactory.client_for_server_test.DAO.DataDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {
    @POST("getdatas")
    Call<List<DataDAO>> getDatas(@Body Category category);
}
