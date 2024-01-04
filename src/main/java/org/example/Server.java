package org.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @TimeStamp 2024-01-04 22:35
 * @ProjectDetails ChatApplication02
 */
public class Server {
    public static void main(String[] args) {
        try {
            // Create a server socket and bind to port
            ServerSocket serverSocket = new ServerSocket(3000);

            // Accept connection and move to a new socket
            Socket socket = serverSocket.accept();

            // Client accepted
            System.out.println("Client Accepted!");

            // Output stream used for writing data to the client
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Input stream used for reading data from the client
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String message = "";
            String reply = "";

            while (!message.equals("finish")) {
                // Receive and print the client's message
                message = dataInputStream.readUTF();
                System.out.println("Client: " + message);

                // Read server's reply from console
                reply = bufferedReader.readLine();

                // Send the reply back to the client
                dataOutputStream.writeUTF(reply);
                dataOutputStream.flush();
            }

            // Close streams and socket
            dataInputStream.close();
            dataOutputStream.close();
            bufferedReader.close();
            socket.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
