package info.competitiveprogramming.simplecapture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.journeyapps.barcodescanner.SourceData;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapUtility {
    public static final String PREFIX = "bitmap_";

    public static Bitmap fromSourceData(SourceData sourceData, boolean resize) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        if (resize) {
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(sourceData.getData(), 0, sourceData.getData().length, option);
            if (option.outWidth > 640 || option.outHeight > 640) {
                option.inSampleSize = (Math.max(option.outWidth, option.outHeight) + 640 - 1) / 640;
            }
        }
        option.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(sourceData.getData(), 0, sourceData.getData().length, option);

        if (sourceData.isRotated()) {
            Matrix imageMatrix = new Matrix();
            imageMatrix.postRotate(sourceData.getRotation());
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), imageMatrix, false);
        }

        return bitmap;
    }

    public static String saveToFile(Context context, Bitmap bitmap) {
        try {
            File file = File.createTempFile(PREFIX, null, context.getFilesDir());
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {

        }
        return null;
    }

    public static Bitmap loadFromFile(Context context, String filename) {
        return BitmapFactory.decodeFile(filename);
    }
}
