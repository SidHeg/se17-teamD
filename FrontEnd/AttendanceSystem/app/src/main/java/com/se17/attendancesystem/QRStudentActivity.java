package com.se17.attendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRStudentActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private Button btn;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrstudent);

        btn = (Button) findViewById(R.id.btn);
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(this);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(scannerView);
                scannerView.startCamera();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        /*
            send to backend to compare and register attendance
        */

        ServerComm serverComm = new ServerComm();
        serverComm.execute("3",MainActivity.user.getUserId(),MainActivity.user.getPassword(),result.getText());

        if(result!=null){
            Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"QR Code was not scanned!",Toast.LENGTH_LONG).show();
        }
    }
}
