package martirosyan.taron.fanarik;


import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Camera camera;
    private Camera.Parameters parameters;
    private ImageView flashLightButton;
    boolean isFlashLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
        flashLightButton = findViewById(R.id.imageOffOn);
        flashLightButton.setOnClickListener(new FlashOnOffListener());

        if (isFlashSupported()) {
            camera = android.hardware.Camera.open();
            parameters = camera.getParameters();
        } else {
            showNoFlashAlert();
        }
    }

    private class FlashOnOffListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (isFlashLightOn) {
                flashLightButton.setImageResource(R.drawable.off);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                isFlashLightOn = false;
                Log.i(TAG, "onClick: turnOFF");
            } else {
                flashLightButton.setImageResource(R.drawable.on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isFlashLightOn = true;
                Log.i(TAG, "onClick: turnON");
            }
        }
    }

    private void showNoFlashAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Your device hardware does not support flashlight!")
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    private boolean isFlashSupported() {
        PackageManager pm = getPackageManager();
        Log.i(TAG, "isFlashSupported: ");
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    protected void onDestroy() {
      //  camera.setParameters(parameters);
        camera.startPreview();
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
}
//    public static final String TAG = MainActivity.class.getName();
//    private ImageView on;
//    private Camera camerObj;
//    private boolean flashOn;
//    private Camera.Parameters params;
//    private LinearLayout linearLayout;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i(TAG, "onCreate: ---> ");
//        setContentView(R.layout.activity_main);
//        on = findViewById(R.id.imageOffOn);
//linearLayout=findViewById(R.id.linearID);
//        boolean isCameraFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        if (!isCameraFlash) {
//            showCameraAlert();
//        } else {
//            camerObj = Camera.open();
//            params = camerObj.getParameters();
//        }
//        on.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (flashOn) {
//                    turnOff();
//                    Toast.makeText(MainActivity.this, "turn OFF ", Toast.LENGTH_SHORT).show();
//                } else {
//                    on.setImageResource(R.drawable.on);
//                   startService(new Intent(MainActivity.this,FlashLightService.class));
//                    Toast.makeText(MainActivity.this, "turn ON ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    private void showCameraAlert() {
//        new AlertDialog.Builder(this)
//                .setTitle("Error: No camera flash")
//                .setMessage("Camera flashligth not available in this device!")
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                }).show();
//
//    }
//
//    private void turnOn() {
//        params = camerObj.getParameters();
//        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//        camerObj.setParameters(params);
//        camerObj.startPreview();
//        flashOn = true;
//        on.setImageResource(R.drawable.on);
//        linearLayout.setBackgroundResource(R.drawable.green);
//        Log.i(TAG, "turnOn: ---> ");
//    }
//
//    private void turnOff() {
//        getCamera();
//        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//        camerObj.setParameters(params);
//        camerObj.stopPreview();
//        flashOn = false;
//        linearLayout.setBackgroundResource(R.drawable.red);
//        on.setImageResource(R.drawable.off);
//        Log.i(TAG, "turnOff: --> ");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        startService(new Intent(this, FlashLightService.class));
//        Log.i(TAG, "onStop: ---> ");
//        if (camerObj != null) {
//            camerObj.release();
//            camerObj = null;
//            params = null;
//        }
//    }
//
////    @Override
////    protected void onRestart() {
////        super.onRestart();
////        turnOff();
////        Log.i(TAG, "onRestart: ---> ");
////    }
//
//    public void getCamera() {
//        if (camerObj == null) {
//            try {
//                camerObj = Camera.open();
//                params = camerObj.getParameters();
//            } catch (Exception e) {
//                e.getMessage();
//                Toast.makeText(this, "error -- " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart: -------->");
//    }
//
////    @Override
////    protected void onResume() {
////        super.onResume();
////        if (camerObj == null || params == null) {
////            Log.i(TAG, "onResume: if block ");
////            camerObj = Camera.open();
////            params = camerObj.getParameters();
////            on.setImageResource(R.drawable.off);
////        }
////        Log.i(TAG, "onResume: --> ");
////    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause: --->");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//       turnOff();
//        Log.i(TAG, "onDestroy: ---> ");
//    }



