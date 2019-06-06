package com.example.lab4_omelchenko;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    Button start, pause, stop;
    SeekBar volumeControl;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        mPlayer = MediaPlayer.create(this, R.raw.ocean_drive);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });

        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pause.setEnabled(false);
        stop.setEnabled(false);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.start:
                mPlayer.start();
                start.setEnabled(false);
                pause.setEnabled(true);
                stop.setEnabled(true);
                break;
            case R.id.pause:
                mPlayer.pause();
                start.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(true);
                break;
            case R.id.stop:
                stopPlay();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }

    private void stopPlay(){
        mPlayer.stop();
        pause.setEnabled(false);
        stop.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            start.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
