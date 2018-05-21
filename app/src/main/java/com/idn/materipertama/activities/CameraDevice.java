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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraDevice extends MyFunction {

    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.iv_show)
    ImageView ivShow;

    Uri locationFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_device);
        ButterKnife.bind(this);

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        10);

            }
        }


        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        11);

            }
        }
    }

    @OnClick({R.id.btn_camera, R.id.btn_show})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_camera:
                takeCamera();
                break;

            case R.id.btn_show:
                showGallery();
                break;
        }
    }


    private void showGallery() {



    }


    private void takeCamera() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String folderCamera = "photo";

        File file = new File(
                Environment.getExternalStorageDirectory(),
                folderCamera);

        // cek jika file belum ada
        if (!file.exists()) {
            file.mkdir();
        }


        File isiFile = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + folderCamera + "/VID" + currentDate() + ".jpg");

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        locationFile = Uri.fromFile(isiFile);

        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(MediaStore.EXTRA_OUTPUT, locationFile);
        startActivityForResult(i, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                toast("Berhasil Menyimpan Gambar \n Location"
                        + locationFile.toString());

            } else if (resultCode == RESULT_CANCELED) {
                toast("Cancel");

            } else {
                toast("Gagal Mengambil Gambar");
            }

        } else if (requestCode == 2) {

        }
    }
}
