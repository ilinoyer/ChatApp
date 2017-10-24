package sample;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main extends Application {

    private static String name;
    private static String ip;
    static ArrayList<String> bufforToSend = new ArrayList<String>(1);
    static ArrayList<String> bufforToWrite = new ArrayList<String>(1);

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = new GridPane();

        Label title = new Label("Login");
        title.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 46));
        title.setMinSize(600,66);
        root.add(title,1,1,2,1);

        Label nickname = new Label("Nickname: ");
        nickname.setFont(new Font("Arial", 23));
        nickname.setMinSize(300,66);
        nickname.setAlignment(Pos.CENTER);
        root.add(nickname, 1, 2, 1, 1);

        Label serverIp = new Label("Server IP: ");
        serverIp.setFont(new Font("Arial", 23));
        serverIp.setMinSize(300,66);
        serverIp.setAlignment(Pos.CENTER);
        root.add(serverIp, 1, 3, 1, 1);

        TextField nicknameInput = new TextField();
        nicknameInput.setMaxSize(250, 33);
        root.add(nicknameInput,2,2,1,1);

        TextField serverIpInput = new TextField();
        serverIpInput.setMaxSize(250, 33);
        root.add(serverIpInput,2,3,1,1);

        Button connect = new Button("Connect");
        connect.setFont(new Font("Arial", 23));
        root.add(connect,1,4,1,1);

        primaryStage.setTitle("Instant Messenger");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.setResizable(false);
        primaryStage.show();

        GridPane rootMessenger = new GridPane();

        TextArea chatViwevField = new TextArea();
        chatViwevField.setEditable(false);
        chatViwevField.setMinSize(600, 300);
        rootMessenger.add(chatViwevField, 1,1, 2,1);

        TextField messageField = new TextField();
        messageField.setMinSize(520, 33);
        rootMessenger.add(messageField, 1,2);

        Button sendButton = new Button("Send");
        sendButton.setMinSize(80,33);
        rootMessenger.add(sendButton, 2,2);

        sendButton.setOnAction(new EventHandler<ActionEvent>()  {
            public void handle(ActionEvent event){
               bufforToSend.add(messageField.getText());
               messageField.clear();
            }

        });

        Thread showMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while(true)
                {
                    try {
                            chatViwevField.appendText(bufforToWrite.get(0) + "\n");
                            bufforToWrite.remove(0);
                        }catch(Exception e) {}

                }

            }

        });

        connect.setOnAction(new EventHandler<ActionEvent>()  {
            public void handle(ActionEvent event){
                name = nicknameInput.getText();
                ip = serverIpInput.getText();
                primaryStage.setScene(new Scene(rootMessenger, 600, 400));
                primaryStage.setResizable(false);
                primaryStage.show();

                User user = new User(name);
                Client client = new Client(user, ip);
                try {
                    client.runClient();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                }
            });
        showMessage.start();
    }


    public static void main(String[] args) throws Exception
    {

        launch(args);
    }
}
