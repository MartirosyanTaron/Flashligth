package martirosyan.taron.fanarik;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


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


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart: -------->");
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause: --->");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy: ---> ");
//    }



