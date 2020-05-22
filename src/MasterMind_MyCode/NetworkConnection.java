package MasterMind_MyCode;

import java.io.Serializable;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private Consumer<Serializable> onReceiveCallback;

    public NetworkConnection(Consumer<Serializable> onReceived){
        this.onReceiveCallback = onReceived;
    }

    public void startConnection() throws Exception{

    }

}
