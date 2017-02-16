package com.se17.attendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfessorActivity extends AppCompatActivity {

    private Button btnNfc;
    private Button btnGps;
    private Button btnQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        btnNfc = (Button) findViewById(R.id.btnNfc);
        btnGps = (Button) findViewById(R.id.btnGps);
        btnQRCode = (Button) findViewById(R.id.btnQRCode);
        btnNfc.setOnClickListener(btnNfcOnclickListener);
        btnGps.setOnClickListener(btnGpsOnclickListener);
        btnQRCode.setOnClickListener(btnQRCodeOnclickListener);


    }

    private View.OnClickListener btnNfcOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener btnGpsOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    private View.OnClickListener btnQRCodeOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
