package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> registerClients; // kolekcja z użytkownikami
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChatLog() {
        registerClients = new ConcurrentHashMap<>();
    }

    public boolean register(Socket client) {
        try { // nie try-with-resources
            ObjectOutputStream streamToClient = new ObjectOutputStream(client.getOutputStream());
            registerClients.put(client, streamToClient);
            return true;
        } catch (IOException e) {
            System.out.println("### Someone tried to connect, but was rejected ###");
            return false;
        }
        // zapisz klienta w kolekcji wszystkich klientów
    }

    public boolean unregister(Socket client) {
        ObjectOutputStream connectionToRemovedClient = registerClients.remove(client);
        if (connectionToRemovedClient != null) {
            try {
                connectionToRemovedClient.close();
                return true;
            } catch (IOException e) {
                // nothing to do
            }
        }
        return false;
        // usuń klienta z kolekcji wszystkich klientów
    }

    public void acceptMessage(ChatMessage message) {
        DatedChatMessage datedMessage = new DatedChatMessage(message);
        printMessage(datedMessage);
        updateClients(datedMessage);
//        registerClients.entrySet().forEach(x -> {
//            try {
//                x.getValue().writeObject(datedMessage);
//                x.getValue().flush();
//            } catch (IOException e) {
//                unregister(x.getKey());
//            }
//        });

        // przekonwertuj ChatMessage na DatedChatMessage
        // wpisz na ekran wiadomość w formacie:
        //     <Data> <Author>: <Message>
        // wyślij DatedChatMessage do wszystkich klientów
        //  jeżeli nie udało się wysłać komunikatu do którego z klientów
        //  to wyrejestruj tego klienta
    }

    private void printMessage(DatedChatMessage datedMessage) {
        System.out.println(dateFormatter.format(datedMessage.getReceiveDate())
                + " " + datedMessage.getAuthor()
                + ": " + datedMessage.getMessage());
    }

    private void updateClients(DatedChatMessage datedMessage) {
        Set<Map.Entry<Socket, ObjectOutputStream>> allEntries = registerClients.entrySet();
        for (Map.Entry<Socket, ObjectOutputStream> entry : allEntries) {
            ObjectOutputStream connectionToClient = entry.getValue();
            try {
                connectionToClient.writeObject(datedMessage);
                connectionToClient.flush();
            } catch (IOException e) {
                unregister(entry.getKey());
            }
        }
//        for (Socket client : registerClients.keySet()) {
//            registerClients.get(client);
//        }
    }
}
