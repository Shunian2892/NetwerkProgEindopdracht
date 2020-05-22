package MasterMind_MyCode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        TextArea readMessagesArea = new TextArea();
        TextField writeMessagesField = new TextField();
        Button sendMessage = new Button("Send");
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(writeMessagesField, sendMessage);

        borderPane.setCenter(readMessagesArea);
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("MasterMind_Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        sendMessage.setOnAction(event -> {
            try {
                String text = writeMessagesField.getText();
                out.writeUTF(text);
                out.flush();

                String name = in.readUTF();

                readMessagesArea.appendText("<" + name + "> has connected to the server!" + "\n");

                String input = "";
                while(!input.equals("quit")){
                    out.writeUTF(input);
                    readMessagesArea.appendText(input);
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        });

        try{
            socket = new Socket("localhost", 10000);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
