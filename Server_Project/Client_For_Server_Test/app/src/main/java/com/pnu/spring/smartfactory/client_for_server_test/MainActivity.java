package com.pnu.spring.smartfactory.client_for_server_test;

import android.annotation.SuppressLint;
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
import com.kakao.auth.*;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.AppFriendOrder;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.Category;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.DataDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     Button btnStartApp, btnLogout, btnUnlink,
            btnFriend, btnData, btnMessage,
            btn_token, btnTest1, btnTest2,  btnTest3;
     EditText editIPAddr;
     EditText editCategroyId;
     TextView textView;
     NetworkService networkService;
    List<String> uuids;
    private String access_token="";

    // 세션 콜백 구현
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            Log.d("KAKAO_API", Session.getCurrentSession().getAccessToken());

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         uuids  = new ArrayList<String>();
        setContentView(R.layout.activity_main);

        btnStartApp= (Button) findViewById(R.id.btn_start_app) ;
        btnLogout= (Button) findViewById(R.id.btn_logout) ;
        btnUnlink= (Button) findViewById(R.id.btn_unlink) ;
        btnFriend= (Button) findViewById(R.id.btn_friend) ;
        btnData= (Button) findViewById(R.id.btn_data) ;
        btnMessage = (Button) findViewById(R.id.btn_message) ;
        btn_token= (Button) findViewById(R.id.btn_token) ;
        btnTest1= (Button) findViewById(R.id.btn_test1) ;
        btnTest2 = (Button) findViewById(R.id.btn_test2) ;
        btnTest3 = (Button) findViewById(R.id.btn_test3) ;

        btnStartApp.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnUnlink.setOnClickListener(this);
        btnFriend.setOnClickListener(this);
        btnData.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btn_token.setOnClickListener(this);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);

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
        if (id==R.id.btn_start_app){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class) ;
            startActivity(intent) ;
        }
        else if (id == R.id.btn_logout){
            doLogout();
        }
        else if (id == R.id.btn_unlink){
            doUnlink();
        }
        if (id ==R.id.btn_friend){
            requestFriend();
        }
        if (id ==R.id.btn_data){
            requestDatas();
        }
        else if (id == R.id.btn_message) {

        }//end btn message
        else if (id == R.id.btn_token) {
            access_token=Session.getCurrentSession().getAccessToken();
        }

        else if (id == R.id.btn_test1) {

        }
        else if (id == R.id.btn_test2) {

        }

        else if (id == R.id.btn_test3) {

        }
    }

    private void doUnlink() {
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

    private void doLogout() {
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO_API", "로그아웃 완료");
                    }
                });
    }

    private void requestFriend() {
        // 조회 요청
        AppFriendContext context =
                new AppFriendContext(AppFriendOrder.NICKNAME, 0, 100, "asc");

        // 친구권한 받기
        KakaoTalkService.getInstance()
                .requestAppFriends(context, new TalkResponseCallback<AppFriendsResponse>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "친구 조회 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(AppFriendsResponse result) {
                        Log.i("KAKAO_API", "친구 조회 성공");

                        for (AppFriendInfo friend : result.getFriends()) {
                            Log.d("KAKAO_API", friend.toString());
                           // uuids.add(friend.getUUID());
                            // 메시지 전송 시 사용

                        }
                    }
                });
    }//end requestFriends

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
    }//end requestDatas

    public void doLogin() {// 로그인하기
        Call<Void> joinContentCall=networkService.doLogin();
        joinContentCall.enqueue(new Callback<Void>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //성공
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
            public void onFailure(Call<Void> call, Throwable t) {
                //실패
            }
        });
    }//end doLogin()


}
