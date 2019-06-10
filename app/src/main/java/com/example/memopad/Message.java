package com.example.memopad;

import android.os.Bundle;

import java.util.ArrayList;

public class Message {

    public static ArrayList<String> messages = new ArrayList<String>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public ArrayList<String> messageList() {
        return messages;
    }

}
