package org.example.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerFormController extends Application {

    @FXML
    private Button btnSend;
    @FXML
    private TextArea txtAreaServer;

    @FXML
    private TextField txtSendServer;

    String message="";
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    public void initialize() {
        new Thread(() ->{
            try {
                socket=new Socket("localhost",5000);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtAreaServer.appendText("\nClient: "+message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtSendServer.getText().trim());
        dataOutputStream.flush();
    }

    @FXML
    void txtSendServerOnAction(ActionEvent event) {

    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.
                load(this.getClass().getResource("/view/ServerForm.fxml"))));
        stage.show();
    }
}
