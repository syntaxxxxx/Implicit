package com.idn.materipertama.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Alarm extends AppCompatActivity {

    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.ac_clock)
    AnalogClock acClock;
    @BindView(R.id.btn_set_alarm)
    Button btnSetAlarm;
    @BindView(R.id.tv_show_alarm)
    TextView tvShowAlarm;

    TimePickerDialog tpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_set_alarm)
    public void onViewClicked() {

        openTpDialog(false);

    }


    // buat nampilin Time Picker Dialog
    private void openTpDialog(boolean b) {

        Calendar calendar = Calendar.getInstance();
        tpDialog = new TimePickerDialog(
                this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);

        tpDialog.setTitle("Set Alarm Time");
        tpDialog.show();

    }


    // ketika muncul lalu set time nya
    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calendar = Calendar.getInstance();
            Calendar callset = (Calendar) calendar.clone();
            callset.set(Calendar.HOUR_OF_DAY, hourOfDay);
            callset.set(Calendar.MINUTE, minute);
            callset.set(Calendar.SECOND, 0);
            callset.set(Calendar.MILLISECOND, 0);

            if (callset.compareTo(calendar) <= 0) {
                callset.add(Calendar.DATE, 1);

            } else if (callset.compareTo(calendar) > 0) {
                Log.i("Hasil", "> 0");

            }

            setAlarm(callset);

        }
    };


    // tampilkan ke textview sesuai format nya
    @SuppressLint("SetTextI18n")
    private void setAlarm(Calendar c) {

        tvShowAlarm.setText("***\n "
                + "Alarm Set On"
                + c.getTime()
                + "\n***");

        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                1, i, 0);

        @SuppressLint("ServiceCast") AlarmManager alarmManager
                = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
}



