package inc.app.mes.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.InspectDAO;
import inc.app.mes.DTO.Message;
import inc.app.mes.DTO.RepairDAO;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.recycler.InspectAdapter;
import inc.app.mes.recycler.RepairAdapter;
import inc.app.mes.util.PreferenceManager;
import inc.app.mes.util.respnoseLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacilityDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleText, facilityNmText, facilityCdText, placeText, placeCdText, departmentNmText, employeeNmText,
            facilityTypeText, stateText, plineNmText, regNmText;
    private Button regFRequestBtn, inspectLookupBtn, repairLookupBtn;
    private PopupWindow mPopupWindow;
    private NetworkService networkService;
    private RecyclerView dialogRecycler;
    private RepairAdapter repairAdapter;
    private InspectAdapter inspectAdapter;
    private String facility_no;
    private EditText requestDetail, remarkText;
    private RadioGroup radioGroup;

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
        regFRequestBtn = (Button) findViewById(R.id.btn_register_frequest);
        inspectLookupBtn = (Button) findViewById(R.id.btn_inspect_lookup);
        repairLookupBtn = (Button) findViewById(R.id.btn_repair_lookup);
        regFRequestBtn.setOnClickListener(this);
        inspectLookupBtn.setOnClickListener(this);
        repairLookupBtn.setOnClickListener(this);
        Intent intent = getIntent();
        facility_no = intent.getExtras().getString("facility_cd");
        getFacilityListPerPlace(facility_no);

    }

    public void getFacilityListPerPlace(String facility_cd) {
        Log.i("SINSIN", facility_cd);
        Call<List<FacilityDAO>> joinContentCall = networkService.getFacilityDetail(facility_cd);

        joinContentCall.enqueue(new Callback<List<FacilityDAO>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<FacilityDAO>> call, Response<List<FacilityDAO>> response) {
                if (response.isSuccessful()) {
                    List<FacilityDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    if (dataDAO.size() > 0) {
                        FacilityDAO facility = dataDAO.get(0);
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
                    } else {
                        titleText.setText("설비 찾을 수 없음");
                    }

                } else {// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<FacilityDAO>> call, Throwable t) {
                //실패
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_register_frequest) {
            View popupView = getLayoutInflater().inflate(R.layout.dialog_frequest_activity, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            radioGroup = (RadioGroup) popupView.findViewById(R.id.radio_status);
            requestDetail = popupView.findViewById(R.id.request_detail);
            remarkText = popupView.findViewById(R.id.remark);
            Button ok = (Button) popupView.findViewById(R.id.btn_register);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int status = radioGroup.getCheckedRadioButtonId();
                    String status_result = getStringFromID(status);
                    String request_detail = requestDetail.getText().toString();
                    String remark = remarkText.getText().toString();
                    insFRequest(status_result, request_detail, remark);
                    Toast.makeText(getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();
                    mPopupWindow.dismiss();
                }
            });
            Button cancel = (Button) popupView.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        } else if (id == R.id.btn_inspect_lookup || id == R.id.btn_repair_lookup) {
            View popupView = getLayoutInflater().inflate(R.layout.dialog_history_activity, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            mPopupWindow.setFocusable(true);
            // 외부 영역 선택히 PopUp 종료
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            TextView title = popupView.findViewById(R.id.title);

            dialogRecycler = popupView.findViewById(R.id.dialog_recyclerview);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            dialogRecycler.setLayoutManager(linearLayoutManager2);
            if (id == R.id.btn_inspect_lookup) {
                title.setText("점검 이력 조회");
                inspectAdapter = new InspectAdapter(this);
                getInspectDetail(facility_no);
            }
            if (id == R.id.btn_repair_lookup) {
                title.setText("수리 이력 조회");
                repairAdapter = new RepairAdapter(this);
                getRepairDetail(facility_no);
            }

            Button cancel = (Button) popupView.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }  //end else if

    }

    private String getStringFromID(int status) {
        String status_result = "F";
        if (status == R.id.request) {
            status_result = "Q";
        } else if (status == R.id.receipt) {
            status_result = "R";
        } else if (status == R.id.progress) {
            status_result = "P";
        } else if (status == R.id.complete) {
            status_result = "C";
        } else if (status == R.id.hold) {
            status_result = "H";
        } else if (status == R.id.cancel) {
            status_result = "X";
        }
        return status_result;
    }

    public void getInspectDetail(String facility_no) {
        Call<List<InspectDAO>> joinContentCall = networkService.getInspectDetail(facility_no);

        joinContentCall.enqueue(new Callback<List<InspectDAO>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<InspectDAO>> call, Response<List<InspectDAO>> response) {
                if (response.isSuccessful()) {
                    List<InspectDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    inspectAdapter.setClear();
                    for (int i = 0; i < dataDAO.size(); ++i) {
                        inspectAdapter.addItem(dataDAO.get(i));
                    }
                    dialogRecycler.setAdapter(inspectAdapter);
                } else {// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InspectDAO>> call, Throwable t) {
                //실패
            }
        });
    }

    public void getRepairDetail(String facility_no) {
        Call<List<RepairDAO>> joinContentCall = networkService.getRepairDetail(facility_no);

        joinContentCall.enqueue(new Callback<List<RepairDAO>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<RepairDAO>> call, Response<List<RepairDAO>> response) {
                if (response.isSuccessful()) {
                    List<RepairDAO> dataDAO = response.body();
                    assert dataDAO != null;
                    repairAdapter.setClear();
                    for (int i = 0; i < dataDAO.size(); ++i) {
                        repairAdapter.addItem(dataDAO.get(i));
                    }
                    dialogRecycler.setAdapter(repairAdapter);
                } else {// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }
            @Override
            public void onFailure(Call<List<RepairDAO>> call, Throwable t) {
                //실패
            }
        });
    }// end

    // #{req_user_id}, #{facility_no}, #{status},  #{req_details}, #{remark}, #{reg_id}
    public void insFRequest(String status, String req_details, String remark) {
        String user_id = PreferenceManager.getString(this, "user_id");
        Log.i("Shin", user_id);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("req_user_id", user_id);
        param.put("facility_no", facility_no);
        param.put("status", status);
        param.put("req_details", req_details);
        param.put("remark", remark);
        param.put("reg_id", user_id);
        Call<Message> joinContentCall = networkService.insFRequest(param);

        joinContentCall.enqueue(new Callback<Message>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message dataDAO = response.body();
                    assert dataDAO != null;
                    if (dataDAO.getResult().equals("success")) {
                        //do something
                    }
                } else {// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                //실패
            }
        });
    }//
}