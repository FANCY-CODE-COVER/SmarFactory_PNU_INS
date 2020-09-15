package inc.app.mes.ui.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.AppFriendOrder;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.exception.KakaoException;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import inc.app.mes.DTO.TokenManager;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.util.EnDecrypt;
import inc.app.mes.util.PreferenceManager;
import inc.app.mes.util.QRCodeUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_RECORD_AUDIO = 12344;
    private SettingViewModel settingViewModel;
    private NetworkService networkService;
    private LinearLayout settingPage, kakaoPage, qrPage, voiceMessagePage;
    private LoginButton kakaoLoginBtn;
    private Button kakaoAccountManageBtn, QRGenerateBtn, voiceMessageBtn;
    private Button friendAuthBtn, unlinkBtn, logoutBtn;
    private ImageView QRImageView;
    private ImageButton voiceRecogBtn;
    private TextView voiceMessage;
    private Intent i;
    private SpeechRecognizer mRecognizer;
    private String speechText;

    private Button changeIPBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        networkService = MyApplication.getInstance().getNetworkService();
        Session.getCurrentSession().addCallback(sessionCallback);
        speechText="";
        // This callback will only be called when MyFragment is at least Started.
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(settingPage.getVisibility()==View.INVISIBLE) {
                    settingPage.setVisibility(View.VISIBLE);
                    kakaoPage.setVisibility(View.INVISIBLE);
                    qrPage.setVisibility(View.INVISIBLE);
                    voiceMessagePage.setVisibility(View.INVISIBLE);
                }
                else{
                    setEnabled(false);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mRecognizer.setRecognitionListener(listener);


        changeIPBtn=(Button) root.findViewById(R.id.btn_change_ip_addr);
        changeIPBtn.setOnClickListener(this);
        settingPage=(LinearLayout) root.findViewById(R.id.setting_page);
        kakaoLoginBtn=(LoginButton) root.findViewById(R.id.login_button);
        kakaoAccountManageBtn=(Button)root.findViewById(R.id.btn_kakao_account_manage);
        boolean kakaoLogined=false;
        kakaoLogined= PreferenceManager.getBoolean(getContext(), "kakao_login");
        if( kakaoLogined)
        {
            kakaoAccountManageBtn.setVisibility(View.VISIBLE);
            kakaoLoginBtn.setVisibility(View.INVISIBLE);
        }
        else
        {
            kakaoAccountManageBtn.setVisibility(View.INVISIBLE);
            kakaoLoginBtn.setVisibility(View.VISIBLE);
        }


        QRGenerateBtn=(Button) root.findViewById(R.id.btn_qr_generate);
        voiceMessageBtn=(Button) root.findViewById(R.id.btn_voice_message);
        kakaoLoginBtn.setOnClickListener(this);
        kakaoAccountManageBtn.setOnClickListener(this);
        QRGenerateBtn.setOnClickListener(this);
        voiceMessageBtn.setOnClickListener(this);

        kakaoPage=(LinearLayout) root.findViewById(R.id.kakao_page);
        friendAuthBtn=(Button) root.findViewById(R.id.btn_friend_auth);
        unlinkBtn= (Button) root.findViewById(R.id.btn_unlink);
        logoutBtn=(Button) root.findViewById(R.id.btn_logout);
        friendAuthBtn.setOnClickListener(this);
        unlinkBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        qrPage=(LinearLayout) root.findViewById(R.id.qr_page);
        QRImageView= (ImageView) root.findViewById(R.id.imageview_QR);

        voiceMessagePage=(LinearLayout) root.findViewById(R.id.voice_message_page);
        voiceRecogBtn=(ImageButton) root.findViewById(R.id.btn_voice_recog);
        voiceMessage=(TextView) root.findViewById(R.id.voice_message);

        voiceRecogBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_change_ip_addr:

                break;
            case R.id.login_button:
                break;
            case R.id.btn_kakao_account_manage:
                settingPage.setVisibility(View.INVISIBLE);
                kakaoPage.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_qr_generate:
                settingPage.setVisibility(View.INVISIBLE);
                qrPage.setVisibility(View.VISIBLE);
                generateQRCode();
                break;
            case R.id.btn_voice_message:
                settingPage.setVisibility(View.INVISIBLE);
                voiceMessagePage.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_friend_auth:
                requestFriend();
                break;
            case R.id.btn_unlink:
                doUnlink();
                PreferenceManager.setBoolean(getContext(), "kakao_login", false);
                break;
            case R.id.btn_logout:
                doLogout();
                PreferenceManager.setBoolean(getContext(), "kakao_login", false);
                break;
            case R.id.btn_voice_recog:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
                    //권한을 허용하지 않는 경우
                } else {
                    //권한을 허용한 경우
                    try {

                        mRecognizer.startListening(i);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }//end onClink

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            System.out.println("onReadyForSpeech.........................");
        }
        @Override
        public void onBeginningOfSpeech() {
//            voiceMessage.setText( "지금부터 말을 해주세요!");
            System.out.println("지금부터 말을 해주세요!");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            System.out.println("onRmsChanged.........................");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            System.out.println("onBufferReceived.........................");
        }

        @Override
        public void onEndOfSpeech() {
            System.out.println("onEndOfSpeech.........................");
        }

        @Override
        public void onError(int error) {
            voiceMessage.setText( "음성 인식 실패");
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            System.out.println("onPartialResults.........................");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            System.out.println("onEvent.........................");
        }

        @Override
        public void onResults(Bundle results) {
            voiceMessage.setText("음성인식 종료");
            System.out.println("음성인식 종료");
            String key= "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            Log.i("KAKAO",  rs[0]);
            speechText=rs[0];

            String access_token = PreferenceManager.getString(getContext(), "access_token");
            String refresh_token = PreferenceManager.getString(getContext(), "refresh_token");
            isTokenAvailable(access_token, refresh_token);
            Log.i("Token", "토큰 확인");

            sendMessage(access_token,rs[0]);
            mRecognizer.stopListening();

//            mRecognizer.startListening(i);
        }
    };

    private void generateQRCode() {
        EnDecrypt enDecrypt = new EnDecrypt();
        String user_id="test";
        String password="test";
        String access_token="test";
        String refresh_token="test";
        user_id= PreferenceManager.getString(getContext(), "user_id");
        password=PreferenceManager.getString(getContext(), "password");
        access_token=PreferenceManager.getString(getContext(), "access_token");
        refresh_token=PreferenceManager.getString(getContext(), "refresh_token");
        enDecrypt.setUser_id(user_id);
        enDecrypt.setPassword(password);
        enDecrypt.setAccess_token(access_token);
        enDecrypt.setRefresh_token(refresh_token);
        String barcode=enDecrypt.makePlainText();
//                String barcode=enDecrypt.Encrypt(enDecrypt.makePlainText());
//                Log.i("KAKAO",enDecrypt.makePlainText());
//                String barcode2=enDecrypt.Decrypt("@B>FB>pg?>FB>D@UB\\XXVUI;FB>D@BBLHFZVdUXPFU=h{kQI;wYp<UTwm:Ra=rol5{JQo[3nQBgpLDoWJtqi<VBJFG7h65\\:BD@B>FBSLIWF\\M`[RPFW?UVDdCV5gkvL7KQZ{LjZaRJjO\\4lz5i<pcpwu_Ttqk>RHDFG9j2;V9hFB>D@B}�");
//                Log.i("KAKAO",barcode2);
        Bitmap bitmap = QRCodeUtil.encodeAsBitmap(barcode, 600, 600); //입력값 받아와서 파라미터로 전달합니다. 인수: 값, 가로, 세로
        QRImageView.setImageBitmap(bitmap); //변환 후 반환된 비트맵 이미지 지정
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
                        }
                    }
                });
    }//end requestFriends

    private void doLogout() {
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO_API", "로그아웃 완료");
                    }
                });
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

    private void isTokenAvailable(String access_token, String refresh_token){
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", access_token);
        param.put("refresh_token", refresh_token);
        Log.i("KAKAO", "access_token"+ access_token);
        Log.i("KAKAO", "refresh_token"+ refresh_token);
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
            String access_token = PreferenceManager.getString(getContext(), "access_token");
            String refresh_token = PreferenceManager.getString(getContext(), "refresh_token");
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
            access_token = PreferenceManager.getString(getContext(), "access_token");
            refresh_token = PreferenceManager.getString(getContext(), "refresh_token");

            sendMessage(access_token, speechText);
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
            PreferenceManager.setString(getContext(), "access_token", tokenManager.getAccess_token());
            PreferenceManager.setString(getContext(), "refresh_token", tokenManager.getRefresh_token());

            String access_token = PreferenceManager.getString(getContext(), "access_token");
            String refresh_token = PreferenceManager.getString(getContext(), "refresh_token");

            sendMessage(access_token, speechText);
            Log.i("Token", "메시지 보내기");
        }//end onPostExecute
    }

    public void sendMessage(String access_token, String raw_string) {//
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", access_token);
        param.put("rawstring", raw_string);
        Call<Void> joinContentCall = networkService.sendMessageByVoice(param);
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
            if( !access_token.isEmpty() && !refresh_token.isEmpty()) {
                PreferenceManager.setString(getContext(), "access_token", access_token);
                PreferenceManager.setString(getContext(), "refresh_token", refresh_token);
            }
            PreferenceManager.setBoolean(getContext(), "kakao_login", true);
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}