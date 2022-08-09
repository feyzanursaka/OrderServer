package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextView t1;//gelen mesajların gösterildiği textview tanımı
    String mesaj = "";//gelen mesajın tutuldugu string tanımı
    Typeface tf1;//yazı tipi uygulamak için typeface tanımlama

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.textView);//gelen mesajı gösteren textview'i id den bulup t1'e atama
        t1.setMovementMethod(new ScrollingMovementMethod());//textview için sayfayı kaydırma özelliği ekleme
        tf1 = Typeface.createFromAsset(getAssets(), "font/Cantata.ttf");//yazı tipi kullanma
        t1.setTypeface(tf1);//textviewde gösterilen yazıya cantata yazıtipi uygulama

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();
    }

    class MyServerThread implements Runnable {
        ServerSocket serverSocket;
        Socket socket;
        DataInputStream dataInputStream;
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8080);//8080 portunu kullanmak için belirtme
                System.out.println("waiting for connections...");
            } catch (IOException e) {
                System.out.println("failed to start server socket");//porta bağlanamazsa hata gösterme
                e.printStackTrace();
            }
            while (true) {
                try {
                    socket = serverSocket.accept();//server başladıktan sonra bağlanan clientı kabul etme
                    System.out.println("client connected");
                    dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));//geken mesajı datainputstream ile alma
                    mesaj += dataInputStream.readUTF() + "\n\n";//readUTF ile string mesajı datainputstreamden okuma
                    t1.setText(mesaj);//gelen mesajı textviewde gösterme
                } catch (IOException e) {
                    System.out.println("failed to accept");//bağlantı kurulamadıysa hata göster
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}