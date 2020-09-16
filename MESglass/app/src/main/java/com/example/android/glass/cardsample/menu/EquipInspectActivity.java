package com.example.android.glass.cardsample.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.FacilityDAO;
import com.example.android.glass.cardsample.data.InspectDAO;
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

public class EquipInspectActivity extends BaseActivity {
    private String facility_cd;
    private TextView machine;
    private TextView val1,val2,val3,val4,val5,val6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_inspect_layout);

        facility_cd = getIntent().getStringExtra(EQUIPMENT_RESULT);
        Map<String, Object> facility_map = new HashMap<>();
        facility_map.put("facility_no", facility_cd);

        machine = (TextView)findViewById(R.id.machine_name);
        machine.setText(facility_cd);

        val1 = (TextView)findViewById(R.id.insp_val1) ;
        val2 = (TextView)findViewById(R.id.insp_val2) ;
        val3 = (TextView)findViewById(R.id.insp_val3) ;
        val4 = (TextView)findViewById(R.id.insp_val4) ;
        val5 = (TextView)findViewById(R.id.insp_val5) ;
        val6 = (TextView)findViewById(R.id.insp_val6) ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);


        Call<List<InspectDAO>> call = service.getInspectDetail(facility_map);
        call.enqueue(new Callback<List<InspectDAO>>() {

            @Override
            public void onResponse(Call<List<InspectDAO>> call, Response<List<InspectDAO>> response) {
                if(response.isSuccessful()) {
                    List<InspectDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0) {
                        val1.setText(dataDAO.get(0).getFacility_nm());
                        val2.setText(dataDAO.get(0).getResult());
                        val3.setText(dataDAO.get(0).getState());
                        val4.setText(dataDAO.get(0).getStart_dt());
                        val5.setText(dataDAO.get(0).getEnd_dt());
                        val6.setText(dataDAO.get(0).getInsp_rst_no());
                    }
                }
                else {
                    Log.d("response errrror", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<InspectDAO>> call, Throwable t) {
                Log.d("onFailure in EquipInsp", t.getMessage());
            }
        });
    }
}
