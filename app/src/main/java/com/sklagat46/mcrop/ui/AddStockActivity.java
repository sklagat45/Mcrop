package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.sklagat46.mcrop.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddStockActivity extends AppCompatActivity {

    private EditText productName, location, description;
    private Spinner productType, vegetable, fruit;
    private Button galleryBtn, done;
    private ImageButton captureBtn;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    //String productName,location,description;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Camera mCamera;
    //private CameraPreview mPreview;

    DatabaseReference databaseUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        FirebaseAuth auth;
        // Add a listener to the Capture button
        captureBtn = (ImageButton) findViewById(R.id.cameraBtn);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }

    });







    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

    }


}