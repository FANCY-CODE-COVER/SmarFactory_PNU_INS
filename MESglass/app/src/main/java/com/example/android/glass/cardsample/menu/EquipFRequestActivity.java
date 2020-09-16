package com.example.android.glass.cardsample.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.FRequestDAO;
import com.example.android.glass.cardsample.data.FacilityDAO;
import com.example.android.glass.cardsample.data.RetrofitInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.glass.cardsample.MainActivity.base_url;
import static com.example.android.glass.cardsample.menu.EquipQRScan.EQUIPMENT_RESULT;

public class EquipFRequestActivity extends BaseActivity {
    private String facility_cd;
    private TextView val1,val2,val3,val4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_frequest_layout);

        facility_cd = getIntent().getStringExtra(EQUIPMENT_RESULT);

        val1 = (TextView)findViewById(R.id.freq_val1) ;
        val2 = (TextView)findViewById(R.id.freq_val2) ;
        val3 = (TextView)findViewById(R.id.freq_val3) ;
        val4 = (TextView)findViewById(R.id.freq_val4) ;
        Map<String, Object> facility_map = new HashMap<>();
        facility_map.put("facility_cd", facility_cd);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);


        Call<List<FRequestDAO>> call = service.getFRequestDetail(facility_map);
        call.enqueue(new Callback<List<FRequestDAO>>() {

            @Override
            public void onResponse(Call<List<FRequestDAO>> call, Response<List<FRequestDAO>> response) {
                if(response.isSuccessful()) {
                    List<FRequestDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0) {
                        val1.setText(dataDAO.get(0).getRemark());
                        val2.setText(dataDAO.get(0).getEmployee_nm());
                        val3.setText(dataDAO.get(0).getReq_no());
                        val4.setText(dataDAO.get(0).getStatus());
                    }
                }
                else {
                    Log.d("response errrror", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<FRequestDAO>> call, Throwable t) {
                Log.d("onFailure in EquipFReq", t.getMessage());
            }
        });
    }
}
