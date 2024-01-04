package org.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @TimeStamp 2024-01-04 22:35
 * @ProjectDetails ChatApplication02
 */
public class Client {
    public static void main(String[] args) {
        try {
            // Open a socket and specify server address and port
            Socket socket = new Socket("localhost", 3000);

            // Output stream used for writing data to the server
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String message = "";
            String reply = "";

            while (!message.equals("finish")) {
                // Read user input
                reply = bufferedReader.readLine();

                // Send the user input to the server
                dataOutputStream.writeUTF(reply);
                dataOutputStream.flush();

                // Receive and print the server's response
                message = dataInputStream.readUTF();
                System.out.println("Server: " + message);
            }

            // Close streams and socket
            dataInputStream.close();
            dataOutputStream.close();
            bufferedReader.close();
            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
