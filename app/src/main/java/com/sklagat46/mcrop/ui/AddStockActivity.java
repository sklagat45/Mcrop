package com.sklagat46.mcrop.ui;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.sklagat46.mcrop.R;

import java.net.URL;

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
                startActivity(intent, CAMERA_REQUEST_CODE);
                // get an image from the camera
                mCamera.takePicture(null, null, picture);
            }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && RESULT_OK) {
            Url url = data.getData();
        }
    }
};

//        //addListenerOnButton();
//        databaseUserProfile = FirebaseDatabase.getInstance().getReference("userProfile");
//        mCamera = getameraInstance();
//        public static Camera getCameraInstance(){
//            Camera c = null;
//            try {
//                c = Camera.open(); // attempt to get a Camera instance
//            }
//            catch (Exception e){
//                // Camera is not available (in use or does not exist)
//
//        }
//
//
//    }
//
//
//    private Camera getameraInstance() {
//    }
//
//    public void takePicture(View view) {
//
//
//    }}
