package pl.sdacademy.chat.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatedChatMessageTest {
    @Test
    public void shouldCopyValuesFromChatMessage() {
        // Given
        String author = "Arek";
        String message = "kaczki";
        ChatMessage chatMessage = new ChatMessage(author, message);
        // When
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);
        // Then
        assertEquals(author, datedChatMessage.getAuthor());
        assertEquals(message, datedChatMessage.getMessage());
        assertNotNull(datedChatMessage.getReceiveDate());
        assertThat(datedChatMessage.getReceiveDate()).isNotNull();
        assertThat(datedChatMessage.getMessage()).isEqualTo(message);
        assertThat(datedChatMessage.getAuthor()).isEqualTo(author);
    }
}
