package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> registerClients; // kolekcja z użytkownikami

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
        ObjectOutputStream clientToRemove = registerClients.remove(client);
        if (clientToRemove != null) {
            try {
                clientToRemove.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
        // usuń klienta z kolekcji wszystkich klientów
    }

    public void acceptMessage(ChatMessage chatMessage) {
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);
        System.out.println("<" + datedChatMessage.getReceiveDate() + "> <"
                + datedChatMessage.getAuthor() + ">: "
                + datedChatMessage.getMessage() + ">");
        registerClients.entrySet().forEach(x -> {
            try {
                x.getValue().writeObject(datedChatMessage);
                x.getValue().flush();
            } catch (IOException e) {
                unregister(x.getKey());
            }
        });

        // przekonwertuj ChatMessage na DatedChatMessage
        // wpisz na ekran wiadomość w formacie:
        //     <Data> <Author>: <Message>
        // wyślij DatedChatMessage do wszystkich klientów
        //  jeżeli nie udało się wysłać komunikatu do którego z klientów
        //  to wyrejestruj tego klienta
    }
}
