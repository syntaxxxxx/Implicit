package com.idn.materipertama.myHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFunction extends AppCompatActivity {

    public Context c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = MyFunction.this;

    }


    public void intent(Class destination) {
        startActivity(new Intent(c, destination));

    }


    public void toast(String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();

    }


    public String currentDate() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat
                = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        Date date = new Date();
        return dateFormat.format(date);

    }
}
