package com.example.lab4_omelchenko;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPhoneActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView =  (VideoView)findViewById(R.id.videoView);

        Uri myVideoUri= Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(myVideoUri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }
}
