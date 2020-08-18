package com.pnu.spring.smartfactory.client_for_server_test;

import android.annotation.SuppressLint;
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
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.pnu.spring.smartfactory.client_for_server_test.DAO.Category;
import com.pnu.spring.smartfactory.client_for_server_test.DAO.DataDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     Button button, btnLogout, btnUnlink, btnData;
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

        button= (Button) findViewById(R.id.btn_message) ;
        button.setOnClickListener(this);
        btnLogout= (Button) findViewById(R.id.btn_logout) ;
        btnLogout.setOnClickListener(this);
        btnUnlink= (Button) findViewById(R.id.btn_unlink) ;
        btnUnlink.setOnClickListener(this);
        btnData =(Button) findViewById(R.id.btn_data) ;
        btnData.setOnClickListener(this);
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
        if (id ==R.id.btn_data){
            requestDatas();
        }
        else if (id == R.id.btn_message) {
            // 메시지 나에게 보내기 안드로이드 버전
            AuthService.getInstance()
                    .requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "토큰 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(AccessTokenInfoResponse result ) {
                            Log.i("KAKAO_API", "사용자 아이디: " + result.toString());
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getUserId());
                            Log.i("KAKAO_API", "남은 시간(s): " + result.getExpiresIn());
                            ;
                        }
                    });
        }//end btn message
        else if (id == R.id.btn_logout){
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Log.i("KAKAO_API", "로그아웃 완료");
                        }
                    });
        }
        else if (id == R.id.btn_unlink){
            UserManagement.getInstance()
                    .requestUnlink(new UnLinkResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

                        }
                        @Override
                        public void onSuccess(Long result) {
                            Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                        }
                    });
        }
    }

    public void requestDatas() {// 신신사 서버에서 데이터 받아오기
        Category join = new Category("A01"); // A01 인 자료 요청
        Call<List<DataDAO>> joinContentCall=networkService.getDatas(join);
        joinContentCall.enqueue(new Callback<List<DataDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DataDAO>> call, Response<List<DataDAO>> response) {
                if(response.isSuccessful()){

                   List<DataDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    StringBuilder result= new StringBuilder();
                    for(int i = 0; i<dataDAO.size(); ++i) {
                        result.append(dataDAO.get(i).getCode()).append("\n\r");
                   }
                    textView.setText(result.toString());
                }
                else{// 실패시 에러코드들
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
