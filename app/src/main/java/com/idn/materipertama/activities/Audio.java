package com.idn.materipertama.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Audio extends MyFunction {

    @BindView(R.id.btn_ring)
    Button btnRing;
    @BindView(R.id.btn_vibrate)
    Button btnVibrate;
    @BindView(R.id.btn_silent)
    Button btnSilent;

    AudioManager audioManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);
        ButterKnife.bind(this);

        audioManager = (AudioManager) getSystemService(c.AUDIO_SERVICE);

        // izinkan aplikasi akses audio manager device
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // permision untuk nougat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        10);

            }
        }
    }


    @OnClick({R.id.btn_ring, R.id.btn_vibrate, R.id.btn_silent})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_ring:
                setRing();
                break;

            case R.id.btn_vibrate:
                setVibrate();
                break;

            case R.id.btn_silent:
                setSilent();
                break;

        }
    }


    private void setSilent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
                    AudioManager.ADJUST_UNMUTE, 0);

        } else {

            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            toast("Dalam Mode Silent");

        }
    }


    private void setVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

    }


    private void setRing() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
                    AudioManager.ADJUST_UNMUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
                    AudioManager.ADJUST_UNMUTE, 0);

        } else {

            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            toast("Dalam Mode Normal");

        }
    }
}
