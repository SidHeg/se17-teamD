package com.se17.attendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userId;
    private EditText password;
    private Button btnSignIn;
    public static User user;

    private View.OnClickListener signInOnClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //user.setUserId(userId.getText().toString());
            String userIdEntered = userId.getText().toString();
            String passwordEntered = password.getText().toString();

           // ServerComm serverComm = new ServerComm();
            //serverComm.execute("0",userIdEntered,passwordEntered);

            /*
                Todo: Validate username and password, and get response from backend.
                1 => Professor, 2=> Student, else=> Log in failed.
             */
            int response = 2;
            if(response == 1){
                user = new User();
                user.setUserId(userIdEntered);
                user.setPassword(passwordEntered);
                user.setProfessor(true);
                Intent intent = new Intent(getBaseContext(), ProfessorActivity.class);
                startActivity(intent);

            }else if(response == 2){
                user = new User();
                user.setStudent(true);
                user.setUserId(userIdEntered);
                user.setPassword(passwordEntered);
                Intent intent = new Intent(getBaseContext(), StudentActivity.class);
                startActivity(intent);

            }else{

                Toast.makeText(getApplicationContext(),"Wrong Credentials!",Toast.LENGTH_LONG).show();
                return;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        userId = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnSignIn.setOnClickListener(signInOnClickListener);
    }

}
