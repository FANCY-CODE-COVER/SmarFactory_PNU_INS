package com.example.android.glass.cardsample.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.LoginData;
import com.example.android.glass.cardsample.data.RetrofitInterface;
import com.example.glass.ui.GlassGestureDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.glass.cardsample.LoginActivity.ACCESS_TOKEN;
import static com.example.android.glass.cardsample.MainActivity.base_url;

public class VoiceRecognitionActivity extends BaseActivity {

    private static final int REQUEST_CODE = 999;
    private static final String TAG = VoiceRecognitionActivity.class.getSimpleName();
    private static final String DELIMITER = "\n";

    private TextView resultTextView;
    private GlassGestureDetector glassGestureDetector;
    private List<String> mVoiceResults = new ArrayList<>(4);
    private String access_token = "ACCESS_TOKEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognition_layout);
        resultTextView = findViewById(R.id.voice_results);
        glassGestureDetector = new GlassGestureDetector(this, this);
        access_token = getIntent().getStringExtra(ACCESS_TOKEN);
        Log.d("access_token1",access_token);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            final List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.d(TAG, "results: " + results.toString());
            if (results != null && results.size() > 0 && !results.get(0).isEmpty()) {
                updateUI(results.get(0));
                Log.d("access_token2",access_token);
                sendMessage(access_token,results.get(0));
            }
        } else {
            Log.d(TAG, "Result not OK");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return glassGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                requestVoiceRecognition();
                return true;
            case SWIPE_DOWN:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void requestVoiceRecognition() {

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void updateUI(String result) {
        if (mVoiceResults.size() >= 4) {
            mVoiceResults.remove(mVoiceResults.size() - 1);
        }
        mVoiceResults.add(0, result);
        final String recognizedText = String.join(DELIMITER, mVoiceResults);
        resultTextView.setText(recognizedText);
    }

    public void sendMessage(String access_token, String raw_string) {
        Map<String, Object> msg_map = new HashMap<>();
        msg_map.put("access_token", access_token);
        Log.d("ac_tok",access_token);
        msg_map.put("rawstring", raw_string);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        Call<Void> call = service.sendMessageByVoice(msg_map);
        call.enqueue(new Callback<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //성공
                    Log.d("MSG", "send success");
                    resultTextView.setText("메세지 전송 완료");
                } else {// 실패시 에러코드들
                    resultTextView.setText("메세지 전송 실패");
                    if (response.code() == 500) {
                        Log.d("MSG err", "500");
                    } else if (response.code() == 503) {
                        Log.d("MSG err", "503");
                    } else if (response.code() == 401) {
                        Log.d("MSG err", "401");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //실패
                Log.d("onFailure message send", t.getMessage());
                resultTextView.setText("메세지 전송 실패");
            }
        });
    }
}
