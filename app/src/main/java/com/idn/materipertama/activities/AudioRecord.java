package com.idn.materipertama.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.idn.materipertama.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioRecord extends AppCompatActivity {

    @BindView(R.id.btn_record)
    public Button btnRecord;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_play)
    Button btnPlay;

    MediaRecorder recorder;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        ButterKnife.bind(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        3);
            }
        }

        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecord.3gp";

        btnRecord.setEnabled(true);
        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);

    }

    @OnClick({R.id.btn_record, R.id.btn_stop, R.id.btn_play})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_record:
                mediaRecord();

                try {
                    recorder.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                recorder.start();

                btnRecord.setEnabled(false);
                btnPlay.setEnabled(true);
                btnStop.setEnabled(true);

                break;

            case R.id.btn_stop:
                stopRecord();
                break;

            case R.id.btn_play:
                playRecord();
                break;

        }
    }


    private void playRecord() {

        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

    }


    private void stopRecord() {

        try {
            recorder.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        recorder.release();
        btnPlay.setEnabled(true);
        btnRecord.setEnabled(true);

    }


    private void mediaRecord() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }
}

