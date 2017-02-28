package com.se17.attendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

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

            ServerComm serverComm = new ServerComm();
            String result="";
            try {
                 result = serverComm.execute("0", userIdEntered, passwordEntered).get();
                if(result!=null && !result.equalsIgnoreCase("failed"))
                    Toast.makeText(getApplicationContext(),"Login Successful: "+result,Toast.LENGTH_SHORT).show();
            }catch (InterruptedException ex){

            }catch (ExecutionException ex){

            }

            if(result==null){
                Toast.makeText(getApplicationContext(),"Check your Internet Connection",Toast.LENGTH_LONG).show();
                return;
            }

            if(result.equalsIgnoreCase("professor")){
                user = new User();
                user.setUserId(userIdEntered);
                user.setPassword(passwordEntered);
                user.setProfessor(true);
                Intent intent = new Intent(getBaseContext(), ProfessorActivity.class);
                startActivity(intent);

            }else if(result.equalsIgnoreCase("student")){
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
