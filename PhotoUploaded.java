package com.example.project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class PhotoUploaded extends AppCompatActivity {
    Toolbar toolbar;
    EditText productName,productDesc, productPrice;
    Button submitProduct;
    RadioGroup radioGroup;
    String categoryName = "";
    ConstraintLayout constraintLayout;

    //database
    SQLiteDatabase database;
    Controllerdb db = new Controllerdb(this);
    DBHelper dbHelper = new DBHelper(this);

    //fot image uploading
    ImageView imageUploaded;

    private static final String IMAGE_DIRECTORY = "/demonuts";//path to save uploaded photo
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_uploaded);

        imageUploaded = (ImageView) findViewById(R.id.image_uploaded);
        productName = (EditText) findViewById(R.id.product_name);
        productDesc = (EditText) findViewById(R.id.product_desc);
        productPrice = (EditText) findViewById(R.id.product_price);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        submitProduct = (Button) findViewById(R.id.submit_productBtn);

        setToolbar();

        imageUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermissions();
                showPictureDialog();
            }
        });

        submitProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioBooks) {
                    categoryName = "books";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioHomeAccess) {
                    categoryName = "home_accessories";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioElectronic) {
                    categoryName = "electronic";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioHomeAppliance) {
                    categoryName = "home_appliance";
                } else {
                    Snackbar snackbar = Snackbar
                            .make(constraintLayout, "لم تقم باختيار القسم لمنتجك!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }

                boolean isInserted = dbHelper.insertProduct(productName.getText().toString(),productDesc.getText().toString(), productPrice.getText().toString(), imageViewByte(imageUploaded)
                        , categoryName);
                if (isInserted) {
                    Toast.makeText(PhotoUploaded.this, "تم رفع المنتج بنجاح!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PhotoUploaded.this, "حدث خطأ!", Toast.LENGTH_LONG).show();

                }

            }
        });
    }//end of onCreate()

    private byte[] imageViewByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("رفع الصورة");
        String[] pictureDialogItems = {
                "اختيار صورة من استديو الصور",
                "اختيار صورة من الكاميرا"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(PhotoUploaded.this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show();
                    imageUploaded.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PhotoUploaded.this, "فشل حفظ الصورة!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageUploaded.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(PhotoUploaded.this, " تم حفظ الصورة!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }//end of requestMultiplePermissions()

    public void setToolbar() {
        toolbar = findViewById(R.id.add_product_toolbar);
        setSupportActionBar(toolbar);

        //setting back button
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//setting the tool bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);//making it showing

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//going automatically to the previous page
            }
        });
    }//end of setToolbar

}//end of class
