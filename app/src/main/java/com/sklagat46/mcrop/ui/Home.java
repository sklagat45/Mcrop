package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sklagat46.mcrop.R;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
               Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                //show how to pass information to next activity
                startActivity(startIntent);
    }
        });
                Button registerBtn = (Button) findViewById(R.id.registerBtn);
              registerBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                        //show how to pass information to next activity
                        startActivity(startIntent);

            }
        });



    }
}
