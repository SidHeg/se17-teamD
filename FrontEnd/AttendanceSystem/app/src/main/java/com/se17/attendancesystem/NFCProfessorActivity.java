package com.se17.attendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
    Activity that helps professor to write payload in the NFC Tags
 */

public class NFCProfessorActivity extends AppCompatActivity {

    private EditText nfcText;
    private Button btnNfcWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcprofessor);
        nfcText = (EditText) findViewById(R.id.nfctext);
        btnNfcWrite = (Button) findViewById(R.id.btnNFCWrite);

    }


    private View.OnClickListener nfcWriteOnclickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            
        }
    };
}
