package chat.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerChat extends Remote {
    List<String> getRooms() throws RemoteException;
    void createRoom(String roomName) throws RemoteException;
    void registerUser(IUserChat user) throws RemoteException;
}
