package pl.sdacademy.chat.client;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {
    private final Socket connectionToServer;

    public ClientTerminal() throws IOException {
        connectionToServer = new Socket("IP", 5557);
    }

    @Override
    public void run() {
        try (ObjectOutputStream streamToServer = new ObjectOutputStream(connectionToServer.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Your username: ");
            String username = scanner.nextLine();
            String message;
            do {
                System.out.print("> ");
                message = scanner.nextLine();
                ChatMessage messageToSend = new ChatMessage(username, message);
                streamToServer.writeObject(messageToSend);
                streamToServer.flush();
            } while (!message.equalsIgnoreCase("exit"));
        } catch (IOException e) {
            System.out.println("Server closed connection. ");
            e.printStackTrace();
        }
        System.out.println("Disconnecting");
        // wytworzenie strumieni musi byc w try-with-resources -> try()
        // pobierz nick od usera
        // pętla aż user wpisze exit
        //    pobierać tekst do wysłania od użytkownika
        //    tworzymy nowy obiekt ChatMessage
        //    wysyłamy go do serwera -> wpisujemy do OOS
        // !!!! komunikat exit też wysyłamy na serwer
    }
}
