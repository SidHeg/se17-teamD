package com.se17.attendancesystem;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    private Button btnNfc;
    private Button btnGps;
    private Button btnQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        btnNfc = (Button) findViewById(R.id.btnNfc1);
        btnGps = (Button) findViewById(R.id.btnGps1);
        btnQRCode = (Button) findViewById(R.id.btnQRCode1);
        btnNfc.setOnClickListener(btnNfcOnclickListener);
        btnGps.setOnClickListener(btnGpsOnclickListener);
        btnQRCode.setOnClickListener(btnQRCodeOnclickListener);
    }


    private View.OnClickListener btnNfcOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getBaseContext(), NFCStudentActivity.class);
            startActivity(intent);

        }
    };

    private View.OnClickListener btnGpsOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getBaseContext(), GPSStudentActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener btnQRCodeOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getBaseContext(), QRStudentActivity.class);
            startActivity(intent);

        }
    };
}
