package chat.server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerChat extends UnicastRemoteObject implements IServerChat {
    private List<String> roomList;
    private Map<String, IUserChat> userList;
   

    public ServerChat() throws RemoteException {
        this.roomList = new ArrayList<>();
        userList = new HashMap<>();
    }

    @Override
    public List<String> getRooms() throws RemoteException {
        return this.roomList;
    }

    @Override
    public void createRoom(String roomName) throws RemoteException {
        this.roomList.add(roomName);
    }

    @Override
    public void registerUser(IUserChat user) throws RemoteException {
        // Adicionar o usuário à lista de usuários registrados
        userList.put(user.getUserName(), user);
        System.out.println("Usuário registrado: " + user.getUserName());
   
    }
}
