package chat.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserChat extends Remote {
    void deliverMsg(String senderName, String msg) throws RemoteException;
    String getUserName() throws RemoteException;
    IRoomChat getCurrentRoom() throws RemoteException; // Adicionado o método getCurrentRoom()
}
