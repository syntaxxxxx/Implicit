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
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsDevice extends MyFunction {

    private static final int REQUESTSMS = 1;

    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_msh_sms)
    EditText edtMsgSms;
    @BindView(R.id.btn_sms_intent)
    Button btnSmsIntent;
    @BindView(R.id.btn_kirim_sms)
    Button btnKirimSms;

    private String noHp, message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_device);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edt_number, R.id.btn_sms_intent, R.id.btn_kirim_sms})
    public void onViewClicked(View view) {

        noHp = edtNumber.getText().toString();
        message = edtMsgSms.getText().toString();

        switch (view.getId()) {

            case R.id.edt_number:
                edtInputNo();
                break;

            case R.id.btn_sms_intent:
                smsIntent();
                break;

            case R.id.btn_kirim_sms:
                smsLangsung();
                break;
        }
    }


    private void smsLangsung() {

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.SEND_SMS)) {

            } else {

                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.SEND_SMS},
                        REQUESTSMS);

            }

        } else {

            try {

                SmsManager smsMan = SmsManager.getDefault();
                smsMan.sendTextMessage(
                        noHp, null, message,
                        null, null);
                toast("Berhasil Mengirim Sms");

            } catch (Exception e) {
                e.printStackTrace();
                toast("Gagal Mengirim Sms" + e.getMessage());

            }
        }
    }


    private void smsIntent() {

        String error = "Tidak Boleh Kosong";

        if (TextUtils.isEmpty(noHp)) {
            edtNumber.setError(error);
            edtNumber.requestFocus();

        } else if (TextUtils.isEmpty(message)) {
            edtMsgSms.setError(message);
            edtMsgSms.requestFocus();

        } else {

            Intent sms = new Intent(Intent.ACTION_VIEW);
            sms.putExtra("address", noHp);
            sms.putExtra("sms_body", message);
            sms.setType("vnd.android-dir/mms-sms");
            startActivity(sms);

        }

    }


    private void edtInputNo() {

        Intent i = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(i, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Cursor cursor = null;

            try {

                // akses data local contact telepon API
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        null, null, null);

                if (cursor != null && cursor.moveToNext()) {

                    String phone = cursor.getString(0);
                    edtNumber.setText(phone);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
