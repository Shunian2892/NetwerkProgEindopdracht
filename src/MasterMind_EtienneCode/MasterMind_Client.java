package MasterMind_EtienneCode;

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
import java.util.Scanner;

public class MasterMind_Client extends Application implements MasterMindContestants {
    private String hostName;
    private int port;
    private boolean isConnected = true;
    private Socket socket;

//    private boolean myTurn = false;
//    private char myToken =' ';
//    private char otherToken = ' ';
//    private boolean waitingForPlayer = true;
//    private boolean continuePlay = true;
//    private String codeToBreak = "", tryingCode;

    private BorderPane borderPane = new BorderPane();
    private TextArea readMessagesArea = new TextArea();
    private TextField writeMessagesField = new TextField("");
    private Button sendMessage = new Button("send");

    public MasterMind_Client(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }

    public static void main(String[] args) {
        MasterMind_Client client = new MasterMind_Client("localhost", 10000);
        client.connect();
    }

      @Override
    public void start(Stage primaryStage){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(writeMessagesField, sendMessage);

        borderPane.setCenter(readMessagesArea);
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MasterMind_Client");
        primaryStage.show();
    }


    private void connect() {
        System.out.println("Connecting to server: " + this.hostName);

            Scanner scanner = new Scanner(System.in);

            try {
                socket = new Socket(this.hostName, this.port);

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                Thread readSocketThread = new Thread( () -> {
                    receiveDataFromSocket(in);
                });
                readSocketThread.start();

            sendMessage.setOnAction(event -> {
                try {
                    String name = in.readUTF();
                    readMessagesArea.appendText(name + " has connected!" + "\n");

                    String input = "";
                    while(!input.equals("quit")){
                        out.writeUTF(input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


//            System.out.println("Enter a nickname: ");
//            String name = scanner.nextLine();
//            out.writeUTF(name);
//            System.out.println(name + " has connected to the server!");
//
//            String input = "";
//            while(!input.equals("quit")){
//                input = scanner.nextLine();
//                out.writeUTF(input);
//            }



//            Thread guiThread = new Thread(() -> {
//                try {
//                    int player = in.readInt();
//                    if (player == PLAYER1) {
//                        Platform.runLater(() -> {
//                            readMessagesArea.appendText("Waiting for player two");
//                        });
//
//                        sendMessage.setOnAction(event -> {
//                            codeToBreak = writeMessagesField.getText();
//                        });
//
//                        //TODO implement code to make a code from the characters B, Y, G, R, P, O
//
//                    } else if (player == PLAYER2) {
//                        if (codeToBreak.equals("")) {
//                            Platform.runLater(() -> {
//                                readMessagesArea.appendText("Waiting on player one to make a code...");
//                            });
//                        }
//
//                        if (!codeToBreak.equals("")) {
//                            //TODO implement code to guess the code with the characters B, Y, G, R, P, O
//                            sendMessage.setOnAction(event -> {
//                                tryingCode = writeMessagesField.getText();
//                                readMessagesArea.appendText(tryingCode);
//                            });
//                        }
//                    }
//
//                    while (continuePlay) {
//                        if (player == PLAYER1) {
//                            //TODO maybe implement like tictactoe example?
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            });
//            guiThread.start();



            try {
//              guiThread.join();
                readSocketThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            isConnected = false;
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receiveDataFromSocket(DataInputStream in) {
        String receiving = "";
        while(isConnected){
            try{
                receiving = in.readUTF();
                System.out.println(receiving);
            } catch (IOException e){
                System.out.println("Goodbye!");
            }
        }
    }

//    public void writeStringToSocket(Socket socket, String text) {
//
//        try {
//            socket.getOutputStream().write(text.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
