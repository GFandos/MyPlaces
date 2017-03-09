package gfandos.myplaces.Utils;

import android.app.Application;

import java.io.File;

/**
 * Created by 47989768s on 07/03/17.
 */

public class MyPlaces extends Application {
    // Singleton instance
    private static MyPlaces sInstance = null;
    public File filePhoto = null;


    @Override
    public void onCreate() {
        super.onCreate();
        // Setup singleton instance
        sInstance = this;
    }

    // Getter to access Singleton instance
    public static MyPlaces getInstance() {
        return sInstance ;
    }
}
