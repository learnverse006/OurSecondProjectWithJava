package io.ourchat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 📦 DTO trả về danh sách cuộc trò chuyện (conversation) cho client.
 *
 * Dùng trong màn hình danh sách đoạn chat (chat list).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    //id tro chuyen
    private long id;
    // kieu tro chuyen: direct, group, self
    private String type;

    private LocalDateTime updatedAt;

    private MessageResponse lastMessage;

    private List<UserSummary> members;

}
