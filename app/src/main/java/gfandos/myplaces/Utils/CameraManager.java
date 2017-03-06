package gfandos.myplaces.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gfandos.myplaces.Activities.MainActivity;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Gerard on 04/03/2017.
 */

public class CameraManager {

    public String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
//    private static final int ACTIVITAT_SELECCIONAR_IMATGE = 1;
//    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
//    private Uri fileUri;
    private Activity activity;
    private String currentImageName;
    private File photoFile;

    public CameraManager(Activity a) {
        activity = a;
    }

    public void takePhoto() {
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        try {
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                ((MainActivity) activity).startPhotoActivity(photoFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Error occurred while creating the File
        }
//        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";
        currentImageName = imageFileName;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = "file:" + image.getAbsolutePath();

        return image;

    }

}
