package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    String mesaj="";
    Typeface tf1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //e1=(EditText) findViewById(R.id.editTextTextPersonName2);

        t1=(TextView) findViewById(R.id.textView);
        t1.setMovementMethod(new ScrollingMovementMethod());
        tf1= Typeface.createFromAsset(getAssets(),"font/Cantata.ttf");
        t1.setTypeface(tf1);


        Thread myThread=new Thread(new MyServerThread());
        myThread.start();



    }

    class MyServerThread implements Runnable
    {
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader br;
        Handler h = new Handler();
        String message;

         Thread thread;
         ServerSocket serverSocket;
         Socket socket;
         DataInputStream dataInputStream;
         DataOutputStream dataOutputStream;






        @Override
        public void run() {
            try
            {
                serverSocket = new ServerSocket( 8080 );
            }
            catch ( IOException e )
            {
                System.out.println( "failed to start server socket" );
                e.printStackTrace();
            }

            // wait for a connection
            System.out.println( "waiting for connections..." );


            while ( true )
            {
                try
                {
                    socket = serverSocket.accept();
                }
                catch ( IOException e )
                {
                    System.out.println( "failed to accept" );
                    e.printStackTrace();
                }
                System.out.println( "client connected" );
                try
                {
                    dataInputStream = new DataInputStream( new BufferedInputStream( socket.getInputStream() ) );
                    mesaj += dataInputStream.readUTF()+"\n\n";
                    t1.setText(mesaj);




                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                    break;
                }
            }

            System.out.println( "server thread stopped" );
                }
            }


}