package com.example.android.glass.cardsample.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.FacilityDAO;
import com.example.android.glass.cardsample.data.RetrofitInterface;
import com.example.glass.ui.GlassGestureDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.glass.cardsample.menu.EquipQRScan.EQUIPMENT_RESULT;
import static com.example.android.glass.cardsample.MainActivity.base_url;
public class EquipInfoActivity extends BaseActivity {

    private TextView machine;
    private String facility_cd;
    private TextView val1,val2,val3,val4;

    private String facility_nm, place, state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_info_layout);
        facility_cd = getIntent().getStringExtra(EQUIPMENT_RESULT);
        Log.d("machine_name", facility_cd);

        Map<String, Object> facility_map = new HashMap<>();
        facility_map.put("facility_cd", facility_cd);

        machine = (TextView)findViewById(R.id.machine_name);
        machine.setText(facility_cd);

//        key1 = (TextView)findViewById(R.id.info_key1) ;
//        key2 = (TextView)findViewById(R.id.info_key2) ;
//        key3 = (TextView)findViewById(R.id.info_key3) ;
        val1 = (TextView)findViewById(R.id.info_val1) ;
        val2 = (TextView)findViewById(R.id.info_val2) ;
        val3 = (TextView)findViewById(R.id.info_val3) ;
        val4 = (TextView)findViewById(R.id.info_val4) ;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);


        Call<List<FacilityDAO>> call = service.getFacilityDetail(facility_map);
        call.enqueue(new Callback<List<FacilityDAO>>() {

            @Override
            public void onResponse(Call<List<FacilityDAO>> call, Response<List<FacilityDAO>> response) {
                if(response.isSuccessful()) {

                    List<FacilityDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0) {
                        val1.setText(dataDAO.get(0).getFacility_nm());
                        val2.setText(dataDAO.get(0).getPlace());
                        val3.setText(dataDAO.get(0).getState());
                        val4.setText(dataDAO.get(0).getFacility_type());
                    }
                }
                else {
                    Log.d("response errrror", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<FacilityDAO>> call, Throwable t) {
                Log.d("onFailure in EquipInfo", t.getMessage());
            }
        });

    }


    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case SWIPE_DOWN:
                finish();
                return true;
            default:
                return false;
        }
    }
}
