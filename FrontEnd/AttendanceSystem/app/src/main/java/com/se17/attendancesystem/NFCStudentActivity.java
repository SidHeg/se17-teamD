package com.se17.attendancesystem;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/*
    Activity that handles the attendance registration of students using NFC Technology
 */


public class NFCStudentActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] filterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcstudent);
        nfcAdapter =  NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            filter.addDataType("text/plain");
        }
        catch (IntentFilter.MalformedMimeTypeException ex) {
            throw new RuntimeException("MalformedMimeTypeException", ex);
        }
        filterArray = new IntentFilter[] {filter, };
    }

    /*
        onNewIntent will be called when the NDEF message is received by the mobile from an NFC Tag.
     */

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,filterArray, null);
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
                payload = new String(rec.getPayload());
                Toast.makeText(getApplicationContext(),payload,Toast.LENGTH_LONG).show();
                ServerComm serverComm = new ServerComm();
                serverComm.execute("1",MainActivity.user.getUserId(),MainActivity.user.getPassword(),payload);

                /*
                    Todo:
                        Authenticate the payload with backend and notify the student if he/she registered the
                    attendance successfully
                 */

            }
        }
    }



}
