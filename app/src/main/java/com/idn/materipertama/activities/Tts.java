package com.idn.materipertama.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tts extends MyFunction implements TextToSpeech.OnInitListener {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btnSpeech)
    Button btnSpeech;

    private TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        ButterKnife.bind(this);

        tts = new TextToSpeech(c, this);

    }


    @OnClick(R.id.btnSpeech)
    public void onViewClicked() {

        String text = editText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int hasil = tts.setLanguage(Locale.ENGLISH);

            if (hasil == TextToSpeech.LANG_MISSING_DATA ||
                    hasil == TextToSpeech.LANG_NOT_SUPPORTED) {
                toast("Bahasa Tidak Mendukung");

            } else {

                onViewClicked();
                btnSpeech.setEnabled(true);

            }

        } else {
            toast("TTS Tidak Support");
        }
    }
}
