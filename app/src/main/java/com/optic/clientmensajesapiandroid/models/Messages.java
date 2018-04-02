package com.optic.clientmensajesapiandroid.models;

import java.util.ArrayList;

/**
 * Created by Optic on 2/04/2018.
 * Clase utilizada para mostrar la lista de los mensajes
 */

public class Messages {

    private ArrayList<Message> messages;

    public Messages() {

    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

}
