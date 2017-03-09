package gfandos.myplaces.Fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import gfandos.myplaces.Activities.VideoPlayer;
import gfandos.myplaces.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoPlayerFragment extends Fragment {

    private Button okButton;
    private VideoView videoPlayer;

    public VideoPlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_player, container, false);

        okButton = (Button) view.findViewById(R.id.okButton);
        videoPlayer = (VideoView) view.findViewById(R.id.videoPlayer);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        String data = getActivity().getIntent().getStringExtra("EXTRA_PATH");
//        Uri uri = Uri.parse(data);
        videoPlayer.setVideoPath(data);
        videoPlayer.start();
        videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.start();
            }
        });
        return view;
    }
}
