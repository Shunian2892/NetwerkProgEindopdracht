package MasterMind_EtienneCode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class MasterMind_Server extends Application {
    private int port = 10000;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream in;

    private ArrayList<MasterMind_ServerClient> clients = new ArrayList<>();
    private HashMap<String, Thread> clientThreads = new HashMap<>();

    public static void main(String[] args) {
        MasterMind_Server server = new MasterMind_Server();
        server.connect();
        System.out.println("Starting mastermind server...");
    }

    private void connect() {
        Thread connectThread = new Thread();

        try {
            this.serverSocket = new ServerSocket(port);
            boolean isRunning = true;

            while (isRunning) {
                if (clients.size() < 3) {
                    System.out.println("Waiting for client");
                    this.socket = serverSocket.accept();

                    System.out.println("Client connected via address: " + socket.getInetAddress().getHostAddress());

                    in = new DataInputStream(socket.getInputStream());
                    String name = in.readUTF();

                    MasterMind_ServerClient serverClient = new MasterMind_ServerClient(socket, name, this);
                    Thread t = new Thread(serverClient);
                    t.start();

                    this.clientThreads.put(name, t);
                    this.clients.add(serverClient);

                }
                // System.out.println(clients.size());
            }

            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToAllClients(String text) {
        for (MasterMind_ServerClient client : clients) {

            client.writeUTF(text);
        }
    }

    public void removeClient(MasterMind_ServerClient serverClient) {
        String name = serverClient.getName();
        this.clients.remove(serverClient);

        Thread t = this.clientThreads.get(name);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.clientThreads.remove(name);
    }

//    public void writeStringToSocket(Socket socket, String text) {
//
//        try {
//            socket.getOutputStream().write(text.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public ArrayList<MasterMind_ServerClient> getClients() {
        return clients;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        TextArea readMessagesArea = new TextArea();
//
//        Scene scene = new Scene(new ScrollPane(readMessagesArea), 450, 200);
//        primaryStage.setTitle("MasterMind_Server");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        connect();
    }
}
