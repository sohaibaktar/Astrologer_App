package com.b.chattappmean;

public class Message {

    private String MessageId,Message, senderId;
    private long timestamp;
    private int feeling = -1;

    public Message() {

    }

    public Message(String message, String senderId, long timestamp) {
        Message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }
}
