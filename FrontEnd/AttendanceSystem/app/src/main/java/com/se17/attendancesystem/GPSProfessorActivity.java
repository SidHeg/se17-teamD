package com.se17.attendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GPSProfessorActivity extends AppCompatActivity {

    private Button btnGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsprofessor);
        btnGPS = (Button) findViewById(R.id.btnGPSProf);
        btnGPS.setOnClickListener(btnGPSListener);
    }

    private View.OnClickListener btnGPSListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };
}
