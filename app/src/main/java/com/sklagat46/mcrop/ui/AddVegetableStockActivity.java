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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sklagat46.mcrop.BuildConfig;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.data.model.Vegetable;
import com.sklagat46.mcrop.data.repository.McropRepository;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddVegetableStockActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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
    ImageButton cameraBtn;
    @BindView(R.id.btn_gallary)
    ImageButton galleryBtn;
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
    //private Button done;
    //String productName,location,description;
    private Bitmap imageBitmap;
    private String encodedImage = "";
    private Uri selectedImageUri;
    private boolean onLoadFinishedCalled = false;
    private PackageManager packageManager;
    public static Activity activity;
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
    DatabaseReference databaseUserProfile;
    DatabaseReference databaseVegetableDetails;
    private String selectedImagePath;
    private McropRepository mcropRepository;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vegetable_stock);
        ButterKnife.bind(this);
        FirebaseAuth auth;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseVegetableDetails = FirebaseDatabase.getInstance().getReference("vegetableDetails");
        //get user instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        mcropRepository = new McropRepository(getApplicationContext());
        setUpActionBar();
        btnSave.setOnClickListener(v -> addVegetableDetails());

        //Check Permissions
        if (!SharedPreferenceManager.hasPermissions(this, PERMISSIONS_CAMERA)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(this),
                    PERMISSIONS_CAMERA, PERMISSION_ALL);
        }

        img_photo.setVisibility(View.GONE);
        packageManager = this.getPackageManager();

        cameraBtn.setOnClickListener(v -> {
            //Check Permissions
            if (!SharedPreferenceManager.hasPermissions(this, PERMISSIONS_CAMERA)) {
                int PERMISSION_ALL = 1;
                ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA, PERMISSION_ALL);
            } else {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(AddVegetableStockActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file));
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Camera error1", Toast.LENGTH_SHORT).show();
                }
            }
        });


        galleryBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(
                    Intent.createChooser(intent, "Pick a source"),
                    PICK_IMAGE);

        });

    }

    //check if everything is filled
    private void addVegetableDetails() {
        String ProductName = productName.getText().toString().trim();
        String Location = location.getText().toString().trim();
        String Description = description.getText().toString().trim();
        final String vegetableId = UUID.randomUUID().toString();


        //check if everything is field
        if (TextUtils.isEmpty(ProductName) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(Description)) {

            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            //safe to local db
            Vegetable vegetable = new Vegetable();
            vegetable.setVegetableId(vegetableId);
            vegetable.setVegetableName(ProductName);
            vegetable.setLocation(Location);
            vegetable.setDescription(Description);
            if (imageBitmap != null) {
                vegetable.setProductImage(getStringFromBitmap(imageBitmap));
            }
            new addVegetableTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, vegetable);


            String id = databaseVegetableDetails.push().getKey();
            AddStockViews addStockViews = new AddStockViews(id, ProductName, Location, Description);
            databaseVegetableDetails.child(id).setValue(addStockViews);
            Toast.makeText(getApplicationContext(), "product saved", Toast.LENGTH_SHORT).show();
            uploadImage();
        }


    }

    private class addVegetableTask extends AsyncTask<Vegetable, Void, Void> {
        @Override
        protected Void doInBackground(Vegetable... item) {
            mcropRepository.addVegetable(item[0]);
            return null;
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
                        IMAGE_LOADER_ID, null, AddVegetableStockActivity.this);
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
                    System.out.println(picturePath + " Picture path in side the On activity method");

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
            File myDir = new File(root + "/mcrop/Images");

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
        return new CursorLoader(AddVegetableStockActivity.this, selectedImageUri, PROJECTION,
                null, null, null);
    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case IMAGE_LOADER_ID:
                if (!onLoadFinishedCalled) {
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

                                Cursor cursor = AddVegetableStockActivity.this
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
                            Cursor cursor = AddVegetableStockActivity.this.getContentResolver()
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
                            Toast.makeText(AddVegetableStockActivity.this,
                                    "Could not retrieve the selected image",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddVegetableStockActivity.this,
                                "Could not retrieve the selected image",
                                Toast.LENGTH_LONG).show();
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    }
                }
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

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
            progressDialog.setTitle("Uploading Image...");
            progressDialog.show();

            StorageReference ref = storageReference.child("vegetables/" + UUID.randomUUID().toString());
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(AddVegetableStockActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(AddVegetableStockActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Done " + (int) progress + "%");
                    });
        }
    }

    public void btnCancel(View view) {
        onBackPressed();
    }

}