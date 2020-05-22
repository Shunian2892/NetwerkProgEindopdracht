package MasterMind_EtienneCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MasterMind_ServerClient implements Runnable{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private MasterMind_Server server;
    private boolean isConnected = true;


    public MasterMind_ServerClient(Socket socket, String name, MasterMind_Server server){
        this.socket = socket;
        this.name = name;
        this.server = server;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUTF(String text){
        try {
            this.out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        while(isConnected){
           // String receiving = "";
            String receiving = null;
            try {
                receiving = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (receiving.equals("quit") ){
                isConnected = false;
                this.server.removeClient(this);
            }
            if (server.getClients().size() == 3){
                isConnected = false;
                sendToLastClient();
                this.server.removeClient(this);
               // System.out.println(server.getClients().size());
            } else{
                this.server.sendToAllClients("<" + this.name + "> : " + receiving);
            }
        }
    }

    public String getName(){
        return name;
    }

    public void sendToLastClient(){
        MasterMind_ServerClient lastClient = server.getClients().get(server.getClients().size()-1);
        lastClient.writeUTF("A game has already begun...");

    }
}
