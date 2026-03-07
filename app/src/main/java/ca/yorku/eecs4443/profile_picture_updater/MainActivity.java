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

public class MainActivity extends AppCompatActivity {

    // Button for taking a photo (camera)
    private Button cameraButton;
    // Button for opening photo gallery
    private Button galleryButton;

    private Button useButton;
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

        useButton = (Button) findViewById(R.id.UseButton);

        displayImage = (ImageView) findViewById(R.id.ImageDisplay);


        // SETS USE BUTTON AND IMAGE VIEW INVISIBLE
        // HAVE IT ONLY BE VISIBLE WHEN AN IMAGE IS EITHER TAKEN ON CAMERA OR SELECTED FROM GALLERY
        // probably via intents
        useButton.setVisibility(View.INVISIBLE);
        displayImage.setVisibility(View.INVISIBLE);



        cameraButton = (Button) findViewById(R.id.PhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TAG = "MyActivityTag";
                Log.d(TAG, "Camera open");

                Toast.makeText(MainActivity.this, "Ask Camera Permission", Toast.LENGTH_SHORT).show();

                requestPermissions("Camera");

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

                requestPermissions("Gallery");
            }
        });
    }

    // Looking at reference (can be changed later)
        // geeksforgeeks.org/android/android-how-to-request-permissions-in-android-application/
    private void requestPermissions(String media) {
        //do permissions here

        //String media -> tell if permission is for "Camera" or "Gallery"
    }


}