package inc.app.mes.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import inc.app.mes.DTO.Message;
import inc.app.mes.MainActivity;
import inc.app.mes.R;
import inc.app.mes.custum_application.MyApplication;
import inc.app.mes.network.NetworkService;
import inc.app.mes.ui.home.FacilityDetailActivity;
import inc.app.mes.util.PreferenceManager;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText editUserID, editPassword;
    private NetworkService networkService;
    private Boolean AUTO_LOGIN;
    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AUTO_LOGIN = false;

        networkService = MyApplication.getInstance().getNetworkService();
        editUserID = (EditText) findViewById(R.id.edit_user_id);
        editPassword = (EditText) findViewById(R.id.edit_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputID = editUserID.getText().toString();
                if (inputID.equals("ip")) {
                    // ip 변경을 위한 부분
                    // 사용 필요 적음
                    View popupView = getLayoutInflater().inflate(R.layout.dialog_change_ip, null);
                    mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mPopupWindow.setFocusable(true);
                    mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    final EditText baseUrlEdit;
                    Button ok = (Button) popupView.findViewById(R.id.btn_change_ip_addr);
                    baseUrlEdit=(EditText) popupView.findViewById(R.id.edit_base_url);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String new_IP=baseUrlEdit.getText().toString();
                            MyApplication.getInstance().setBaseUrl(new_IP);
                            mPopupWindow.dismiss();
                        }
                    });

                    Button cancel = (Button) popupView.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                        }
                    });
                    return;
                }
                String inputPW = editPassword.getText().toString();
                Map<String, Object> param = new HashMap<>();
                param.put("user_id", inputID);
                param.put("password", inputPW);
                Call<Message> joinContentCall = networkService.doLogin(param);
                new NetworkCall().execute(joinContentCall);
            }
        });
        // 로그인정보 저장으로 PreferenceManager 사용
        String user_id = PreferenceManager.getString(this, "user_id");
        String password = PreferenceManager.getString(this, "password");

        if (!user_id.isEmpty() && !password.isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("user_id", user_id);
            param.put("password", password);
            AUTO_LOGIN = true;
            Call<Message> joinContentCall = networkService.doLogin(param);
            // 로그인 응답 후 접속되야하기 때문에 동기식 통신사용
            new NetworkCall().execute(joinContentCall);
            Toast.makeText(this, "자동 로그인 됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private class NetworkCall extends AsyncTask<Call, Void, String> {
        //동기식 통신을 위한 내부 클래스
        @Override
        protected String doInBackground(Call[] params) {
            try {
                if(params.length>0) {
                    Call<Message> call = params[0];
                    Response<Message> response = call.execute();
                    Log.i("SINSIN", "시도"+response.body());
                    return response.body().getResult();
            }
                return "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result!=null && result.equals("success")) {
                Log.i("SINSIN", result);
                if(! AUTO_LOGIN) {
                    String inputID = editUserID.getText().toString();
                    String inputPW = editPassword.getText().toString();

                    PreferenceManager.setString(LoginActivity.this, "user_id", inputID);
                    PreferenceManager.setString(LoginActivity.this, "password", inputPW);
                }
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
            }
            editUserID.setText("");
            editPassword.setText("");
        }//end onPostExecute
    }
}