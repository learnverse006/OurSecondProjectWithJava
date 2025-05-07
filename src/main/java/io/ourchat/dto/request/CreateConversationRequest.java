package io.ourchat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 📦 DTO dùng để nhận yêu cầu tạo một cuộc trò chuyện 1-1 từ phía client.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
 public class CreateConversationRequest {
     // id bạn muốn tao cuộc trò chuyện với
    private long TargetUserId;}
