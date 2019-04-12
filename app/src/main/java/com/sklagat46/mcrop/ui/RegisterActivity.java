package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.views.UserProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, residence, phoneNumber, userEmail, userpassword;
    private Button signup, signin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    String Username, Residence, PhoneNumber, Email, password;
    DatabaseReference databaseUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseAuth auth;
        //addListenerOnButton();
        databaseUserProfile = FirebaseDatabase.getInstance().getReference("userProfile");

        //get firebase instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        username = (EditText) findViewById(R.id.productETxt);
        residence = (EditText) findViewById(R.id.residenceETxt);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberETxt);
        userEmail = (EditText) findViewById(R.id.userEmailETxt);
        userpassword = (EditText) findViewById(R.id.passwordETxt);
        signup = (Button) findViewById(R.id.signupBtn);
        signin = (Button) findViewById(R.id.signinBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                //show how to pass information to next activity
                startActivity(startIntent);

                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), com.sklagat46.mcrop.ui.LoginActivity.class);
                //show how to pass information to next activity
                startActivity(startIntent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserProfile();
            }
        });
    }
        //check if everything is field
            private void addUserProfile() {
                String Username = username.getText().toString().trim();
                String Residence = residence.getText().toString().trim();
                String PhoneNumber = phoneNumber.getText().toString().trim();
                String Email = userEmail.getText().toString().trim();
                String Userpassword = userpassword.getText().toString().trim();


            //check if everything is field
                if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Residence) || TextUtils.isEmpty(PhoneNumber) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Userpassword)) {

                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Userpassword.length() < 6) {

                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    String id = databaseUserProfile.push().getKey();
                    UserProfile userProfile = new UserProfile(id, Username, Residence, PhoneNumber, Email, Userpassword);
                    databaseUserProfile.child(id).setValue(userProfile);

                    Toast.makeText(getApplicationContext(), "user added", Toast.LENGTH_SHORT).show();
                }

                Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(Email, Userpassword)

                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                firebaseUser = auth.getCurrentUser();

                                Toast.makeText(RegisterActivity.this, "account created successfully:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a message to the user. If sign in succeeds

                                // the auth state listener will be notified and logic to handle the

                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {

                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {

                                    startActivity(new Intent(RegisterActivity.this, com.sklagat46.mcrop.ui.LoginActivity.class
                                    ));
                                    finish();
                                }
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            //create user
                        });
            }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);}











}
