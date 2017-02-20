package com.se17.attendancesystem;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NFCStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcstudent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /*
        Source: Android Documentation: https://developer.android.com/guide/topics/connectivity/nfc/nfc.html
         */
        super.onNewIntent(intent);
        String payload = null;
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                messages[0] = (NdefMessage) rawMessages[0];
                NdefRecord rec = messages[0].getRecords()[0];
                String str = new String(rec.getPayload()).substring(3);
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

            }
        }
    }
}
