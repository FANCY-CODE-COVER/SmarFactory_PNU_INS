package com.pnu.spring.smartfactory.client_for_server_test;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pnu.spring.smartfactory.client_for_server_test.DTO.DataDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.FacilityDAO;
import com.pnu.spring.smartfactory.client_for_server_test.DTO.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText editID, editPassword;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkService = MyApplication.getInstance().getNetworkService();
        btnLogin = (Button) findViewById(R.id.btn_login);
        editID = (EditText) findViewById(R.id.edit_id);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin.setOnClickListener(this);
        String id = PreferenceManager.getString(this, "id");
        String password = PreferenceManager.getString(this, "password");

        if (!id.isEmpty() && !password.isEmpty()) {
            Toast.makeText(this, "자동 로그인 됩니다.", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_login) {
            String inputID = editID.getText().toString();
            String inputPW = editPassword.getText().toString();
            Map<String, Object> param = new HashMap<>();
            param.put("user_id", inputID);
            param.put("password", inputPW);
            Call<Message> joinContentCall = networkService.doLogin(param);
            new NetworkCall().execute(joinContentCall);




        }//end login button
    }// end onClick


    private class NetworkCall extends AsyncTask<Call, Void, String> {

        @Override
        protected String doInBackground(Call[] params) {
            try {
                Call<Message> call = params[0];
                Response<Message> response = call.execute();
                Log.i("SINSIN", "시도");
                return response.body().getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("SINSIN", result);
            if (result.equals("success")) {
                String inputID = editID.getText().toString();
                String inputPW = editPassword.getText().toString();

                PreferenceManager.setString(LoginActivity.this, "id", inputID);
                PreferenceManager.setString(LoginActivity.this, "password", inputPW);
                finish();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
            }
            editID.setText("");
            editPassword.setText("");
        }//end onPostExecute
    }


}
