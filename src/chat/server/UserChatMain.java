package chat.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class UserChatMain {
    public static void main(String[] args) throws RemoteException {
        IServerChat serverChat = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 2020); // Obtenha o registro RMI na porta 2020
            serverChat = (IServerChat) registry.lookup("Servidor"); // Procure pelo objeto remoto com o nome "Servidor" no registro RMI
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        IUserChat userChat = new UserChat("User", serverChat); // Substitua o segundo argumento pelo servidor de chat apropriado
        try {
            serverChat.registerUser(userChat);
        } catch (RemoteException e) {
            System.out.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        while (running) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                running = false; // Termina o loop quando "exit" é inserido
            } else {
                IRoomChat currentRoom = userChat.getCurrentRoom();
                if (currentRoom != null) {
                    try {
                        currentRoom.sendMsg(userChat.getUserName(), message);
                    } catch (RemoteException e) {
                        System.out.println("Error sending message: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error: No room joined."); // Exibe uma mensagem de erro adequada caso o usuário não tenha entrado em uma sala
                }
            }
        }
        
        scanner.close();
        
}}
