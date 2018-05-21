package com.idn.materipertama.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoDevice extends MyFunction {

    Uri locationFile;
    @BindView(R.id.btn_video)
    Button btnVideo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_device);
        ButterKnife.bind(this);

        // izinkan device access camera
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // nougat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        100);

            }


            // cek lagi dan izinkan aplikasi ngambil resource dari device
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // nougat
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);

                }
            }
        }
    }


    @OnClick(R.id.btn_video)
    public void onViewClicked() {

        // menajalankan disk pada device tetap responsif
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String folderCamera = "videoku1";

        File file = new File(
                Environment.getExternalStorageDirectory(),
                folderCamera);

        // cek jika file belum ada
        if (!file.exists()) {
            file.mkdir();
        }


        File isiFile = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + folderCamera + "/VID" + currentDate() + ".mp4");

        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        locationFile = Uri.fromFile(isiFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, locationFile);
        startActivityForResult(i, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                toast("Berhasil Menyimpan Video \n Location"
                        + locationFile.toString());

            } else if (resultCode == RESULT_CANCELED) {
                toast("Cancel");

            } else {
                toast("Gagal Mengambil Gambar");

            }
        }
    }
}
