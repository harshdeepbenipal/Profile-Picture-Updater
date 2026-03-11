package ca.yorku.eecs4443.profile_picture_updater;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Button for taking a photo (camera)
    private Button cameraButton;
    // Button for opening photo gallery
    private Button galleryButton;

    //private Button useButton;
    private ImageView displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //useButton = (Button) findViewById(R.id.UseButton);

        displayImage = (ImageView) findViewById(R.id.ImageDisplay);


        // SETS USE BUTTON AND IMAGE VIEW INVISIBLE
        // HAVE IT ONLY BE VISIBLE WHEN AN IMAGE IS EITHER TAKEN ON CAMERA OR SELECTED FROM GALLERY
        // probably via intents
        //useButton.setVisibility(View.INVISIBLE);


        cameraButton = (Button) findViewById(R.id.PhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TAG = "MyActivityTag";
                Log.d(TAG, "Camera open");

                Toast.makeText(MainActivity.this, "Ask Camera Permission", Toast.LENGTH_SHORT).show();

                checkPermissions("Camera");

                // if permission granted
                //Toast.makeText(MainActivity.this, "Camera allowed", Toast.LENGTH_SHORT).show();

                // if permission denied
                //Toast.makeText(MainActivity.this, "Camera denied", Toast.LENGTH_SHORT).show();
            }
        });

        galleryButton = (Button) findViewById(R.id.FileButton);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Ask Gallery Permission", Toast.LENGTH_SHORT).show();

                checkPermissions("Gallery");
            }
        });
    }

    // Camera result launcher
    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {

            if (result.getData() != null) {

                Bundle extras = result.getData().getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                if (imageBitmap != null) {
                    displayImage.setImageBitmap(imageBitmap);
                }
            }
            //displayImage.setVisibility(View.VISIBLE);
            //useButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Camera cancelled", Toast.LENGTH_SHORT).show();
        }
    });

    // Gallery result launcher
    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri imageUri = result.getData().getData();
            displayImage.setImageURI(imageUri);

            //displayImage.setVisibility(View.VISIBLE);
            //useButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Gallery selection cancelled", Toast.LENGTH_SHORT).show();
        }
    });

    private void checkPermissions(String media) {
        if (media.equals("Camera")) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }else if (media.equals("Gallery")) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 200);
            }
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryLauncher.launch(galleryIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                openCamera();
            }
            else if (requestCode == 200) {
                openGallery();
            }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


}