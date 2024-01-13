package org.example.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController extends Application {

    @FXML
    private Button btnSend;

    @FXML
    private TextArea txtAreaClient;

    @FXML
    private TextField txtSend;

    String message="";
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    public void initialize() {
        new Thread(() -> {
            try {

                socket=new Socket("localhost", 3000);
                txtAreaClient.appendText("Server Started..");
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                txtAreaClient.appendText("\nClient Connected..");
                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtAreaClient.appendText("\nServer: "+message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();;
    }
    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtSend.getText().trim());
        dataOutputStream.flush();
    }
    @FXML
    void txtSendOnAction(ActionEvent event) {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientForm.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
