package chat.server;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RoomChatMain {

    private static IServerChat serverChat;
    private static IRoomChat currentRoom;
    private static IUserChat userChat;
    private static String userName = "User"; 

    public static void main(String[] args) {
        // Crie e mostre a GUI.
        currentRoom = null;

        try {
            Registry registry = LocateRegistry.getRegistry(2020); // Obtenha o registro RMI na porta 2020
    
            serverChat = (IServerChat) registry.lookup("Servidor"); // Procure pelo objeto remoto com o nome "Servidor" no registro RMI

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    
    

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Chat Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JTextArea textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        JComboBox<String> roomList = new JComboBox<>();
        updateRoomList(roomList);

        JTextField textField = new JTextField(40);

        JButton sendButton = new JButton("Send Message");
        JButton joinButton = new JButton("Join Room");
        JButton leaveButton = new JButton("Leave Room");
        JButton createButton = new JButton("Create Room");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(roomList, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(joinButton);
        buttonPanel.add(leaveButton);
        buttonPanel.add(createButton);

        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentRoom.sendMsg(userName, textField.getText());
                    textArea.append(userName + ": " + textField.getText() + "\n");
                    textField.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String roomName = (String) roomList.getSelectedItem();
                    System.out.println("Nome da Sala:" +roomName);
                    IRoomChat roomChat = (IRoomChat) Naming.lookup("rmi://localhost/" + roomName);
                    roomChat.joinRoom(userName, userChat);
                    currentRoom = roomChat;
                    System.out.println("Sala:" +currentRoom);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currentRoom != null) {
                        currentRoom.leaveRoom(userName);
                        currentRoom = null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = JOptionPane.showInputDialog(frame, "Room name:");
                if (roomName != null && !roomName.trim().isEmpty()) {
                    try {
                        serverChat.createRoom(roomName);
                        System.out.println("Sala Criada;");
                        updateRoomList(roomList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private static void updateRoomList(JComboBox<String> roomList) {
        try {
            if(serverChat != null) {
                java.util.List<String> rooms = serverChat.getRooms();
                roomList.removeAllItems();
                for (String room : rooms) {
                    roomList.addItem(room);
                }
                System.out.println("Salas atualizadas;" + roomList);

            } else {
                // Exibe uma mensagem de erro ou executa ações para lidar com a situação quando serverChat é null.
                String errorMessage = "Erro: serverChat é null. Verifique a conexão com o servidor RMI.";
                JOptionPane.showMessageDialog(null, errorMessage, "Erro de conexão", JOptionPane.ERROR_MESSAGE);
                System.out.println("Erro: serverChat é null. Verifique a conexão com o servidor RMI.");
                
                // Limpa a lista de salas.
                roomList.removeAllItems();           
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}