package com.pnu.spring.smartfactory.client_for_server_test;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;
import com.kakao.auth.*;
import com.kakao.util.exception.KakaoException;
import com.pnu.spring.smartfactory.client_for_server_test.DAO.Category;
import com.pnu.spring.smartfactory.client_for_server_test.DAO.DataDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     Button button;
     EditText editIPAddr;
     EditText editCategroyId;
     TextView textView;
     NetworkService networkService;

    // 세션 콜백 구현
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button) ;
        button.setOnClickListener(this);
        editIPAddr = (EditText) findViewById(R.id.edit_ipaddr) ;
        editCategroyId= (EditText) findViewById(R.id.edit_category_id) ;
        textView=(TextView) findViewById(R.id.textView);
        // 세션 콜백 등록
        Session.getCurrentSession().addCallback(sessionCallback);

        networkService = MyApplication.getInstance().getNetworkService();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button)
        {
            requestDatas();
        }
    }

    public void requestDatas(){
        Category join = new Category("A01");

        Call<List<DataDAO>> joinContentCall=networkService.getDatas(join);

        joinContentCall.enqueue(new Callback<List<DataDAO>>(){

            @Override
            public void onResponse(Call<List<DataDAO>> call, Response<List<DataDAO>> response) {
                if(response.isSuccessful()){

                   List<DataDAO> dataDAO=response.body();
                    textView.setText( dataDAO.get(0).getCode());

                }
                else{
                    if(response.code()==500)
                    {

                    }
                    else if(response.code()==503)
                    {

                    }
                    else if(response.code()==401)
                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<DataDAO>> call, Throwable t) {
                //실패
            }
        });
    }

}
