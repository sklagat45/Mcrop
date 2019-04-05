package com.sklagat46.mcrop.ui;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sklagat46.mcrop.R;


public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText UserEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        UserEmail = (EditText) findViewById(R.id.userEmailETxt);
        resetPassword = (Button) findViewById(R.id.resetPasswordBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail =UserEmail.getText() .toString().trim();

                if(userEmail.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email adress ID", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "ForgotPasswordActivity reset email!", Toast.LENGTH_SHORT).show();
                                fileList();
                                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Error in sending ForgotPasswordActivity reset email", Toast.LENGTH_SHORT).show();


                            }

                        }


                    });
                }

            }
        });
    }
}
