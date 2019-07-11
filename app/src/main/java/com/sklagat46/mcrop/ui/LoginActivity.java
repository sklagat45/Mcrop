package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.sklagat46.mcrop.R;

public class LoginActivity extends AppCompatActivity implements OnConnectionFailedListener {
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private Button login;
    private EditText userEmail;
    private EditText userpassword;
    DatabaseReference userprofile;
    String Email, Password;
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.userEmailETxt);
        userpassword = (EditText) findViewById(R.id.passwordETxt);
        login = (Button) findViewById(R.id.loginBtn2);
        forgotPassword = (TextView) findViewById(R.id.forgotPaswordBtn);


        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }

//
//            userprofile.child("userEmail,password").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String userEmail = dataSnapshot.getValue(UserProfile.class);
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//
//            });
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
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

    private void logIn() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                authWithGoogle(account);
            }
        }
    }
    private void authWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Auth Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

