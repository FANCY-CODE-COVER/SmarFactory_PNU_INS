package com.pnu.spring.smartfactory.client_for_server_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText editID, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        editID = (EditText) findViewById(R.id.edit_id);
        editPassword = (EditText) findViewById(R.id.edit_password);

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputID = editID.getText().toString();
                String inputPW = editPassword.getText().toString();
                editID.setText("");
                editPassword.setText("");
                if (inputID.equals("abcd") && inputPW.equals("1234")) {
                    PreferenceManager.setString(LoginActivity.this, "id", inputID);
                    PreferenceManager.setString(LoginActivity.this, "password", inputPW);
                    finish();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class) ;
                    startActivity(intent) ;
                }
                else{
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String id = PreferenceManager.getString(this, "id");
        String password = PreferenceManager.getString(this, "password");

        if (!id.isEmpty() && !password.isEmpty()) {
            Toast.makeText(this, "자동 로그인 됩니다.", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class) ;
            startActivity(intent) ;
        }
    }
}