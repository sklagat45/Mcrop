package com.sklagat46.mcrop.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sklagat46.mcrop.BuildConfig;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.util.Configs;
import com.sklagat46.mcrop.util.ImageUtil;
import com.sklagat46.mcrop.util.SharedPreferenceManager;
import com.sklagat46.mcrop.views.AddStockViews;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFruitStockActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //private EditText productName, location, description;
    private static final int IMAGE_LOADER_ID = 1;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private static final String[] PROJECTION = {MediaStore.Images.Media.DATA};
    private static final int TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 2;
    int PERMISSION_ALL = 1;
    private File file;
    private String iname;
    @BindView(R.id.btn_camera)
    ImageButton btnCamera;
    @BindView(R.id.btn_gallary)
    ImageButton btnGallery;
    @BindView(R.id.imgv_photo)
    ImageView img_photo;
    @BindView(R.id.editTextProductName)
    EditText productName;
    @BindView(R.id.locationETxt)
    EditText location;
    @BindView(R.id.descriptionETxt)
    EditText description;
    @BindView(R.id.btnSave)
    Button btnSave;


    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseUserProfile;
    DatabaseReference databaseFruitDetails;

    private Bitmap imageBitmap;
    private String encodedImage = "";
    private Uri selectedImageUri;
    private boolean onLoadFinishedCalled = false;
    private PackageManager packageManager;
    private int icon;

    private static final int CAMERA_REQUEST_CODE = 1;
    private Camera mCamera;
    private AlertDialog resultDialog;
    private ProgressDialog progressDialog;
    private SQLiteDatabase db;
    public static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //private CameraPreview mPreview;

    private String selectedImagePath;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruit_stock);
        ButterKnife.bind(this);
        FirebaseAuth auth;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseFruitDetails = FirebaseDatabase.getInstance().getReference("fruitDetails");
        //get user instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        btnSave.setOnClickListener(v -> addFruitDetails());

        setUpActionBar();
        //Check Permissions
        if (!SharedPreferenceManager.hasPermissions(this, PERMISSIONS_CAMERA)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(this),
                    PERMISSIONS_CAMERA, PERMISSION_ALL);
        }

        img_photo.setVisibility(View.GONE);
        packageManager = this.getPackageManager();

        btnCamera.setOnClickListener(v -> {
            //Check Permissions
            if (!SharedPreferenceManager.hasPermissions(this,PERMISSIONS_CAMERA)) {
                int PERMISSION_ALL = 1;
                ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA, PERMISSION_ALL);
            } else {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(AddFruitStockActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file));
                    int REQUEST_CAMERA = 1;
                    startActivityForResult(intent, REQUEST_CAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Camera error1", Toast.LENGTH_SHORT).show();
                }}
        });

        btnGallery.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(
                    Intent.createChooser(intent, "Pick a source"),
                    PICK_IMAGE);

        });
    }
    //check if everything is filled
    private void addFruitDetails() {
        String ProductName = productName.getText().toString().trim();
        String Location = location.getText().toString().trim();
        String Description = description.getText().toString().trim();


        //check if everything is field
        if (TextUtils.isEmpty(ProductName) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(Description) ) {

            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else {
           //upload details
            String id = databaseFruitDetails.push().getKey();
            AddStockViews addStockViews = new AddStockViews(id, ProductName, Location, Description);
            assert id != null;
            databaseFruitDetails.child(id).setValue(addStockViews);
            Toast.makeText(getApplicationContext(), "product saved", Toast.LENGTH_SHORT).show();
            uploadImage();
        }

    }

    private void setUpActionBar() {
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationIcon(upArrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                selectedImageUri = data.getData();
                onLoadFinishedCalled = false;
                this.getSupportLoaderManager().restartLoader(
                        IMAGE_LOADER_ID, null, AddFruitStockActivity.this);
                img_photo.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "An error occurred",
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == TAKE_PHOTO) {

            if (resultCode == Activity.RESULT_OK) {

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    String picturePath = file.getAbsolutePath();
                    System.out.println(picturePath + " Picture path in side the Onactivity method");

                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),
                            bitmapOptions);
                    // dest measurements
                    final int destWidth = 1500;
                    final int destHeight = 1500;


                    imageBitmap = Configs.getResizedBitmap(bitmap, destWidth, destHeight);

                    MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                            imageBitmap,
                            String.valueOf(System.currentTimeMillis()),
                            "Description");


                    saveImage(imageBitmap);
                    img_photo.setVisibility(View.VISIBLE);
                    img_photo.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Camera error", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //img_photo.setVisibility(View.GONE);

            } else {
                Toast.makeText(this, "An error occurred",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    //Save Images to a specific folder
    public void saveImage(Bitmap finalBitmap) {

        try {

            String root = Environment.getExternalStorageDirectory().toString();
            System.out.println(root + " Root value in saveImage Function");
            File myDir = new File(root + "/Soko Yetu/Images/sourcing/invoice");

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            Random generator = new Random();
            int n = 100000;
            n = generator.nextInt(n);

            iname = "TFL-" + n + Configs.getCurrentDateTime() + ".jpg";
            File file = new File(myDir, iname);

            if (file.exists()) {
                file.delete();
            }
            if (file.isFile()) {
                Log.d("files", " " + file.getPath() + " 2:" + file.getAbsolutePath());
            }

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
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
        return new CursorLoader(AddFruitStockActivity.this, selectedImageUri, PROJECTION,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case IMAGE_LOADER_ID:
                if (!onLoadFinishedCalled) {
                    onLoadFinishedCalled = true;
                    try {
                        selectedImagePath = null;
                        if (data != null) {

                            String wholeID = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                wholeID = DocumentsContract
                                        .getDocumentId(selectedImageUri);
                            }

                            // Split at colon, use second item in the array
                            String id = wholeID.split(":")[1];

                            String[] column = {MediaStore.Images.Media.DATA};


                            // where id is equal to
                            String sel = MediaStore.Images.Media._ID + "=?";

                            Cursor cursor = AddFruitStockActivity.this
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

                        if (selectedImagePath != null) {
                            decodeFile(selectedImagePath);
                            file = new File(selectedImagePath);
                        } else {
                            Toast.makeText(AddFruitStockActivity.this,
                                    "Could not retrieve the selected image",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddFruitStockActivity.this,
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage() {

        if (selectedImageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading image...");
            progressDialog.show();

            StorageReference ref = storageReference.child("fruits/" + UUID.randomUUID().toString());
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddFruitStockActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddFruitStockActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void btnCancel(View view) {


    }

    private class InvokeCameraTask extends AsyncTask<String, Void, Boolean> {
        private File imageFile;
        private ProgressDialog progressDialog;

        public InvokeCameraTask(File imageFile) {
            this.imageFile = imageFile;
            progressDialog = new ProgressDialog(AddFruitStockActivity.this);
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