package it_blog_net.ITBlogNet.Models;

import it_blog_net.ITBlogNet.Enums.NotificationMessageType;

public class NotificationMessage {
    private NotificationMessageType type;
    private String text;

    public NotificationMessage(NotificationMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public NotificationMessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}