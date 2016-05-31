package com.qomostudio.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by robin on 5/31/16.
 */
public class SocketClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.start();
    }

    private void start() {
        BufferedReader inputReader = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 9090);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            startServerReplyListener(reader);
            String inputContent;
            while (!(inputContent = inputReader.readLine()).equals("bye")) {
                writer.write(inputContent + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                inputReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startServerReplyListener(final BufferedReader reader) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response;
                    while ((response = reader.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
