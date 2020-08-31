package com.pnu.spring.smartfactory.client_for_server_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.AppFriendOrder;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.util.exception.KakaoException;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.Message;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.TokenManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnFriend, btnSend, btnLogout;
    EditText editRecevier;
    NetworkService networkService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 세션 콜백 등록
        Session.getCurrentSession().addCallback(sessionCallback);
        btnFriend=(Button) findViewById(R.id.btn_friend);
        btnSend=(Button) findViewById(R.id.btn_send);
        btnLogout=(Button) findViewById(R.id.btn_local_logout);

        btnFriend.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        editRecevier=(EditText) findViewById(R.id.edit_receiver);

        networkService = MyApplication.getInstance().getNetworkService();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_friend) {
            requestFriend();
        }
        else if (id == R.id.btn_send) {
            // 1. 여기서 토큰, 보낼사람 이름으로 요청 보낸다.
            // 2. 서버에서 토큰으로 친구 목록 찾는다.
            // 3. 친구 목록에서 원하는 이름 찾아서 보낸다.
            String access_token = PreferenceManager.getString(this, "access_token");
            String refresh_token = PreferenceManager.getString(this, "refresh_token");

            isTokenAvailable(access_token, refresh_token);
            Log.i("Token", "토큰 확인");
        }
        else if (id == R.id.btn_local_logout) {
            PreferenceManager.removeKey(HomeActivity.this, "id");
            PreferenceManager.removeKey(HomeActivity.this, "password");
            finish();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class) ;
            startActivity(intent) ;
        }
    }

    private void isTokenAvailable(String access_token, String refresh_token){
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", access_token);
        param.put("refresh_token", refresh_token);
        param.put("expiresIn", "");
        Call<TokenManager> joinContentCall = networkService.isTokenAvailable(param);
        new TokenAvailableNetworkCall().execute(joinContentCall);
    }

    private class TokenAvailableNetworkCall extends AsyncTask<Call, Void, TokenManager> {
        @Override
        protected TokenManager doInBackground(Call[] params) {
            try {
                Call<TokenManager> call = params[0];
                Response<TokenManager> response = call.execute();
                Log.i("SINSIN", "토큰 검사");
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TokenManager tempTm) {
            boolean result=false;
            if (tempTm.getExpiresIn()!=null) {
                int expire_time = Integer.parseInt(tempTm.getExpiresIn());
                if (expire_time > 3000) {
                    result = true;
                }
            }
            String access_token = PreferenceManager.getString(HomeActivity.this, "access_token");
            String refresh_token = PreferenceManager.getString(HomeActivity.this, "refresh_token");
            //토큰 재발급 쪽으로 프로세스를 넘긴다.
            getNewToken(result, access_token, refresh_token);
        }//end onPostExecute
    }

    private void getNewToken(boolean result, String access_token, String refresh_token){
        if(!result){
            // 토큰이 이용불가능 하면
            // 새로 발급한다.
            Map<String, Object> param = new HashMap<>();
            param.put("access_token", access_token);
            param.put("refresh_token", refresh_token);
            param.put("expiresIn", "");
            Call<TokenManager> joinContentCall = networkService.getNewToken(param);
            new GetNewTokenNetworkCall().execute(joinContentCall);
        }
        else{
            // 토큰이 이용 가능하면 전송
            access_token = PreferenceManager.getString(HomeActivity.this, "access_token");
            refresh_token = PreferenceManager.getString(HomeActivity.this, "refresh_token");
            String receiver = editRecevier.getText().toString();
            String contents="여기에 내용이 들어간다.";
            String btnname="버튼명";
            sendMessage(access_token, refresh_token, receiver, contents, btnname);
            Log.i("Token", "메시지 보내기");
        }
    }

    private class GetNewTokenNetworkCall extends AsyncTask<Call, Void, TokenManager> {

        @Override
        protected TokenManager doInBackground(Call[] params) {
            try {
                Call<TokenManager> call = params[0];
                Response<TokenManager> response = call.execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TokenManager tokenManager) {
            //토큰 업데이트
            assert tokenManager != null;
            PreferenceManager.setString(HomeActivity.this, "access_token", tokenManager.getAccess_token());
            PreferenceManager.setString(HomeActivity.this, "refresh_token", tokenManager.getRefresh_token());

            String access_token = PreferenceManager.getString(HomeActivity.this, "access_token");
            String refresh_token = PreferenceManager.getString(HomeActivity.this, "refresh_token");
            String receiver = editRecevier.getText().toString();
            String contents="여기에 내용이 들어간다.";
            String btnname="버튼명";
            sendMessage(access_token, refresh_token, receiver, contents, btnname);
            Log.i("Token", "메시지 보내기");
        }//end onPostExecute
    }


    private void requestFriend() {// 안드로이드 SDK 친구 요청
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
                    }
                });
    }//end requestFriends

    public void sendMessage(String access_token, String refresh_token, String receiver, String contents, String btnname) {//
        Map<String, Object> param = new HashMap<>();
        param.put("refresh_token", refresh_token);
        param.put("access_token", access_token);
        param.put("receiver", receiver);
        param.put("btnname", btnname);
        param.put("contents", contents);
        Call<Void> joinContentCall = networkService.sendMessageToFriend(param);
        joinContentCall.enqueue(new Callback<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //성공
                    Log.d("Kakao", "보내기 성공");
                } else {// 실패시 에러코드들
                    if (response.code() == 500) {

                    } else if (response.code() == 503) {

                    } else if (response.code() == 401) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //실패
            }
        });
    }

    // 세션 콜백 구현 : 로그인관련
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            String access_token = Session.getCurrentSession().getAccessToken();
            String refresh_token = Session.getCurrentSession().getRefreshToken();
            PreferenceManager.setString(HomeActivity.this, "access_token", access_token);
            PreferenceManager.setString(HomeActivity.this, "refresh_token", refresh_token);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

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
}