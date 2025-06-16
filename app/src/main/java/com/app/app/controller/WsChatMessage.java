package com.app.app.controller;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WsChatMessage {

    private String sender;
    private String content;
    private WsChatMessageType type;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public WsChatMessageType getType() {
        return type;
    }

    public void setType(WsChatMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
