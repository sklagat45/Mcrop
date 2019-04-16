package com.sklagat46.mcrop.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.util.Configs;
import com.sklagat46.mcrop.util.ImageUtil;
import com.sklagat46.mcrop.util.SharedPreferenceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddStockActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText productName, location, description;
    private Spinner productType, vegetable, fruit;
    private static final int IMAGE_LOADER_ID = 1;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private static final String[] PROJECTION = {MediaStore.Images.Media.DATA};
    private static final int TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 2;
    private final int PICK_IMAGE_REQUEST = 3;
    @BindView(R.id.btn_camera)
    ImageButton cameraBtn;
    @BindView(R.id.btn_gallary)
    ImageButton galleryBtn;
    @BindView(R.id.imgv_photo)
    ImageView img_photo;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Button done;
    //String productName,location,description;
    private Bitmap imageBitmap;
    private String encodedImage = "";
    private Uri selectedImageUri;
    private Uri filePath;
    private boolean onLoadFinishedCalled = false;
    private PackageManager packageManager;
    private int icon;

    private static final int CAMERA_REQUEST_CODE = 1;
    private Camera mCamera;
    private AlertDialog resultDialog;
    private ProgressDialog progressDialog;
    private SQLiteDatabase db;
    //private CameraPreview mPreview;

    DatabaseReference databaseUserProfile;
    private String selectedImagePath;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        ButterKnife.bind(this);
        FirebaseAuth auth;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        // Add a listener to the Capture button

        img_photo.setVisibility(View.GONE);
        packageManager = this.getPackageManager();
        cameraBtn.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ECLAIR)
            public void onClick(View v) {
                if (packageManager
                        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                    File imageFile;
                    try {
                        imageFile = createImageFile();
                        SharedPreferenceManager.saveTemporaryImagePath(AddStockActivity.this, imageFile.getAbsolutePath());
                        invokeCamera(imageFile);
                    } catch (IOException e) {
                        Toast.makeText(AddStockActivity.this, "An error occurred",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddStockActivity.this,
                            "There is no camera app installed on your phone.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(
                        Intent.createChooser(intent, "Pick a source"),
                        PICK_IMAGE);

            }
        });


    }

    private File createImageFile() throws IOException {
        File storagePath = new File(Environment.getExternalStorageDirectory()
                .getPath() + Configs.IMAGES_DIRECTORY);
        storagePath.mkdirs();

        Random rand = new Random();
        String randomStr = Long.toString(Math.abs(rand.nextLong()));
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = randomStr + timeStamp;
        File image = File.createTempFile(imageFileName, ".jpg", storagePath);

        // File image = File.createTempFile(imageFileName,
        // Configs.DEFAULT_IMAGE_EXTENSION, this.getCacheDir());

        return image;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void btnDone(View view) {

        uploadImage();
    }

    private void invokeCamera(File imageFile) {
        InvokeCameraTask invokeCameraTask = new InvokeCameraTask(imageFile);
        invokeCameraTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                selectedImageUri = data.getData();
                onLoadFinishedCalled = false;
                this.getSupportLoaderManager().restartLoader(
                        IMAGE_LOADER_ID, null, AddStockActivity.this);
                img_photo.setVisibility(View.VISIBLE);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //img_photo.setVisibility(View.GONE);
                // Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                // .show();
            } else {
                Toast.makeText(this, "An error occurred",
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == TAKE_PHOTO) {

            if (resultCode == Activity.RESULT_OK) {
                try {
                    String cameraPhotoPath = SharedPreferenceManager.getTemporaryImagePath(this);

                    if (!cameraPhotoPath.equals("")) {
                        decodeFile(cameraPhotoPath);
                        img_photo.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, "An error occurred photopath",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "An error occurred",
                            Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //img_photo.setVisibility(View.GONE);

            } else {
                Toast.makeText(this, "An error occurred",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void decodeFile(String filePath) {

        final int REQUIRED_WIDTH = 400;
        final int REQUIRED_HEIGHT = 300;

        final BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        o.inSampleSize = ImageUtil.calculateBitmapInSampleSize(o,
                REQUIRED_WIDTH, REQUIRED_HEIGHT);
        o.inJustDecodeBounds = false;

        imageBitmap = BitmapFactory.decodeFile(filePath, o);

        img_photo.setImageBitmap(imageBitmap);
        img_photo.setVisibility(View.VISIBLE);

    }

    public String getStringFromBitmap(Bitmap bitmapPicture) {

        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    //covert imagestring to bitmap
    private Bitmap getBitmapFromString(String input) {

        byte[] decodedString = Base64.decode(input, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(AddStockActivity.this, selectedImageUri, PROJECTION,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case IMAGE_LOADER_ID:
                if (onLoadFinishedCalled == false) {
                    onLoadFinishedCalled = true;
                    try {
                        selectedImagePath = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (data != null) {

                                String wholeID;

                                wholeID = DocumentsContract
                                        .getDocumentId(selectedImageUri);

                                // Split at colon, use second item in the array
                                String id = wholeID.split(":")[1];

                                String[] column = {MediaStore.Images.Media.DATA};

                                // where id is equal to
                                String sel = MediaStore.Images.Media._ID + "=?";

                                Cursor cursor = AddStockActivity.this
                                        .getContentResolver()
                                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                column, sel, new String[]{id},
                                                null);

                                String filePath = "";

                                int columnIndex = cursor.getColumnIndex(column[0]);

                                if (cursor.moveToFirst()) {
                                    filePath = cursor.getString(columnIndex);
                                }

                                cursor.close();

                                selectedImagePath = filePath;
                            } else {
                                selectedImagePath = selectedImageUri.getPath();
                            }
                        } else {
                            String[] projection = {MediaStore.Images.Media.DATA};
                            Cursor cursor = AddStockActivity.this.getContentResolver()
                                    .query(selectedImageUri, projection, null,
                                            null, null);
                            selectedImagePath = selectedImageUri.getPath();
                            if (cursor != null) {
                                int column_index = cursor
                                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();
                                selectedImagePath = cursor.getString(column_index);

                                cursor.close();
                            }
                        }

                        if (selectedImagePath != null) {
                            decodeFile(selectedImagePath);
                            file = new File(selectedImagePath);
                        } else {
                            Toast.makeText(AddStockActivity.this,
                                    "Could not retrieve the selected image",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddStockActivity.this,
                                "Could not retrieve the selected image",
                                Toast.LENGTH_LONG).show();
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    }
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage() {

        if (selectedImageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("vegetables/" + UUID.randomUUID().toString());
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddStockActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddStockActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Done " + (int) progress + "%");
                        }
                    });
        }
    }

    private class InvokeCameraTask extends AsyncTask<String, Void, Boolean> {
        private File imageFile;
        private ProgressDialog progressDialog;

        public InvokeCameraTask(File imageFile) {
            this.imageFile = imageFile;
            progressDialog = new ProgressDialog(AddStockActivity.this);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... cameraPhotoPathArray) {
                            /*editor.putString(Configs.PREFS_CAMERA_PHOTO_PATH_JSON,
                                    imageFile.getAbsolutePath());
                            editor.commit();*/
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent takePictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(imageFile));
            startActivityForResult(takePictureIntent, TAKE_PHOTO);
        }
    }


}