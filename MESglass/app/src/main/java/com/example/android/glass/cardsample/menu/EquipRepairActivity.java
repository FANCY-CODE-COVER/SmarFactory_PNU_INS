package com.example.android.glass.cardsample.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.data.FacilityDAO;
import com.example.android.glass.cardsample.data.RepairDAO;
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

public class EquipRepairActivity extends BaseActivity {
    private TextView machine;
    private String facility_cd;
    private TextView val1,val2,val3,val4,val5,val6,val7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_repair_layout);

        facility_cd = getIntent().getStringExtra(EQUIPMENT_RESULT);


        machine = (TextView)findViewById(R.id.machine_name);
        machine.setText(facility_cd);

        val1 = (TextView)findViewById(R.id.repa_val1) ;
        val2 = (TextView)findViewById(R.id.repa_val2) ;
        val3 = (TextView)findViewById(R.id.repa_val3) ;
        val4 = (TextView)findViewById(R.id.repa_val4) ;
        val5 = (TextView)findViewById(R.id.repa_val5) ;
        val6 = (TextView)findViewById(R.id.repa_val6) ;
        val7 = (TextView)findViewById(R.id.repa_val7) ;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);


        Call<List<RepairDAO>> call = service.getRepairDetail(facility_cd);
        call.enqueue(new Callback<List<RepairDAO>>() {

            @Override
            public void onResponse(Call<List<RepairDAO>> call, Response<List<RepairDAO>> response) {
                if(response.isSuccessful()) {
                    List<RepairDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0) {
                        val1.setText(dataDAO.get(0).getStart_dt());
                        val2.setText(dataDAO.get(0).getEnd_dt());
                        val3.setText(dataDAO.get(0).getFacility_no());
                        val4.setText(dataDAO.get(0).getRepair_type());
                        val5.setText(dataDAO.get(0).getCause());
                        val6.setText(dataDAO.get(0).getRepair_no());
                        val7.setText(dataDAO.get(0).getStatus());
                    }
                }
                else {
                    Log.d("response errrror", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<RepairDAO>> call, Throwable t) {
                Log.d("onFailure in EquipRep", t.getMessage());
            }
        });

    }
}
