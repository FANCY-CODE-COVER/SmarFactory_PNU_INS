package com.example.android.glass.cardsample.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.FacilityDAO;
import com.example.android.glass.cardsample.data.RetrofitInterface;
import com.example.glass.ui.GlassGestureDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.glass.cardsample.menu.CameraActivity.QR_SCAN_RESULT;
import static com.example.android.glass.cardsample.MainActivity.base_url;

public class EquipQRScan extends BaseActivity {
    private static final int REQUEST_CODE = 105;
    public static final String EQUIPMENT_RESULT = "SCAN_RESULT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

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
                Log.d("qrData", qrData);

                //facility_map.put("facility_cd",qrData);

                //애뮬레이터 : http://10.0.2.2:4903
                //디바이스 : http://172.xx.x.x:4903
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitInterface service = retrofit.create(RetrofitInterface.class);


                Call<List<FacilityDAO>> call = service.getFacilityDetail(qrData);
                call.enqueue(new Callback<List<FacilityDAO>>() {

                    @Override
                    public void onResponse(Call<List<FacilityDAO>> call, Response<List<FacilityDAO>> response) {
                        try {
                            List<FacilityDAO> dataDAO = response.body();
                            StringBuilder result= new StringBuilder();
                            for(int i = 0; i<dataDAO.size(); ++i) {
                                result.append(dataDAO.get(i).getFacility_nm()).append("\n\r");
                                Log.d("dataDAO", String.valueOf(result));
                            }

                            Intent intent = new Intent(getApplicationContext(), EquipMenuActivity.class);
                            intent.putExtra(EQUIPMENT_RESULT, qrData);
                            setResult(Activity.RESULT_OK, intent);
                            startActivity(intent);
                            finish();
                        } catch(Exception e) {
                            Log.d("onResponse exception", response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FacilityDAO>> call, Throwable t) {
                        Log.d("onFailure in SearchInfo", t.getMessage());
//                        Intent intent = new Intent(getApplicationContext(), EquipMenuActivity.class);
//                        intent.putExtra(EQUIPMENT_RESULT, qrData);
//                        setResult(Activity.RESULT_OK, intent);
//                        startActivity(intent);
//                        finish();
                    }
                });

            }
        }
    }
    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case TAP:
                startActivityForResult(new Intent(this, CameraActivity.class), REQUEST_CODE);
                return true;
            case SWIPE_DOWN:
                finish();
                return true;
            default:
                return false;
        }
    }
}
