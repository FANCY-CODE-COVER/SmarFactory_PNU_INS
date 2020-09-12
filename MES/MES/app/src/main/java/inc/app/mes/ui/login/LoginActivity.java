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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText editUserID, editPassword;
    private NetworkService networkService;
    private Boolean AUTO_LOGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AUTO_LOGIN=false;

        networkService = MyApplication.getInstance().getNetworkService();
        editUserID = (EditText) findViewById(R.id.edit_user_id);
        editPassword = (EditText) findViewById(R.id.edit_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                String inputID = editUserID.getText().toString();
                String inputPW = editPassword.getText().toString();
                Map<String, Object> param = new HashMap<>();
                param.put("user_id", inputID);
                param.put("password", inputPW);
                Call<Message> joinContentCall = networkService.doLogin(param);
                new NetworkCall().execute(joinContentCall);
            }
        });
        String user_id = PreferenceManager.getString(this, "user_id");
        String password = PreferenceManager.getString(this, "password");

        if (!user_id.isEmpty() && !password.isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("user_id", user_id);
            param.put("password", password);
            AUTO_LOGIN=true;
            Call<Message> joinContentCall = networkService.doLogin(param);
            new NetworkCall().execute(joinContentCall);
            Toast.makeText(this, "자동 로그인 됩니다.", Toast.LENGTH_SHORT).show();
        }



    }

    private class NetworkCall extends AsyncTask<Call, Void, String> {

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