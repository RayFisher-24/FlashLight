package com.application.flashlight;

import static android.os.Build.VERSION.SDK_INT;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.application.flashlight.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.powerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(binding.button.getText().toString().equals("TurnOn")) {
                   binding.button.setText(R.string.turn_off);
                   binding.powerImage.setImageResource(R.drawable.off);
                   changeLightState(true);
               } else {
                   binding.button.setText(R.string.turn_on);
                   binding.powerImage.setImageResource(R.drawable.on);
                   changeLightState(false);
               }
            }
        });
    }

    private void changeLightState(boolean b) {
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

           CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
           String camID = null;
           try {
               camID = cameraManager.getCameraIdList()[0];
               cameraManager.setTorchMode(camID, b);
           } catch (CameraAccessException e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.button.setText(R.string.turn_on);
    }
}