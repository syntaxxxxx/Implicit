package com.idn.materipertama.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiDevice extends MyFunction {

    @BindView(R.id.wifi)
    Switch wifi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_device);
        ButterKnife.bind(this);

        wifi.setChecked(status());

        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                wifiChangeStatus(isChecked);

            }
        });
    }


    @SuppressLint("MissingPermission")
    private void wifiChangeStatus(boolean b) {

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        if (ActivityCompat.checkSelfPermission(

                this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // permission nougat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

                    && checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED

                    && checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE}, 100);

            }

        } else if (b && !wifiManager.isWifiEnabled()) {

            toast("Wifi Aktif");
            wifiManager.setWifiEnabled(true);

        } else if (!b & wifiManager.isWifiEnabled()) {

            toast("Wifi Tidak Aktif");
            wifiManager.setWifiEnabled(false);

        }
    }


    private boolean status() {

        WifiManager manager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();

    }
}
