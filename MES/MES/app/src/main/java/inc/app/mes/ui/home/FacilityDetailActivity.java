package inc.app.mes.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacilityDetailActivity extends AppCompatActivity {

    private TextView titleText, facilityNmText, facilityCdText, placeText, placeCdText, departmentNmText, employeeNmText,
    facilityTypeText, stateText, plineNmText, regNmText;
    private NetworkService networkService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_detail);
        networkService = MyApplication.getInstance().getNetworkService();

        titleText = (TextView) findViewById(R.id.facility_nm_title);
        facilityNmText = (TextView) findViewById(R.id.facility_nm);
        facilityCdText = (TextView) findViewById(R.id.facility_cd);
        placeText = (TextView) findViewById(R.id.place);
        placeCdText = (TextView) findViewById(R.id.place_cd);
        departmentNmText = (TextView) findViewById(R.id.department_nm);
        employeeNmText = (TextView) findViewById(R.id.employee_nm);
        facilityTypeText = (TextView) findViewById(R.id.facility_type);
        stateText = (TextView) findViewById(R.id.state);
        plineNmText = (TextView) findViewById(R.id.pline_nm);
        regNmText = (TextView) findViewById(R.id.reg_nm);

        Intent intent = getIntent();
        String facility_no = intent.getExtras().getString("facility_cd");
        getFacilityListPerPlace(facility_no);

    }

    public void getFacilityListPerPlace(String facility_cd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("facility_cd",facility_cd);
        Log.i("SINSIN", param.get("facility_cd").toString());
        Call<List<FacilityDAO>> joinContentCall=networkService.getFacilityDetail(param);

        joinContentCall.enqueue(new Callback<List<FacilityDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<FacilityDAO>> call, Response<List<FacilityDAO>> response) {
                if(response.isSuccessful()){
                    List<FacilityDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0){
                        FacilityDAO facility=dataDAO.get(0);
                        titleText.setText(facility.getFacility_nm());
                        facilityNmText.setText(facility.getFacility_nm());
                        facilityCdText.setText(facility.getFacility_no());
                        placeText.setText(facility.getPlace());
                        placeCdText.setText(facility.getPlace_cd());
                        departmentNmText.setText(facility.getDepartmet_nm());
                        employeeNmText.setText(facility.getEmployee_nm());
                        facilityTypeText.setText(facility.getFacility_type());
                        stateText.setText(facility.getState());
                        plineNmText.setText(facility.getPline_nm());
                        regNmText.setText(facility.getReg_nm());
                    }
                    else{
                        titleText.setText("설비 찾을 수 없음");
                    }

                }
                else{// 실패시 에러코드들
                    if(response.code()==500)
                    {
                        Log.i("SINSIN", "500실패");
                    }
                    else if(response.code()==503)
                    {
                        Log.i("SINSIN", "503 실패");
                    }
                    else if(response.code()==401)
                    {
                        Log.i("SINSIN", "401 실패");
                    }
                    Log.i("SINSIN", response.code()+"실패");
                    Log.i("SINSIN", response.body()+"실패");
                }
            }
            @Override
            public void onFailure(Call<List<FacilityDAO>> call, Throwable t) {
                //실패
            }
        });
    }
}