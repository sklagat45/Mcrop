package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.sklagat46.mcrop.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private Button login;
    private EditText userEmail;
    private EditText userpassword;
    DatabaseReference userprofile;
    private FirebaseAuth Auth;
    String Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = (EditText) findViewById(R.id.userEmailETxt);
        userpassword = (EditText) findViewById(R.id.passwordETxt);
        login = (Button) findViewById(R.id.loginBtn2);
        forgotPassword = (TextView) findViewById(R.id.forgotPaswordBtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }

    public void register(View view) {
        Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        //show how to pass information to next activity
        startActivity(startIntent);
    }
}
