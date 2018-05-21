package com.idn.materipertama.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailClient extends MyFunction {

    @BindView(R.id.edt_destination)
    EditText edtDestination;
    @BindView(R.id.edt_subject)
    EditText edtSubject;
    @BindView(R.id.edt_message)
    EditText edtMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_client);
        ButterKnife.bind(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        String error = "Tidak Boleh Kosong";

        String destiantion = edtDestination.getText().toString();
        String message = edtMessage.getText().toString();
        String subject = edtSubject.getText().toString();

        if (id == R.id.send) {

            if (TextUtils.isEmpty(destiantion)) {
                edtDestination.setError(error);
                edtDestination.requestFocus();

            } else if (TextUtils.isEmpty(message)) {
                edtMessage.setError(error);
                edtMessage.requestFocus();

            } else if (TextUtils.isEmpty(subject)) {
                edtSubject.setError(error);
                edtSubject.requestFocus();

            } else {

                Intent i = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", "", null));

                i.putExtra(Intent.EXTRA_EMAIL, new String[]{destiantion});
                i.putExtra(Intent.EXTRA_TEXT, message);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(i,
                            "Choose Email Client"));

                } else {
                    toast("Email Client Not Available");
                }
            }

        } else {

            edtDestination.setText(null);
            edtSubject.setText(null);
            edtMessage.setText(null);

        }

        return super.onOptionsItemSelected(item);

    }
}
