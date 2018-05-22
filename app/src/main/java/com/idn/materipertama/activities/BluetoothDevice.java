package com.idn.materipertama.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.idn.materipertama.R;
import com.idn.materipertama.myHelper.MyFunction;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothDevice extends MyFunction {

    @BindView(R.id.btn_turn_on)
    Button btnTurnOn;
    @BindView(R.id.btn_list)
    Button btnList;
    @BindView(R.id.btn_find)
    Button btnFind;
    @BindView(R.id.btn_visible)
    Button btnVisible;
    @BindView(R.id.btn_turn_off)
    Button btnTurnOff;
    @BindView(R.id.list_device)
    ListView listDevice;
    @BindView(R.id.list_find)
    ListView listFind;

    BluetoothAdapter blthAdapter;
    Set<android.bluetooth.BluetoothDevice> blthDevices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);
        ButterKnife.bind(this);

        blthAdapter = BluetoothAdapter.getDefaultAdapter();

        if (blthAdapter == null) {
            toast("Device Tidak Support Device");
        }
    }


    @OnClick({R.id.btn_turn_on, R.id.btn_list, R.id.btn_find, R.id.btn_visible, R.id.btn_turn_off})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_turn_on:
                turnOn();
                break;

            case R.id.btn_list:
                listDevice();
                break;

            case R.id.btn_find:
                findDevice();
                break;

            case R.id.btn_visible:
                visible();
                break;

            case R.id.btn_turn_off:
                turnOff();
                break;
        }
    }


    private void turnOff() {

        blthAdapter.disable();
        listDevice.setAdapter(null);
        listFind.setAdapter(null);

    }


    private void visible() {

        Intent i = new Intent(BluetoothAdapter
                .ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(i, 3);

    }


    private void findDevice() {

        // find perangkat bluetooth terdekat
        BluetoothAdapter blthAdapter = (BluetoothAdapter.getDefaultAdapter());
        blthAdapter.startDiscovery();

        final ArrayAdapter adapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                // action cari device
                if (android.bluetooth.BluetoothDevice.ACTION_FOUND.equals(action)) {

                    android.bluetooth.BluetoothDevice device = intent.getParcelableExtra(
                            android.bluetooth.BluetoothDevice.EXTRA_DEVICE);

                    adapter.add(device.getName() + "\n");
                    listFind.setAdapter(adapter);

                } else {
                    toast("Tidak Ada Perangkat Bluetooth Yang Aktif");
                }
            }
        };


        if (!blthAdapter.isEnabled()) {
            toast("Bluetooth Belum Aktif");
        }


        IntentFilter filter = new IntentFilter(
                android.bluetooth.BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

    }


    private void listDevice() {

        blthDevices = blthAdapter.getBondedDevices();
        ArrayList arrayList = new ArrayList();

        if (blthDevices.size() > 0) {

            for (android.bluetooth.BluetoothDevice device : blthDevices) {
                arrayList.add(device.getName() + "\n" + device.getAddress());
                toast("Menampilkan Perangkat Yang Telah Terhubung");

                ArrayAdapter adapter = new ArrayAdapter(
                        this, android.R.layout.simple_list_item_1);
                listDevice.setAdapter(adapter);

            }

        } else if (!blthAdapter.isEnabled()) {
            toast("Bluetooth Belum Aktif");

        } else {
            toast("Belum Ada Perangkat Yang Terhubung");
        }
    }


    private void turnOn() {

        // jika belum aktif aktifkan
        if (!blthAdapter.isEnabled()) {

            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, 1);
            toast("Bluetooth Diaktifkan");

            // jika sudah aktif
        } else {
            toast("Bluetooth Sudah Aktif");
        }
    }
}
