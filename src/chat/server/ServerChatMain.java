package chat.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerChatMain {
    public static void main(String[] args) {
        try {
             // Cria o objeto remoto ServerChat
             ServerChat server = new ServerChat();
            // Cria e exporta um registro na porta 2020 no host local
            Registry registry = LocateRegistry.createRegistry(2020);
                      
            // Registra o objeto remoto ServerChat no registro
            registry.rebind("Servidor", server);
            
            System.out.println("Servidor iniciado e pronto para aceitar conexões...");

        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
