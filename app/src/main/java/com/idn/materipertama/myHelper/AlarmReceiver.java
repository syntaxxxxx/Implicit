package com.idn.materipertama.myHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.idn.materipertama.R;

/**
 * Created by idn on 5/18/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {

        Toast.makeText(c, "Alarm Berbunyi",
                Toast.LENGTH_SHORT).show();

        MediaPlayer player = MediaPlayer.create(c, R.raw.alarm);
        player.start();

    }
}
