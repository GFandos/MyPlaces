package gfandos.myplaces.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import gfandos.myplaces.Fragments.MainActivityFragment;
import gfandos.myplaces.Utils.MyPlaces;
import gfandos.myplaces.Pojo.Picture;
import gfandos.myplaces.R;
import gfandos.myplaces.Utils.GPSTracker;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int REQUEST_SAVE = 300;
    private static final int REQUEST_INFORMATION_RETURN = 404;
    private File filePhoto;
    private File fileVideo;
    public DatabaseReference mDatabaseRef;
    private GPSTracker gps;
    private String currentMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                // A new media has been added, add it to the displayed list
                try {
                    Picture picture = dataSnapshot.getValue(Picture.class);

                    FragmentManager fm = getSupportFragmentManager();
                    MainActivityFragment fragment = (MainActivityFragment)fm.findFragmentById(R.id.fragment);
                    fragment.putMarkers(picture);
                }catch(NullPointerException e){

                }catch(NumberFormatException e){

                }
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabaseRef.addChildEventListener(childEventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startPhotoActivity(File photofile) {
//        System.out.println(photofile.exists());
        filePhoto = photofile;

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            if (filePhoto != null) {
                i.putExtra(MediaStore.EXTRA_OUTPUT, filePhoto);
                startActivityForResult(i, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void startVideoActivity(File videoFile) {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileVideo = videoFile;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileVideo);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(this, Information.class);
                currentMedia = "photo";

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                filePhoto = finalFile;

                startActivityForResult(i, REQUEST_INFORMATION_RETURN);
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                System.out.println("request video");
                MyPlaces my = MyPlaces.getInstance();
                my.filePhoto = new File(getRealPathFromURI(uri));
                Intent i = new Intent(this, Information.class);
                currentMedia = "video";
                startActivityForResult(i, REQUEST_INFORMATION_RETURN);
            }
        }

        if (requestCode == REQUEST_INFORMATION_RETURN){

            System.out.println("hola caracola ;)");
            System.out.println("request save");
            FragmentManager fm = getSupportFragmentManager();
            MainActivityFragment fragment = (MainActivityFragment)fm.findFragmentById(R.id.fragment);
            gps = fragment.tracker;

            Date d = new Date();
            String id = "" + d.getTime();

            Picture picture = new Picture();
            if(data != null) {
                String array[] = data.getAction().split("º");
                System.out.println("Data != null");

                if(currentMedia.compareTo("photo") == 0) {
                    picture = new Picture(filePhoto.getAbsolutePath(), id, gps.latitude, gps.longitude, 0);
                } else {
                    picture = new Picture(array[3], id, gps.latitude, gps.longitude, 1);
                }
                picture.setType(array[0]);
                picture.setName(array[1]);
                picture.setDescription(array[2]);
            }

            mDatabaseRef.push().setValue(picture);
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }




}
