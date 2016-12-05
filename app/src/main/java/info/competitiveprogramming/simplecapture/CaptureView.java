package info.competitiveprogramming.simplecapture;

import android.content.Context;
import android.util.AttributeSet;

import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.SourceData;
import com.journeyapps.barcodescanner.camera.PictureCallback;

import de.greenrobot.event.EventBus;

public class CaptureView extends CameraPreview implements PictureCallback {
    public CaptureView(Context context) {
        super(context);
        initialize(context, null);
    }

    public CaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public CaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {

    }

    public void takePicture() {
        if (getCameraInstance() != null && getCameraInstance().isOpen()) {
            getCameraInstance().takePicture(this);
        }
    }

    @Override
    public void onPictureTaken(SourceData sourceData) {
        EventBus.getDefault().post(new OnPictureTaken(sourceData));
    }

    public static class OnPictureTaken {
        public SourceData sourceData;
        OnPictureTaken(SourceData sourceData) {
            this.sourceData = sourceData;
        }
    }
}
