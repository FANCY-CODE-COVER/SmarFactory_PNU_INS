package com.example.android.glass.cardsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.glass.cardsample.data.LoginData;
import com.example.android.glass.cardsample.data.RetrofitInterface;
import com.example.android.glass.cardsample.menu.CameraActivity;
import com.example.glass.ui.GlassGestureDetector;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.glass.cardsample.menu.CameraActivity.QR_SCAN_RESULT;
import static com.example.android.glass.cardsample.MainActivity.base_url;
public class LoginActivity extends BaseActivity {

    private static final int REQUEST_CODE = 105;
    private TextView textView2;

    public static final String QR_SCAN_RESULT_2 = "SCAN_RESULT";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private String login_result = "";
    private String success_word = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        textView2 = findViewById(R.id.textView2);
    }

    /**
     * Shows the detected {@link String} if the QR code was successfully read.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                final String qrData = data.getStringExtra(QR_SCAN_RESULT);
                Log.d("qrlogin", qrData);

                //애뮬레이터 : http://10.0.2.2:4903
                //디바이스 : http://172.xx.x.x:4903
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitInterface service = retrofit.create(RetrofitInterface.class);

                Map<String, Object> login_map = new HashMap<>();
                String[] login_info = qrData.split("======");
                String[] user_id = login_info[1].split(":");
                String[] password = login_info[2].split(":");
                String[] access_token = login_info[3].split(":");
                String[] refresh_token = login_info[4].split(":");
                try {
                    login_map.put("user_id",user_id[1]);
                    login_map.put("password",password[1]);
                } catch (Exception e) {
                    Log.d("login failed","id or pwd null");
                }


                Call<LoginData> call = service.getLoginData(login_map);
                Log.d("sendinfo",user_id[1]);
                Log.d("sendinfo",password[1]);
                Log.d("sendinfo",access_token[1]);
                call.enqueue(new Callback<LoginData>() {
                    @Override
                    public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                        try {
                            login_result = response.body().getResult();
                            Log.d("onResponse login", login_result);

                            if(login_result.toString().equals(success_word)) {
                                Log.d("ifsuccess", login_result);
                                final Intent intent = new Intent();
                                intent.putExtra(QR_SCAN_RESULT_2, user_id[1]);
                                intent.putExtra(ACCESS_TOKEN,access_token[1]);
                                Log.d("ACCESS_TOKEN login",access_token[1]);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                            else {
                                Log.d("ifnotsuccess", login_result);
                                textView2.setText("로그인에 실패하였습니다. 다시 시도해주세요");
                            }
                        } catch(Exception e) {
                            Log.d("onResponse error", response.body().toString());
                            textView2.setText("로그인에 실패하였습니다. 다시 시도해주세요");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginData> call, Throwable t) {
                        Log.d("onFailure in Login", t.getMessage());
                        textView2.setText("로그인에 실패하였습니다. 다시 시도해주세요");

                    }
                });
            }
        }
    }

    /**
     * Hides previously shown QR code string and starts scanning QR Code on {@link GlassGestureDetector.Gesture#TAP}
     * gesture. Finishes application on {@link GlassGestureDetector.Gesture#SWIPE_DOWN} gesture.
     */
    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                startActivityForResult(new Intent(this, CameraActivity.class), REQUEST_CODE);
                return true;
            case SWIPE_DOWN:
                //finishAffinity();
                super.onGesture(gesture);
                return true;
            default:
                return false;
        }
    }
}
