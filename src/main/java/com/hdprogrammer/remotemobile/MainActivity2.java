package com.hdprogrammer.remotemobile;

import static com.hdprogrammer.remote69.MainActivity.out;
import static com.hdprogrammer.remote69.MainActivity.socket;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity2 extends AppCompatActivity {

    SoftInputAssist sia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sia = new SoftInputAssist(this);

        new Thread(() -> {
            
            while (true) {
                try {
                    Thread.sleep(500);
                    if(!sendMessage("test")){
                        throw new IOException("Connection Checker Thread-Connection Lost");
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed: " + e.getMessage());
                    runOnUiThread(() ->{
                        Toast.makeText(MainActivity2.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                    break;
                } catch (Exception e) {
                    System.out.println("Connection Checker Thread: " + e.getClass() + ": " + e.getMessage());
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sia.onDestroy();
        new Thread(() -> {
            try {
                socket.close();
                out.close();
                System.out.println("onDestroy: 'socket' and 'out' closed");
            } catch (IOException e2) { System.out.println("onDestroy: Could not close socket: " + e2.getMessage()); }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sia.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sia.onResume();
    }

    protected boolean sendMessage(String message) {
        try {
            out.print(message);
            out.flush();

            byte[] buffer = new byte[8192];
            int bytesRead = socket.getInputStream().read(buffer);
            if (bytesRead == -1) {
                throw new IOException("End of stream reached unexpectedly");
            }

            String serverMessage = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).replaceAll("\n", "");
            System.out.println("Server Message: " + serverMessage);

            return !serverMessage.isEmpty();

        } catch (Exception e) {
            System.out.println("sendMessage: " + e.getClass() + ": " + e.getMessage());
            return false;
        }
    }

    public void ShutDown(View view){
        new Thread(() -> {
            
            if(sendMessage("1")){
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            }else{
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    public void Restart(View view){
        new Thread(() -> {
            
            if(sendMessage("2")){
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            }else{
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
    public void PlaySound(View view){
        new Thread(() -> {
            
            String cmd = "play" + ((EditText)findViewById(R.id.txtPlaySound)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Pause(View view){
        new Thread(() -> {
            
            if(sendMessage("pause")){
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            }else{
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void OpenPath(View view){
        new Thread(() -> {
            
            String cmd = "start" + ((EditText)findViewById(R.id.txtOpenFilePath)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void OpenDesktop(View view){
        new Thread(() -> {
            
            String cmd = "desktop" + ((EditText)findViewById(R.id.txtOpenFileDesktop)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Volume(View view){
        new Thread(() -> {
            
            String cmd = "vol" + ((EditText)findViewById(R.id.txtVol)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Wallpaper(View view){
        new Thread(() -> {
            
            String cmd = "bgp" + ((EditText)findViewById(R.id.txtBgp)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void POS(View view){
        new Thread(() -> {
            
            if(sendMessage("POS")){
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            }else{
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Cmd(View view){
        new Thread(() -> {
            
            String cmd = "cmd" + ((EditText)findViewById(R.id.txtCmd)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Kill(View view){
        new Thread(() -> {
            
            String cmd = "kill" + ((EditText)findViewById(R.id.txtKill)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public void Msg(View view){
        new Thread(() -> {
            
            String cmd = "msg" + ((EditText)findViewById(R.id.txtMsg)).getText().toString();
            if (sendMessage(cmd)) {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "Executed", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity2.this, "FAILED", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}