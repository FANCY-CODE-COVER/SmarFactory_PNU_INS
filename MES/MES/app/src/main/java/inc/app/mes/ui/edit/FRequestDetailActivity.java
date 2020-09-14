package inc.app.mes.ui.edit;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.FRequestDAO;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.Message;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.ui.home.FacilityDetailActivity;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView frequestText, reqNoText, userNmText, facilityNoText,InspRstNoText, statusText,employeeNmText;
    private TextView requestDetailText, remarkText;
    private Button regInspectBtn, regRepairBtn, deleteBtn;
    private NetworkService networkService;
    private PopupWindow mPopupWindow;
    private String req_no, facility_cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_request_detail);

        networkService = MyApplication.getInstance().getNetworkService();
        frequestText=(TextView) findViewById(R.id.frequest_title);
        reqNoText=(TextView) findViewById(R.id.req_no);
        userNmText=(TextView) findViewById(R.id.user_nm);
        facilityNoText=(TextView) findViewById(R.id.facility_no);
        InspRstNoText=(TextView) findViewById(R.id.insp_rst_no);
        statusText=(TextView) findViewById(R.id.status);
        employeeNmText=(TextView) findViewById(R.id.employee_nm);
        requestDetailText=(TextView) findViewById(R.id.request_detail);
        remarkText=(TextView) findViewById(R.id.remark);
        regInspectBtn=(Button) findViewById(R.id.btn_register_inspect);
        regRepairBtn=(Button) findViewById(R.id.btn_register_repair);
        deleteBtn=(Button) findViewById(R.id.btn_delete);
        regInspectBtn.setOnClickListener(this);
        regRepairBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        Intent intent = getIntent();
        req_no = intent.getExtras().getString("req_no");
        facility_cd="";
        getFRequestDetailByReqNo(req_no);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_register_inspect){
            Intent intent = new Intent(FRequestDetailActivity.this, InspectRegisterActivity.class);
            intent.putExtra("req_no", req_no);
            intent.putExtra("facility_cd", facility_cd);

            this.startActivity(intent);
        }
        else if(id==R.id.btn_register_repair){
            Intent intent = new Intent(FRequestDetailActivity.this, RepairRegisterActivity.class);
            intent.putExtra("req_no", req_no);
            intent.putExtra("facility_cd", facility_cd);

            this.startActivity(intent);
        }
        else if(id==R.id.btn_delete){
            View popupView = getLayoutInflater().inflate(R.layout.dialog_yes_or_no, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            Button ok = (Button) popupView.findViewById(R.id.yes);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delFRequest(req_no);
                    mPopupWindow.dismiss();
                    finish();
                }
            });

            Button cancel = (Button) popupView.findViewById(R.id.no);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
    }

    public void getFRequestDetailByReqNo(String req_no) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("req_no",req_no);
        Log.i("SINSIN", param.get("req_no").toString());
        Call<List<FRequestDAO>> joinContentCall=networkService.getFRequestDetailByReqNo(param);

        joinContentCall.enqueue(new Callback<List<FRequestDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<FRequestDAO>> call, Response<List<FRequestDAO>> response) {
                if(response.isSuccessful()){
                    List<FRequestDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0){
                        FRequestDAO fRequestDAO=dataDAO.get(0);
                        frequestText.setText(fRequestDAO.getReq_no());
                        reqNoText.setText(fRequestDAO.getReq_no());
                        userNmText.setText(fRequestDAO.getUser_nm());
                        facilityNoText.setText(fRequestDAO.getFacility_no());
                        facility_cd=fRequestDAO.getFacility_no();

                        InspRstNoText.setText(fRequestDAO.getInsp_rst_no());
                        statusText.setText(fRequestDAO.getStatus());
                        employeeNmText.setText(fRequestDAO.getEmployee_nm());
                        requestDetailText.setText(fRequestDAO.getReq_details());
                        remarkText.setText(fRequestDAO.getRemark());
                    }
                    else{
                        frequestText.setText("설비 찾을 수 없음");
                    }

                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code(), response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<List<FRequestDAO>> call, Throwable t) {
                //실패
            }
        });
    }
    public void delFRequest(String req_no) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("req_no",req_no);
        Log.i("SINSIN", param.get("req_no").toString());
        Call<Message> joinContentCall=networkService.delFRequest(param);

        joinContentCall.enqueue(new Callback<Message>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message dataDAO=response.body();
                    assert dataDAO != null;

                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code(), response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                //실패
            }
        });
    }

}