package controllers;

import java.util.ArrayList;
import java.util.HashSet;

import models.Id;
import models.Message;

public class MessageController {
    private static MessageController controller = new MessageController();
    private HashSet<Message> messagesSeen;
    // why a HashSet??

    private MessageController(){}
    public ArrayList<Message> getMessages() {
        return new ArrayList<Message>(messagesSeen);
    }
    public ArrayList<Message> getMessagesForId(Id Id) {
        return null;
    }
    public Message getMessageForSequence(String seq) {
        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        return null;
    }
    public static MessageController getInstance(){
        return controller;
    }
    public void setMessagesSeen(HashSet<Message> messagesSeen){
        this.messagesSeen = messagesSeen;
    }
}