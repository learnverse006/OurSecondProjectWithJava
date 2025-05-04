package io.ourchat.model.enums;

public enum MessageStatus {
    SENDING,    // Đang gửi\
    SENT,
    DELIVERED, // Tin nhắn đã được chuyển đến người nhận (đã nhận bởi server)
    READ // Tin nhắn đã được người nhận đọc
}
