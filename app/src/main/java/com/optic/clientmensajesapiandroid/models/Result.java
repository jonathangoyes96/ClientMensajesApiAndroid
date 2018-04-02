package com.optic.clientmensajesapiandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Optic on 1/04/2018.
 * Mapeando clase que contiene los datos enviados desde el web service
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public Result(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
