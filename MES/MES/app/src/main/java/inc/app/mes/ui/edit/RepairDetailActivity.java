package inc.app.mes.ui.edit;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.FRequestDAO;
import inc.app.mes.DTO.Message;
import inc.app.mes.DTO.RepairDAO;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
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
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepairDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView repairNoTitle, repairNoText, statusText,
    causeText, repairTypeText, startDtText, endDtText,
    facilityNoText, reqNoText;
    private Button deleteBtn;
    private NetworkService networkService;
    private PopupWindow mPopupWindow;
    private String repair_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_detail);
        networkService = MyApplication.getInstance().getNetworkService();
        repairNoTitle=(TextView) findViewById(R.id.repair_no_title);
        repairNoText=(TextView) findViewById(R.id.repair_no);
        statusText=(TextView) findViewById(R.id.status);
        causeText=(TextView) findViewById(R.id.cause);
        repairTypeText=(TextView) findViewById(R.id.repair_type);
        startDtText=(TextView) findViewById(R.id.start_dt);
        endDtText=(TextView) findViewById(R.id.end_dt);
        facilityNoText=(TextView) findViewById(R.id.facility_no);
        reqNoText=(TextView) findViewById(R.id.req_no);
        deleteBtn=(Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        repair_no = intent.getExtras().getString("repair_no");
        getFRequestDetailByReqNo(repair_no);
    }

    public void getFRequestDetailByReqNo(String repair_no) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("repair_no",repair_no);
        Log.i("SINSIN", param.get("repair_no").toString());
        Call<List<RepairDAO>> joinContentCall=networkService.getRepairDetailByRepairNo(param);

        joinContentCall.enqueue(new Callback<List<RepairDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<RepairDAO>> call, Response<List<RepairDAO>> response) {
                if(response.isSuccessful()){
                    List<RepairDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0){
                        RepairDAO fRequestDAO=dataDAO.get(0);
                        repairNoTitle.setText(fRequestDAO.getRepair_no());
                        repairNoText.setText(fRequestDAO.getRepair_no());
                        statusText.setText(fRequestDAO.getStatus());
                        causeText.setText(fRequestDAO.getCause());
                        repairTypeText.setText(fRequestDAO.getRepair_type());
                        startDtText.setText(fRequestDAO.getStart_dt());
                        endDtText.setText(fRequestDAO.getEnd_dt());
                        facilityNoText.setText(fRequestDAO.getFacility_no());
                        reqNoText.setText(fRequestDAO.getReq_no());
                    }
                    else{
                        repairNoTitle.setText("설비 찾을 수 없음");
                    }

                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code(), response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<List<RepairDAO>> call, Throwable t) {
                //실패
            }
        });
    }

    public void delRepair(String repair_no) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("repair_no",repair_no);
        Log.i("SINSIN", param.get("repair_no").toString());
        Call<Message> joinContentCall=networkService.delRepair(param);

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_delete) {
            View popupView = getLayoutInflater().inflate(R.layout.dialog_yes_or_no, null);
            mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            Button ok = (Button) popupView.findViewById(R.id.yes);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delRepair(repair_no);
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
    }//end onClick
}