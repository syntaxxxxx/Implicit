package com.idn.materipertama.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idn.materipertama.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallPhone extends AppCompatActivity {

    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_tampil_call)
    Button btnTampilCall;
    @BindView(R.id.btnlistcontact)
    Button btnlistcontact;
    @BindView(R.id.edtnumber)
    EditText edtnumber;

    private String noHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_call, R.id.btn_tampil_call, R.id.btnlistcontact})
    public void onViewClicked(View view) {

        noHp = edtnumber.getText().toString();

        switch (view.getId()) {
            case R.id.btn_call:
                call();
                break;

            case R.id.btn_tampil_call:
                startActivity(new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + noHp)));
                break;

            case R.id.btnlistcontact:
                listContact();
                break;
        }
    }


    private void listContact() {

        Intent i = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(i, 2);

    }


    private void call() {

        if (TextUtils.isEmpty(noHp)) {
            edtnumber.setError("Tidak Boleh Kosong");
            edtnumber.requestFocus();

        } else {

            int checkPermission = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.CALL_PHONE},
                        1);

            } else {
                startActivity(new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + noHp)));

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Cursor cursor = null;

            try {

                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null, null, null);

                if (cursor != null && cursor.moveToNext()) {

                    String phone = cursor.getString(0);
                    edtnumber.setText(phone);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
