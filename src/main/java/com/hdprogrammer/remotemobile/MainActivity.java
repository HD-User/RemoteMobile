package com.hdprogrammer.remotemobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public static Socket socket;
    public static PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(view -> {
            new Thread(() -> {
                Looper.prepare();
                String ip = ((EditText)findViewById(R.id.txtIP)).getText().toString();
                int port = Integer.parseInt(((EditText)findViewById(R.id.txtPort)).getText().toString());
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), 500);
                    out = new PrintWriter(socket.getOutputStream(), true);

                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show());
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show());
                    System.out.println("btnConnect-OnClickListener: " + e.getClass());
                }
            }).start();
        });
    }
}