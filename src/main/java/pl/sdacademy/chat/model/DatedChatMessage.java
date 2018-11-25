package pl.sdacademy.chat.model;

import java.time.LocalDateTime;

public class DatedChatMessage extends ChatMessage {
    private final LocalDateTime receiveDate;

    public DatedChatMessage(ChatMessage chatMessage) {
        super(chatMessage.getAuthor(), chatMessage.getMessage()); // super musi być pierwszy w konstruktorze kopiującym
        receiveDate = LocalDateTime.now();
    }

    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }
}
