package pl.sdacademy.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerSocketDispatcher {
    private final ServerSocket server;
    private final ChatLog chatLog;

    ServerSocketDispatcher(int portNumber) throws IOException { // typ prosty nie przyjmie nulla
        server = new ServerSocket(portNumber);
        chatLog = new ChatLog();
    }

    void dispatch() {
        try {
            while (true) { // środek pętli do innej klasy w przypadku testowania (żeby nie odpalać while'a w testach!)
                Socket client = server.accept();
                System.out.println("### New client connected! ###");
                Runnable clientTask = new ServerSocketReaderRunnable(client, chatLog);
                new Thread(clientTask).start();
            }
        } catch (IOException e) {
            System.out.println("!!!!! SERVER HALTED !!!!!");
            e.printStackTrace();
        }
    }
}
