package gfandos.myplaces.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import gfandos.myplaces.Activities.DetailActivity;
import gfandos.myplaces.Activities.VideoPlayer;
import gfandos.myplaces.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private TextView titleTv;
    private TextView typeTv;
    private TextView descriptionTv;

    private ImageView image;

    private Button okButton;
    private Button onVideo;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTv = (TextView) view.findViewById(R.id.detailTitle);
        typeTv = (TextView) view.findViewById(R.id.detailType);
        descriptionTv = (TextView) view.findViewById(R.id.detailDescription);

        image = (ImageView) view.findViewById(R.id.imageView);

        okButton = (Button) view.findViewById(R.id.okButton);
        onVideo = (Button) view.findViewById(R.id.onVideo);

        String data = getActivity().getIntent().getStringExtra("EXTRA_PICTURE");

        final String array[] = data.split("º");

        String path = array[0];
        String title = array[1];
        String type = array[2];
        String description = array[3];
        int media = Integer.valueOf(array[4]);

        titleTv.setText(title);
        typeTv.setText(type);
        descriptionTv.setText(description);

        if(media == 0) {
            Glide.with(getContext()).load(path).into(image);
            onVideo.setVisibility(View.INVISIBLE);
        } else {
            onVideo.setVisibility(View.VISIBLE);
            Uri myUri = Uri.parse(array[0]);
            Bitmap bitmap = getVideoFrame(getContext(), myUri);
            image.setImageBitmap(bitmap);

            onVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(((DetailActivity)getActivity()), VideoPlayer.class);
                    String data = array[0];
                    intent.putExtra("EXTRA_PATH", data);
                    startActivity(intent);
                }
            });
        }



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    public static Bitmap getVideoFrame(Context context, Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(uri.toString(),new HashMap<String, String>());
            return retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        return null;
    }
}
