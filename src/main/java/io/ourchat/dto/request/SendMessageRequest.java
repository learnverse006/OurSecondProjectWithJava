package io.ourchat.dto.request;
// * 📦 DTO dùng để nhận yêu cầu gửi một tin nhắn mới từ client.

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private Long conversationId;
    private String content;
}
