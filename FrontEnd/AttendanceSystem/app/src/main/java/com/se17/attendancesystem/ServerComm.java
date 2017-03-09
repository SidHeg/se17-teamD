package com.se17.attendancesystem;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by saravanan on 2/23/17.
 */



public class ServerComm extends AsyncTask<String, Boolean, String>{



    @Override
    protected String doInBackground(String... params) {

        URL url;
        HttpsURLConnection client;
        StringBuilder sb;
        try
        {
            url = new URL("https://project1-007.herokuapp.com/attendance/");
           // url = new URL("107.13.184.174");
            client = (HttpsURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            String payload;

            if(params[0].equalsIgnoreCase("0")){
                payload = " { \"status\" : \"login\" , \"user_id\": \"" +params[1]+ "\", \"password\": \""+params[2]+"\", \"course_id\" : \"csc510\" }";
                System.out.println("Message sent: " + payload);
            }else if(params[0].equalsIgnoreCase("1")){
                payload = " { \"status\" : \"NFC\" , \"user_id\": \"" +params[1]+ "\", \"password\": \""+params[2]+ "\" , \"text\": \""+params[3]+"\", \"course_id\" : \"csc510\" }";
                System.out.println("Message sent: " + payload);
            }else if(params[0].equalsIgnoreCase("2")){
                payload = " { \"status\" : \"GPS\" , \"user_id\": \"" +params[1]+ "\", \"password\": \""+params[2]+ " \", \"latitude\": \""+params[3]+ "\" , \"longitude\": \""+params[4]+"\", \"course_id\" : \"csc510\" }";
                System.out.println("Message sent: " + payload);
            }else{
                payload = " { \"status\" : \"QRC\" , \"user_id\": \"" +params[1]+ "\", \"password\": \""+params[2]+ "\" , \"text\": \""+params[3]+"\", \"image\": \""+params[4]+"\", \"course_id\" : \"csc510\" }";
                System.out.println("Message sent: " + payload);
            }

            OutputStream outstream = client.getOutputStream();
            outstream.write(payload.getBytes());
            outstream.flush();
            outstream.close();

            int responseCode = client.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }else{
                System.out.println("HTTP 200 ok");
            }

            InputStream inputStream = client.getInputStream();
            int i=0;
            System.out.print("Message received: ");
             sb = new StringBuilder();
            while((i=inputStream.read())!=-1)
            {
                char c=(char)i;
                sb.append(c);
                System.out.print(c);
            }
            System.out.println();


            client.disconnect();

        }catch (MalformedURLException ex1 ){
            System.out.println("MalformedURLException");
            return null;
        }catch (IOException ex2){
            System.out.println("IOException");
            return null;
        }
        System.out.println("Successful");
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
    }
}
