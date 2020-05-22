import javafx.application.Application;
import javafx.application.Platform;
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

public class MasterMind_Client implements MasterMindContestants {
    private String hostName;
    private int port;
    private boolean isConnected = true;
    private Socket socket;

    private boolean myTurn = false;
    private char myToken =' ';
    private char otherToken = ' ';
    private boolean waitingForPlayer = true;
    private boolean continuePlay = true;
    private String codeToBreak = "", tryingCode;

    public MasterMind_Client(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }

    public static void main(String[] args) {
        MasterMind_Client client = new MasterMind_Client("localhost", 10000);
        client.connect();
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

            System.out.println("Enter a nickname: ");
            String name = scanner.nextLine();
            out.writeUTF(name);
            System.out.println(name + " has connected to the server!");

            String input = "";
            while(!input.equals("quit")){
                input = scanner.nextLine();
                out.writeUTF(input);
            }

            isConnected = false;
            socket.close();

            try {

                readSocketThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
