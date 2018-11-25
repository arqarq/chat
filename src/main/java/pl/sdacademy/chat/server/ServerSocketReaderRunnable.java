package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

class ServerSocketReaderRunnable implements Runnable {
    private final Socket client;
    private final ChatLog chatLog;

    ServerSocketReaderRunnable(Socket client, ChatLog chatLog) {
        this.client = client;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        // zarejestruj klienta w chatlogu
        // jeżeli się udało, to pobierz ObjectInputStream dla tego klienta (try-with-resources)
        // w pętli odczytujemy komunikaty od klienta tak długo, aż pojawi się "exit"
        // komunikat przekaż do ChatLogu, ale nie przekazuj komunikaty "exit"
        // jeżeli pojawił się exit, lub nie udało się odczytać komunikatu od klienta
        // to wyrejestruj się z chatlogu i zakończ to zadanie

        if (!chatLog.register(client)) { // fail fast
            return;
        }
        try (ObjectInputStream clientInput = new ObjectInputStream(client.getInputStream())) {
            processClientInput(clientInput);
        } catch (IOException e) {
            System.out.println("### Client disconnected due to network problem ###");
        } catch (ClassNotFoundException e) {
            System.out.println("### Client disconnected due to invalid message format ###");
        }
        chatLog.unregister(client);
    }

    private void processClientInput(ObjectInputStream clientInput) throws IOException, ClassNotFoundException {
        while (true) {
            ChatMessage chatMessage = (ChatMessage) clientInput.readObject();
            if (chatMessage.getMessage() == null ||
                    chatMessage.getMessage().equalsIgnoreCase("exit")) {
                break;
            }
            chatLog.acceptMessage(chatMessage);
        }
    }
}
