package inc.app.mes.ui.edit;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.Message;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.util.PreferenceManager;
import inc.app.mes.util.respnoseLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RepairRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private EditText causeEdit, repairAmtEdit ,repairTypeEdit
            , repairDetailEdit, remarkEdit, causeCdEdit;
    private Button startDateEdit, endDateEdit;
    private TextView facilityTitleText, reqTitleText;
    private Button registerBtn, cancelBtn;
    private String req_no, facility_no;
    private NetworkService networkService;
    private DatePickerDialog.OnDateSetListener callbackMethod1,callbackMethod2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_register);
        networkService = MyApplication.getInstance().getNetworkService();
        facilityTitleText=(TextView) findViewById(R.id.facility_title);
        reqTitleText=(TextView) findViewById(R.id.req_title);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(Calendar.getInstance().getTime());
        startDateEdit= (Button) findViewById(R.id.edit_text_start_dt);
        endDateEdit= (Button) findViewById(R.id.edit_text_end_dt);
        startDateEdit.setText(date);
        endDateEdit.setText(date);
        startDateEdit.setOnClickListener(this);
        endDateEdit.setOnClickListener(this);
        this.InitializeListener();

        radioGroup = (RadioGroup) findViewById(R.id.radio_status);
        causeCdEdit= (EditText) findViewById(R.id.cause_cd);
        causeCdEdit.setText("4N2010");//설비문제 테스트 코드

        causeEdit = (EditText) findViewById(R.id.cause);
        repairTypeEdit= (EditText) findViewById(R.id.repair_type);
        repairTypeEdit.setText("A15340");//설비문제 테스트 코드
        repairAmtEdit= (EditText) findViewById(R.id.repair_amt);
        repairDetailEdit= (EditText) findViewById(R.id.repair_detail);
        remarkEdit= (EditText) findViewById(R.id.remark);


        registerBtn= (Button) findViewById(R.id.btn_register);
        cancelBtn= (Button) findViewById(R.id.btn_cancel);

        registerBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        String radioId= getStringFromID(radioGroup.getCheckedRadioButtonId());

        Intent intent = getIntent();
        facility_no = intent.getExtras().getString("facility_cd");
        req_no = intent.getExtras().getString("req_no");
        facilityTitleText.setText(facility_no);
        reqTitleText.setText(req_no);

    }

    public void InitializeListener()
    {
        callbackMethod1 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                startDateEdit.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        };
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                endDateEdit.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        };
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.edit_text_start_dt){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(Calendar.getInstance().getTime());
            date.substring(0,4);
            Log.i("date",date.substring(0,4)+"-"+date.substring(5,7)+"-"+date.substring(8,10));
            int year=Integer.parseInt(date.substring(0,4));
            int month=Integer.parseInt(date.substring(5,7));
            int day=Integer.parseInt(date.substring(8,10));
            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod1, year, month-1, day);
            dialog.show();
        }
        else if (id==R.id.edit_text_end_dt) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(Calendar.getInstance().getTime());
            date.substring(0,4);
            Log.i("date",date.substring(0,4)+"-"+date.substring(5,7)+"-"+date.substring(8,10));
            int year=Integer.parseInt(date.substring(0,4));
            int month=Integer.parseInt(date.substring(5,7));
            int day=Integer.parseInt(date.substring(8,10));
            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod2, year, month-1, day);
            dialog.show();
        }
        
        else if (id==R.id.btn_register){
            int status = radioGroup.getCheckedRadioButtonId();

            String start_dt =startDateEdit.getText().toString();
            String end_dt =endDateEdit.getText().toString();
            String status_result = getStringFromID(status);
            String case_cd =causeCdEdit.getText().toString();
            String repair_type_cd =repairTypeEdit.getText().toString();
            String repair_amt =repairAmtEdit.getText().toString();
            String cause =causeEdit.getText().toString();
            String repair_details =repairDetailEdit.getText().toString();
            String remark =remarkEdit.getText().toString();
            insRepair(start_dt, end_dt, status_result, case_cd, repair_type_cd, repair_amt
                    , cause, repair_details, remark);
            Toast.makeText(getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(id==R.id.btn_cancel){
            finish();
        }
    }

    //  #{facility_no}, #{req_no}, #{start_dt}, #{end_dt}, #{status}, #{case_cd}
    //  , #{repair_type_cd}, #{repair_amt},#{cause}, #{repair_details},#{remark}, #{reg_id}
    public void insRepair(String start_dt, String end_dt, String status, String case_cd
            ,String repair_type_cd,String repair_amt,String cause,String repair_details
            ,String remark) {
        String user_id= PreferenceManager.getString(this,"user_id");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("facility_no", facility_no);
        param.put("req_no", req_no);
        param.put("start_dt", start_dt);
        param.put("end_dt", end_dt);
        param.put("status", status);
        param.put("case_cd", case_cd);
        param.put("repair_type_cd", repair_type_cd);
        param.put("repair_amt", repair_amt);
        param.put("cause", cause);
        param.put("repair_details", repair_details);
        param.put("remark", remark);
        param.put("reg_id", user_id);

        Call<Message> joinContentCall = networkService.insRepair(param);

        joinContentCall.enqueue(new Callback<Message>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message dataDAO = response.body();
                    assert dataDAO != null;
                    if(dataDAO.getResult().equals("success"))
                    {
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

    private String getStringFromID(int status) {
        String status_result = "F";
        if(status==R.id.request){
            status_result="Q";
        }
        else if(status==R.id.receipt){
            status_result="R";
        }
        else if(status==R.id.progress){
            status_result="P";
        }
        else if(status==R.id.complete){
            status_result="C";
        }
        else if(status==R.id.hold){
            status_result="H";
        }
        else if(status==R.id.cancel){
            status_result="X";
        }
        return status_result;
    }


}