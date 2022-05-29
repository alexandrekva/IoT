package com.example.alarmapp.Service;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, Void> {

    String address;
    Integer port;
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;


    public MessageSender(String address, Integer port) {
        this.address = address;
        this.port = port;
    }



    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];

        try {
            s = new Socket(address, port);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        return null;
    }
}
