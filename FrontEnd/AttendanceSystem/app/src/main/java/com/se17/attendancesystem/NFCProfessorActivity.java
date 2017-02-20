package com.se17.attendancesystem;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

/*
    Activity that helps professor to write payload in the NFC Tags
 */

public class NFCProfessorActivity extends AppCompatActivity {

    private Tag nfcTag;
    private EditText nfcText;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] filterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcprofessor);
        nfcText = (EditText) findViewById(R.id.nfctext);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        filterArray = new IntentFilter[] {filter, };
    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filterArray, null);

    }

    /*
        onNewIntent will be called when the NFC tag is discovered by the phone.
        user written text will be retrived from the edit text and written to the tag later using writeToNFC tag.
     */

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(),"NFC Tag detected",Toast.LENGTH_SHORT).show();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String payload = nfcText.getText().toString();
        if(payload.length()<=3 || payload.length() >=30){
            Toast.makeText(getApplicationContext(),"Allowed payload length >3 and <30",Toast.LENGTH_SHORT).show();
            return;
        }
        if(writeToNFC(tag, payload)){
            Toast.makeText(getApplicationContext(),"Write Completed!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Write Failed!",Toast.LENGTH_SHORT).show();
        }
    }

    /*
        writeToNFC function takes two arguments. Tag detail that we get when phone tapped against the NFC tag.
        This information retriced from the intent. NdefMessage is created and written to the Tag.
        On success, the function returns true. On failure, it returns false.

     */

    private boolean writeToNFC(Tag nfcTag, String payload){
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", payload.getBytes());
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {ndefRecord});
        Ndef ndef = Ndef.get(nfcTag);
        if(ndef!=null){
            try {
                ndef.connect();
                try {
                    ndef.writeNdefMessage(ndefMessage);
                }catch (android.nfc.FormatException ex ){
                    Toast.makeText(getApplicationContext(),"NFC Format Exception!",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }catch (IOException ex ){
                Toast.makeText(getApplicationContext(),"Unable to connect to NFC Tag!",Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{
            return false;
        }

        return true;
    }
}
