package com.journeyapps.barcodescanner.camera;

import com.journeyapps.barcodescanner.SourceData;

public interface PictureCallback {
    void onPictureTaken(SourceData sourceData);
}
