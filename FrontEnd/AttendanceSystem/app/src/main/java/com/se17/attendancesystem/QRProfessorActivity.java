package com.se17.attendancesystem;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;


public class QRProfessorActivity extends AppCompatActivity {

    private EditText qrInput;
    private Button btn;
    private ImageView qrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrprofessor);

        qrInput = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String qrText = qrInput.getText().toString();
                qrImg = (ImageView) findViewById(R.id.imageView);

                if(qrText == null || qrText.length() == 0){
                    Toast.makeText(getApplicationContext(),"No Text Entered!!",Toast.LENGTH_LONG).show();
                    qrImg.setImageResource(0);
                }
                else{
                    /*
                    store text in back end database
                    */
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        int minDimension = Math.min(qrImg.getWidth(), qrImg.getHeight());
                        BitMatrix bitMatrix = multiFormatWriter.encode(qrText, BarcodeFormat.QR_CODE,minDimension,minDimension);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrImg.setImageBitmap(bitmap);

                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        byte[] b = byteArray.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                        ServerComm serverComm = new ServerComm();
                        String result = "";
                        try {
                            result = serverComm.execute("3", MainActivity.user.getUserId(), MainActivity.user.getPassword(), qrText, encodedImage).get();
                        }catch (InterruptedException ex){
                            Toast.makeText(getApplicationContext(),"Exception: Try again",Toast.LENGTH_SHORT).show();
                            return;
                        }catch (ExecutionException ex){
                            Toast.makeText(getApplicationContext(),"Exception: Try again",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(result==null){
                            Toast.makeText(getApplicationContext(),"Check Internet connection!",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(result.equalsIgnoreCase("successful")){
                            Toast.makeText(getApplicationContext(),"QR code set!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"QR Code not set, try again!",Toast.LENGTH_LONG).show();
                        }

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
