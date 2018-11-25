package pl.sdacademy.chat.client;

import java.io.IOException;

class ClientTerminalMain {
    public static void main(String[] args) throws InterruptedException { // i tak JVM wyświetli stack trace
        int reconnectionAttemps = 3;
//        long timeBetweenReconnections = TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS);
        int timeBetweenReconnectAttempt = 5;

        System.out.println("Welcome to Awesome Chat!");
        while (reconnectionAttemps > 0) {
            try {
                System.out.println("Attempting to connect to server");
                ClientTerminal clientTerminal = new ClientTerminal();
                Thread thread = new Thread(clientTerminal);
                thread.setName("Terminal");
                thread.start();
                thread.join();
            } catch (IOException e) {
                System.out.println("Could not connect to server: " + e.getMessage());
//                e.printStackTrace();
                reconnectionAttemps--;
                if (reconnectionAttemps > 0) {
                    countdown(timeBetweenReconnectAttempt);
                }
            } /*catch (InterruptedException e) {
                System.out.println("Application was forcibly closed");
                return;
            }*/
        }
        System.out.println("See you soon!");
    }

    private static void countdown(int seconds) throws InterruptedException {
        while (seconds > 0) {
            System.out.println("### Waiting " + seconds + " till reconnect attempt...");
            Thread.sleep(1000);
            seconds--;
        }
    }
}
