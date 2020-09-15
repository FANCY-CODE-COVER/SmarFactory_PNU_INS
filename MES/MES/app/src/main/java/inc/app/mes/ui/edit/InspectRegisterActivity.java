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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InspectRegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView facilityTitleText, reqTitleText;
    private String req_no, facility_no;
    private Button startDateEdit, endDateEdit;
    private Button registerBtn, cancelBtn;
    private EditText resultCdEdit, resultDetailEdit, remarkEdit;
    private DatePickerDialog.OnDateSetListener callbackMethod1,callbackMethod2 ;
    private NetworkService networkService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_register);
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

        resultCdEdit=(EditText) findViewById(R.id.result_cd);
        resultCdEdit.setText("4N2050");//자재부족 코드 임의 삽입
        resultDetailEdit=(EditText) findViewById(R.id.result_details);
        remarkEdit=(EditText) findViewById(R.id.remark);

        registerBtn= (Button) findViewById(R.id.btn_register);
        cancelBtn= (Button) findViewById(R.id.btn_cancel);

        registerBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

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
        int id = view.getId();
        if (id == R.id.edit_text_start_dt) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(Calendar.getInstance().getTime());
            date.substring(0, 4);
            Log.i("date", date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8, 10));
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod1, year, month - 1, day);
            dialog.show();
        } else if (id == R.id.edit_text_end_dt) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(Calendar.getInstance().getTime());
            date.substring(0, 4);
            Log.i("date", date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8, 10));
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod2, year, month - 1, day);
            dialog.show();
        }
        else if (id==R.id.btn_register){
            String start_dt =startDateEdit.getText().toString();
            String end_dt =endDateEdit.getText().toString();
            String result_cd = resultCdEdit.getText().toString();
            String result_details =resultDetailEdit.getText().toString();
            String remark =remarkEdit.getText().toString();
            insInspect(start_dt, end_dt, result_cd, result_details, remark);
            Toast.makeText(getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(id==R.id.btn_cancel){
            finish();
        }
    }


    // #{facility_no}, #{req_no}, #{start_dt}, #{end_dt}, #{result_cd}, #{result_details}, #{remark}, #{reg_id}
    public void insInspect(String start_dt, String end_dt, String result_cd
            , String result_details, String remark) {
        String user_id= PreferenceManager.getString(this,"user_id");
        user_id="K19870001";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("facility_no", facility_no);
        param.put("req_no", req_no);
        param.put("start_dt", start_dt);
        param.put("end_dt", end_dt);
        param.put("result_cd", result_cd);
        param.put("result_details", result_details);
        param.put("remark", remark);
        param.put("reg_id", user_id);

        Call<Message> joinContentCall = networkService.insInspect(param);

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
                    assert response.body() != null;
//                    respnoseLogger.doPrint(response.code(), response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                //실패
            }
        });
    }//

}