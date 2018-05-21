package com.idn.materipertama;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.idn.materipertama.activities.Alarm;
import com.idn.materipertama.activities.Audio;
import com.idn.materipertama.activities.AudioRecord;
import com.idn.materipertama.activities.BluetoothDevice;
import com.idn.materipertama.activities.Browser;
import com.idn.materipertama.activities.CallPhone;
import com.idn.materipertama.activities.CameraDevice;
import com.idn.materipertama.activities.EmailClient;
import com.idn.materipertama.activities.SmsDevice;
import com.idn.materipertama.activities.Tts;
import com.idn.materipertama.activities.VideoDevice;
import com.idn.materipertama.activities.WifiDevice;
import com.idn.materipertama.myHelper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyFunction {

    @BindView(R.id.btn_browser)
    Button btnBrowser;
    @BindView(R.id.btn_alarm)
    Button btnAlarm;
    @BindView(R.id.btn_audio)
    Button btnAudio;
    @BindView(R.id.btn_audio_record)
    Button btnAudioRecord;
    @BindView(R.id.btn_bluetooth)
    Button btnBluetooth;
    @BindView(R.id.btn_call_phone)
    Button btnCallPhone;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_sms)
    Button btnSms;
    @BindView(R.id.btn_text)
    Button btnText;
    @BindView(R.id.btn_video)
    Button btnVideo;
    @BindView(R.id.btn_wifi)
    Button btnWifi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_browser, R.id.btn_alarm, R.id.btn_audio, R.id.btn_audio_record, R.id.btn_bluetooth, R.id.btn_call_phone, R.id.btn_camera, R.id.btn_email, R.id.btn_sms, R.id.btn_text, R.id.btn_video, R.id.btn_wifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_browser:
                intent(Browser.class);
                break;

            case R.id.btn_alarm:
                intent(Alarm.class);
                break;

            case R.id.btn_audio:
                intent(Audio.class);
                break;

            case R.id.btn_audio_record:
                intent(AudioRecord.class);
                break;

            case R.id.btn_bluetooth:
                intent(BluetoothDevice.class);
                break;

            case R.id.btn_call_phone:
                intent(CallPhone.class);
                break;

            case R.id.btn_camera:
                intent(CameraDevice.class);
                break;

            case R.id.btn_email:
                intent(EmailClient.class);
                break;

            case R.id.btn_sms:
                intent(SmsDevice.class);
                break;

            case R.id.btn_text:
                intent(Tts.class);
                break;

            case R.id.btn_video:
                intent(VideoDevice.class);
                break;

            case R.id.btn_wifi:
                intent(WifiDevice.class);
                break;

        }
    }
}
