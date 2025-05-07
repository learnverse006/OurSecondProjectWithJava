package io.ourchat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 📦 DTO dùng để trả dữ liệu của 1 tin nhắn cho client.
 *
 * Chứa nội dung, người gửi, thời gian gửi, và ID cuộc trò chuyện.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    // id tin nhan
    private long id;
    private String content;
    //info sender
    private UserSummary sender;
    private LocalDateTime createAt;
    private Long conversationId;
}
