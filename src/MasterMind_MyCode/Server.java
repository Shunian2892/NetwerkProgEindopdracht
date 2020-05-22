package MasterMind_MyCode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Application {
    private int port = 10000;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream inP1, inP2;
    private DataOutputStream out;
    private Socket player1, player2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea readMessagesArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(readMessagesArea), 450, 200);
        primaryStage.setTitle("MasterMind_Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                Platform.runLater(() -> {
                    readMessagesArea.appendText("Server started \n");
                });

                while(true){

                    player1 = serverSocket.accept();
                    Platform.runLater(() -> {
                        readMessagesArea.appendText("Player 1 has joined \n");
                        readMessagesArea.appendText("Please type in nickname: ");
                    });

                    inP1 = new DataInputStream(player1.getInputStream());

                    String nameP1 = inP1.readUTF();
                    String textP1 = inP1.readUTF();
                    new DataOutputStream(player1.getOutputStream()).writeUTF(nameP1 + ": " + textP1);

                    player2 = serverSocket.accept();
                    Platform.runLater(() -> {
                        readMessagesArea.appendText("Player 2 has joined \n");
                        readMessagesArea.appendText("Please type in nickname: ");
                    });

                    inP2 = new DataInputStream(player2.getInputStream());
                    String nameP2 = inP2.readUTF();
                    String textP2 = inP2.readUTF();
                    new DataOutputStream(player2.getOutputStream()).writeUTF(nameP2 + ": " + textP2);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }




    public static void main(String[] args) {
        launch(Server.class);
    }
}
