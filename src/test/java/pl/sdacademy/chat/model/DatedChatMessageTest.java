package pl.sdacademy.chat.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatedChatMessageTest {
    @Test
    public void shouldCreateInstanceOfClass() {
        // Given
        ChatMessage chatMessage = new ChatMessage("Arek", "komunikat");
        // When
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);
        // Then
        assertThat(datedChatMessage.getReceiveDate()).isNotNull();
        assertThat(datedChatMessage.getMessage()).isEqualTo("komunikat");
        assertThat(datedChatMessage.getAuthor()).isEqualTo("Arek");
    }
}
