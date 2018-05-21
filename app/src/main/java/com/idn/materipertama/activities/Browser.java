package com.idn.materipertama.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.idn.materipertama.R;

public class Browser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
    }


    public void onAksesLink(View view) {

        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.url))));

    }
}
