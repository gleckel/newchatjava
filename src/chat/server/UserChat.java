package chat.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserChat extends UnicastRemoteObject implements IUserChat {
    private String userName;
    private IServerChat serverChat;
    private IRoomChat currentRoom; // Adicionado o campo currentRoom

    

    public UserChat(String userName, IServerChat serverChat) throws RemoteException {
        this.userName = userName;
        this.serverChat = serverChat;
    }

    @Override
    public void deliverMsg(String senderName, String msg) throws RemoteException {
        System.out.println(senderName + ": " + msg);
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public IRoomChat getCurrentRoom() {
        return this.currentRoom;
    }
    public void setCurrentRoom(IRoomChat room) {
        this.currentRoom = room;
    }
}
