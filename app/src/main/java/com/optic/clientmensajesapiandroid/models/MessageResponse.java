package com.optic.clientmensajesapiandroid.models;

/**
 * Created by Optic on 2/04/2018.
 * Clase que se utiliza para mapear los mensajes que se envian a traves de retrofit mediante peticion POST
 */

public class MessageResponse {

    private boolean error;
    private String message;

    public MessageResponse() {

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
