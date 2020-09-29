package inc.app.mes.ui.edit;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.FRequestDAO;
import inc.app.mes.DTO.InspectDAO;
import inc.app.mes.DTO.Message;
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

public class InspectDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView inspRstNoTitle, inspectNmText, resultText,
    facilityNmText, facilityNoText, userNmText, employeeNmText;
    private TextView requestDetailText, remarkText;
    private Button deleteBtn;
    private NetworkService networkService;
    private PopupWindow mPopupWindow;
    private String insp_rst_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_detail);
        networkService = MyApplication.getInstance().getNetworkService();
        inspRstNoTitle=(TextView) findViewById(R.id.insp_rst_no_title);
        inspectNmText=(TextView) findViewById(R.id.inspect_nm);
        resultText=(TextView) findViewById(R.id.result);
        facilityNmText=(TextView) findViewById(R.id.facility_nm);
        facilityNoText=(TextView) findViewById(R.id.facility_no);
        userNmText=(TextView) findViewById(R.id.user_nm);
        employeeNmText=(TextView) findViewById(R.id.employee_nm);
        deleteBtn=(Button) findViewById(R.id.btn_delete);
        requestDetailText=(TextView) findViewById(R.id.request_detail);
        remarkText=(TextView) findViewById(R.id.remark);

        deleteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        insp_rst_no = intent.getExtras().getString("insp_rst_no");
        getInspectDetailByInspRstNo(insp_rst_no);
    }

    public void getInspectDetailByInspRstNo(String insp_rst_no) {
        Call<List<InspectDAO>> joinContentCall=networkService.getInspectDetailByInspRstNo(insp_rst_no);

        joinContentCall.enqueue(new Callback<List<InspectDAO>>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<InspectDAO>> call, Response<List<InspectDAO>> response) {
                if(response.isSuccessful()){
                    List<InspectDAO> dataDAO=response.body();
                    assert dataDAO != null;
                    if(dataDAO.size()>0){
                        InspectDAO fRequestDAO=dataDAO.get(0);
                        inspRstNoTitle.setText(fRequestDAO.getInsp_rst_no());
                        inspectNmText.setText(fRequestDAO.getInsp_rst_no());
                        resultText.setText(fRequestDAO.getResult());
                        facilityNmText.setText(fRequestDAO.getFacility_nm());
                        facilityNoText.setText(fRequestDAO.getFacility_no());
                        userNmText.setText(fRequestDAO.getUser_nm());
                        employeeNmText.setText(fRequestDAO.getEmplyee_nm());
                        requestDetailText.setText(fRequestDAO.getReq_details());
                        remarkText.setText(fRequestDAO.getRemark());
                    }
                    else{
                        inspRstNoTitle.setText("점검 찾을 수 없음");
                    }

                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InspectDAO>> call, Throwable t) {
                //실패
            }
        });
    }

    public void delFRequest(String insp_rst_no) {
        Call<Message> joinContentCall=networkService.delInspect(insp_rst_no);

        joinContentCall.enqueue(new Callback<Message>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    Message dataDAO=response.body();
                    assert dataDAO != null;

                }
                else{// 실패시 에러코드들
                    respnoseLogger.doPrint(response.code());
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
                    delFRequest(insp_rst_no);
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