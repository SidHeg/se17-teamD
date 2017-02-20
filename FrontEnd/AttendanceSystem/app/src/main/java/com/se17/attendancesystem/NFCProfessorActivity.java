package com.se17.attendancesystem;

import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
    Activity that helps professor to write payload in the NFC Tags
 */

public class NFCProfessorActivity extends AppCompatActivity {

    private Tag nfcTag;
    private EditText nfcText;
    private Button btnNfcWrite;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcprofessor);
        nfcText = (EditText) findViewById(R.id.nfctext);
        btnNfcWrite = (Button) findViewById(R.id.btnNFCWrite);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }
    

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(),"NFC Tag detected",Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener nfcWriteOnclickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(nfcTag==null){
                Toast.makeText(getApplicationContext(),"NFC Tag Not detected",Toast.LENGTH_SHORT).show();
            }else{
                writeToNFC(nfcText.getText().toString());
                Toast.makeText(getApplicationContext(),"Write Completed!",Toast.LENGTH_SHORT).show();
            }

        }

    };

    private void writeToNFC(String payload){

    }
}
