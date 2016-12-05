package info.competitiveprogramming.simplecapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.greenrobot.event.EventBus;

public class CaptureActivity extends AppCompatActivity {
    CaptureView captureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        getSupportActionBar().hide();
        captureView = (CaptureView) findViewById(R.id.captureView);

        findViewById(R.id.button_take_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        EventBus.getDefault().register(this);
        captureView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        captureView.pause();
    }

    void takePicture() {
        captureView.takePicture();
    }

    public void onEventMainThread(CaptureView.OnPictureTaken event) {
        Bitmap bitmap = BitmapUtility.fromSourceData(event.sourceData, true);
        String filename = BitmapUtility.saveToFile(getApplicationContext(), bitmap);
        Intent intent = new Intent();
        intent.putExtra("bitmap", filename);
        setResult(RESULT_OK, intent);
        finish();
    }
}
